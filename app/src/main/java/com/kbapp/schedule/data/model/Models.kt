package com.kbapp.schedule.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 课程数据模型
 */
data class Course(
    val time: String,      // 节次和时间，如 "1-2 节 (8:30-10:05)"
    val name: String,      // 课程名称
    val location: String,  // 教室地点
    val teacher: String    // 教师姓名
)

/**
 * 课表数据模型（按天）
 */
data class Schedule(
    val id: Long = 0,
    val inviteCode: String,    // 邀请码
    val weekNumber: Int,       // 周次
    val dayOfWeek: Int,        // 星期几 (1-7)
    val courses: List<Course>  // 课程列表
)

/**
 * 邀请码数据模型
 */
data class InviteCode(
    val code: String,
    val isValid: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * API 响应数据
 */
data class ApiResponse<T>(
    val success: Boolean,
    val schedules: List<T>?,
    val message: String? = null
)

/**
 * 邀请码验证响应
 */
data class InviteResponse(
    val valid: Boolean,
    val code: String,
    val message: String? = null
)
