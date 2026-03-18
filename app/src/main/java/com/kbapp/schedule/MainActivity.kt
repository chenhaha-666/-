package com.kbapp.schedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kbapp.schedule.ui.components.CourseDetailDialog
import com.kbapp.schedule.ui.components.TodayScheduleView
import com.kbapp.schedule.ui.components.WeekScheduleView
import com.kbapp.schedule.ui.theme.Primary
import com.kbapp.schedule.ui.theme.ScheduleAppTheme
import com.kbapp.schedule.viewmodel.ScheduleViewModel
import java.time.DayOfWeek

class MainActivity : ComponentActivity() {
    
    private val viewModel: ScheduleViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            ScheduleAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF9FAFB)
                ) {
                    ScheduleApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun ScheduleApp(viewModel: ScheduleViewModel) {
    val inviteCode by viewModel.inviteCode.collectAsState()
    val currentWeek by viewModel.currentWeek.collectAsState()
    val schedules by viewModel.schedules.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    var showInviteDialog by remember { mutableStateOf(inviteCode == null) }
    var selectedCourse by remember { mutableStateOf<com.kbapp.schedule.data.model.Course?>(null) }
    var currentView by remember { mutableStateOf("today") } // "today" or "week"
    
    // 今天星期几
    val today = DayOfWeek.now().value
    val weekdayNames = listOf("", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")
    val weekday = weekdayNames.getOrElse(today) { "星期未知" }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 标题
            Text(
                text = "📚 我的课表",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
            
            // 日期
            val todayDate = java.time.LocalDate.now()
            Text(
                text = todayDate.toString(),
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
            
            // 主内容区域
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                if (inviteCode == null) {
                    // 未绑定邀请码
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = { showInviteDialog = true }) {
                            Text("绑定课表")
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // 视图切换
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF3F4F6), RoundedCornerShape(10.dp))
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            androidx.compose.material3.Button(
                                onClick = { currentView = "today" },
                                colors = if (currentView == "today") 
                                    androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Primary
                                    ) else androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("📅 今日", color = if (currentView == "today") Color.White else Color.Gray)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            androidx.compose.material3.Button(
                                onClick = { currentView = "week" },
                                colors = if (currentView == "week") 
                                    androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Primary
                                    ) else androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("📋 周课表", color = if (currentView == "week") Color.White else Color.Gray)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // 周次切换（仅周课表视图显示）
                        if (currentView == "week") {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                                        ),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                androidx.compose.material3.Button(
                                    onClick = { viewModel.changeWeek(-1) },
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    modifier = Modifier.width(80.dp)
                                ) {
                                    Text("◀ 上周", fontSize = 12.sp)
                                }
                                Text(
                                    text = "第 $currentWeek 周",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                ) {
                                    androidx.compose.material3.Button(
                                        onClick = { viewModel.changeWeek(1) },
                                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier.width(80.dp)
                                    ) {
                                        Text("下周 ▶", fontSize = 12.sp)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        
                        // 内容区域
                        if (isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else if (error != null) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(error!!, color = Color.Red)
                            }
                        } else {
                            if (currentView == "today") {
                                val todayCourses = viewModel.getTodaySchedule()
                                TodayScheduleView(
                                    courses = todayCourses,
                                    weekNumber = currentWeek,
                                    weekday = weekday
                                )
                            } else {
                                WeekScheduleView(
                                    schedules = schedules,
                                    onCourseClick = { course -> selectedCourse = course }
                                )
                            }
                        }
                    }
                }
            }
            
            // 底部按钮
            if (inviteCode != null) {
                Spacer(modifier = Modifier.height(12.dp))
                androidx.compose.material3.Button(
                    onClick = { showInviteDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6B7280)
                    )
                ) {
                    Text("切换课表")
                }
            }
        }
    }
    
    // 邀请码输入弹窗
    if (showInviteDialog) {
        InviteCodeDialog(
            onConfirm = { code ->
                viewModel.bindInviteCode(code)
                showInviteDialog = false
            },
            onDismiss = {
                if (inviteCode != null) showInviteDialog = false
            }
        )
    }
    
    // 课程详情弹窗
    selectedCourse?.let { course ->
        CourseDetailDialog(
            course = course,
            onDismiss = { selectedCourse = null }
        )
    }
}

@Composable
fun InviteCodeDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var code by remember { mutableStateOf("") }
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "绑定课表",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "输入 6 位邀请码",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it.uppercase() },
                    label = { Text("邀请码") },
                    singleLine = true,
                    maxLength = 6,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("取消")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onConfirm(code.trim().uppercase()) },
                        enabled = code.length == 6
                    ) {
                        Text("绑定")
                    }
                }
            }
        }
    }
}
