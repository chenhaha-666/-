package com.kbapp.schedule.data.local

import androidx.room.*
import com.kbapp.schedule.data.model.Course
import kotlinx.coroutines.flow.Flow

/**
 * 课表数据访问对象
 */
@Dao
interface ScheduleDao {
    
    @Query("SELECT * FROM schedules WHERE inviteCode = :inviteCode ORDER BY weekNumber, dayOfWeek")
    fun getAllSchedules(inviteCode: String): Flow<List<ScheduleEntity>>
    
    @Query("SELECT * FROM schedules WHERE inviteCode = :inviteCode AND weekNumber = :week AND dayOfWeek = :day")
    suspend fun getSchedule(inviteCode: String, week: Int, day: Int): ScheduleEntity?
    
    @Query("SELECT * FROM schedules WHERE inviteCode = :inviteCode AND weekNumber = :week")
    suspend fun getWeekSchedule(inviteCode: String, week: Int): List<ScheduleEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: ScheduleEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)
    
    @Query("DELETE FROM schedules WHERE inviteCode = :inviteCode")
    suspend fun deleteByInviteCode(inviteCode: String)
    
    @Query("SELECT DISTINCT inviteCode FROM schedules")
    suspend fun getAllInviteCodes(): List<String>
}

/**
 * 邀请码数据访问对象
 */
@Dao
interface InviteCodeDao {
    
    @Query("SELECT * FROM invite_codes LIMIT 1")
    fun getCurrentInviteCode(): Flow<InviteCodeEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInviteCode(code: InviteCodeEntity)
    
    @Query("DELETE FROM invite_codes")
    suspend fun deleteAll()
}

/**
 * 课表实体类（数据库表）
 */
@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val inviteCode: String,
    val weekNumber: Int,
    val dayOfWeek: Int,
    val coursesJson: String  // 存储为 JSON 字符串
)

/**
 * 邀请码实体类
 */
@Entity(tableName = "invite_codes")
data class InviteCodeEntity(
    @PrimaryKey val code: String,
    val isValid: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * 课程类型转换器
 */
class Converters {
    @TypeConverter
    fun fromCourseList(courses: List<Course>): String {
        return courses.joinToString("|||") { "${it.time}::${it.name}::${it.location}::${it.teacher}" }
    }
    
    @TypeConverter
    fun toCourseList(data: String): List<Course> {
        if (data.isEmpty()) return emptyList()
        return data.split("|||").map { parts ->
            val p = parts.split("::")
            Course(
                time = p.getOrElse(0) { "" },
                name = p.getOrElse(1) { "" },
                location = p.getOrElse(2) { "" },
                teacher = p.getOrElse(3) { "" }
            )
        }
    }
}
