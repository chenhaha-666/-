package com.kbapp.schedule.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kbapp.schedule.data.local.AppDatabase
import com.kbapp.schedule.data.local.InviteCodeEntity
import com.kbapp.schedule.data.local.ScheduleEntity
import com.kbapp.schedule.data.model.Course
import com.kbapp.schedule.data.model.Schedule
import com.kbapp.schedule.data.remote.ApiClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 课表 ViewModel
 */
class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val scheduleDao = database.scheduleDao()
    private val inviteCodeDao = database.inviteCodeDao()
    private val apiService = ApiClient.createApiService()
    
    // 当前邀请码
    private val _inviteCode = MutableStateFlow<String?>(null)
    val inviteCode: StateFlow<String?> = _inviteCode.asStateFlow()
    
    // 当前周次
    private val _currentWeek = MutableStateFlow(calculateCurrentWeek())
    val currentWeek: StateFlow<Int> = _currentWeek.asStateFlow()
    
    // 课表数据
    private val _schedules = MutableStateFlow<List<Schedule>>(emptyList())
    val schedules: StateFlow<List<Schedule>> = _schedules.asStateFlow()
    
    // 加载状态
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // 错误信息
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        // 加载保存的邀请码
        loadInviteCode()
    }
    
    /**
     * 加载本地保存的邀请码
     */
    private fun loadInviteCode() {
        viewModelScope.launch {
            inviteCodeDao.getCurrentInviteCode().collect { entity ->
                _inviteCode.value = entity?.code
                if (entity?.code != null) {
                    loadWeekSchedules(entity.code, _currentWeek.value)
                }
            }
        }
    }
    
    /**
     * 绑定邀请码并下载课表
     */
    fun bindInviteCode(code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // 1. 验证邀请码
                val verifyResult = apiService.verifyInviteCode(code)
                if (!verifyResult.valid) {
                    _error.value = "邀请码无效"
                    _isLoading.value = false
                    return@launch
                }
                
                // 2. 保存邀请码
                inviteCodeDao.saveInviteCode(InviteCodeEntity(code))
                _inviteCode.value = code
                
                // 3. 下载第 3-18 周课表
                for (week in 3..18) {
                    try {
                        val response = apiService.getSchedules(code, week, null)
                        if (response.success && response.schedules != null) {
                            val entities = response.schedules.map { schedule ->
                                ScheduleEntity(
                                    inviteCode = code,
                                    weekNumber = week,
                                    dayOfWeek = schedule.dayOfWeek,
                                    coursesJson = schedule.courses.joinToString("|||") { 
                                        "${it.time}::${it.name}::${it.location}::${it.teacher}" 
                                    }
                                )
                            }
                            scheduleDao.insertSchedules(entities)
                        }
                    } catch (e: Exception) {
                        // 某周下载失败，继续下载其他周
                    }
                }
                
                // 4. 加载本地课表
                loadWeekSchedules(code, _currentWeek.value)
                
                _isLoading.value = false
                
            } catch (e: Exception) {
                _error.value = "网络错误：${e.message}"
                _isLoading.value = false
            }
        }
    }
    
    /**
     * 加载指定周次的课表
     */
    fun loadWeekSchedules(week: Int) {
        val code = _inviteCode.value ?: return
        viewModelScope.launch {
            loadWeekSchedules(code, week)
        }
    }
    
    private suspend fun loadWeekSchedules(code: String, week: Int) {
        val entities = scheduleDao.getWeekSchedule(code, week)
        _schedules.value = entities.map { entity ->
            val courses = entity.coursesJson.split("|||").map { parts ->
                val p = parts.split("::")
                Course(
                    time = p.getOrElse(0) { "" },
                    name = p.getOrElse(1) { "" },
                    location = p.getOrElse(2) { "" },
                    teacher = p.getOrElse(3) { "" }
                )
            }
            Schedule(
                id = entity.id,
                inviteCode = entity.inviteCode,
                weekNumber = entity.weekNumber,
                dayOfWeek = entity.dayOfWeek,
                courses = courses
            )
        }
    }
    
    /**
     * 切换周次
     */
    fun changeWeek(delta: Int) {
        val newWeek = (_currentWeek.value + delta).coerceIn(1, 19)
        _currentWeek.value = newWeek
        loadWeekSchedules(newWeek)
    }
    
    /**
     * 切换到今日
     */
    fun switchToToday() {
        _currentWeek.value = calculateCurrentWeek()
        loadWeekSchedules(_currentWeek.value)
    }
    
    /**
     * 解绑邀请码
     */
    fun unbindInviteCode() {
        viewModelScope.launch {
            _inviteCode.value?.let { code ->
                scheduleDao.deleteByInviteCode(code)
                inviteCodeDao.deleteAll()
            }
            _inviteCode.value = null
            _schedules.value = emptyList()
        }
    }
    
    /**
     * 计算当前周次
     */
    private fun calculateCurrentWeek(): Int {
        val semesterStart = java.time.LocalDate.of(2026, 2, 24)
        val today = java.time.LocalDate.now()
        val days = java.time.temporal.ChronoUnit.DAYS.between(semesterStart, today)
        return maxOf(1, (days / 7 + 1).toInt())
    }
    
    /**
     * 获取今天的课表
     */
    fun getTodaySchedule(): List<Course> {
        val today = java.time.DayOfWeek.now().value // 1=周一，7=周日
        return _schedules.value.find { it.dayOfWeek == today }?.courses ?: emptyList()
    }
    
    /**
     * 获取指定星期的课表
     */
    fun getScheduleForDay(dayOfWeek: Int): List<Course> {
        return _schedules.value.find { it.dayOfWeek == dayOfWeek }?.courses ?: emptyList()
    }
}
