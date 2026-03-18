package com.kbapp.schedule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kbapp.schedule.data.model.Course
import com.kbapp.schedule.data.model.Schedule
import com.kbapp.schedule.ui.theme.Primary

/**
 * 今日课表列表
 */
@Composable
fun TodayScheduleView(
    courses: List<Course>,
    weekNumber: Int,
    weekday: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 头部
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Primary,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = "第 $weekNumber 周",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Text(
                text = weekday,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 课程列表
        if (courses.isEmpty()) {
            NoCoursesView()
        } else {
            LazyColumn {
                items(courses) { course ->
                    CourseItem(course)
                }
            }
        }
    }
}

/**
 * 单个课程卡片
 */
@Composable
fun CourseItem(course: Course) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        color = Color(0xFFF9FAFB),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = course.time,
                color = Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = course.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "📍 ${course.location}",
                fontSize = 11.sp,
                color = Color.Gray
            )
            if (course.teacher.isNotBlank()) {
                Text(
                    text = "👨‍🏫 ${course.teacher}",
                    fontSize = 11.sp,
                    color = Primary
                )
            }
        }
    }
}

/**
 * 无课表视图
 */
@Composable
fun NoCoursesView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🎉", fontSize = 40.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "今天没课！",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "好好享受假期吧～",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

/**
 * 周课表表格视图
 */
@Composable
fun WeekScheduleView(
    schedules: List<Schedule>,
    onCourseClick: (Course) -> Unit
) {
    val weekDays = listOf("一", "二", "三", "四", "五", "六", "日")
    val timeSlots = listOf(
        "1-2 节\n8:30-10:05",
        "3-4 节\n10:25-12:00",
        "5-6 节\n14:00-15:40",
        "7-8 节\n16:00-17:40",
        "9-10 节\n18:30-20:10"
    )
    
    // 按天组织课程
    val coursesByDay = mutableMapOf<Int, List<Course>>()
    schedules.forEach { schedule ->
        coursesByDay[schedule.dayOfWeek] = schedule.courses
    }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(8),
        modifier = Modifier.fillMaxWidth()
    ) {
        // 表头
        item {
            Box(
                modifier = Modifier
                    .background(Color(0xFFF3F4F6))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("节次", fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }
        weekDays.forEach { day ->
            Box(
                modifier = Modifier
                    .background(Primary)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(day, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
        
        // 课程数据
        timeSlots.forEachIndexed { slotIndex, slot ->
            // 节次列
            Box(
                modifier = Modifier
                    .background(Color(0xFFF3F4F6))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(slot, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
            
            // 7 天课程
            for (day in 1..7) {
                val courses = coursesByDay[day]
                val course = courses?.getOrNull(slotIndex)
                
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .border(
                            1.dp,
                            Color(0xFFE5E7EB),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(4.dp)
                        .clickable { course?.let { onCourseClick(it) } },
                    contentAlignment = Alignment.Center
                ) {
                    if (course != null) {
                        val nameShort = if (course.name.length > 6) 
                            course.name.take(6) + "…" else course.name
                        Text(
                            text = nameShort,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/**
 * 课程详情弹窗
 */
@Composable
fun CourseDetailDialog(
    course: Course,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(course.name, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("📍 地点", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(course.location, fontSize = 14.sp)
                }
                if (course.teacher.isNotBlank()) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("👨‍🏫 教师", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(course.teacher, fontSize = 14.sp)
                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("⏰ 时间", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(course.time, fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("关闭")
            }
        }
    )
}
