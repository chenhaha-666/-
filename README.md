# 📚 我的课表 - Android 原生 APP

基于 Kotlin + Jetpack Compose 开发的安卓课表应用。

## 🛠️ 技术栈

- **语言**: Kotlin
- **UI**: Jetpack Compose
- **架构**: MVVM
- **数据库**: Room (SQLite)
- **网络**: Retrofit + OkHttp
- **协程**: Kotlin Coroutines

## 📁 项目结构

```
app/src/main/java/com/kbapp/schedule/
├── MainActivity.kt              # 主界面
├── viewmodel/
│   └── ScheduleViewModel.kt     # ViewModel
├── data/
│   ├── model/
│   │   └── Models.kt            # 数据模型
│   ├── local/
│   │   ├── AppDatabase.kt       # 数据库
│   │   └── LocalDatabase.kt     # DAO
│   └── remote/
│       └── ApiService.kt        # API 接口
└── ui/
    ├── theme/
    │   └── Theme.kt             # 主题
    └── components/
        └── ScheduleComponents.kt # UI 组件
```

## 🚀 功能特性

### 核心功能
- ✅ 邀请码绑定（联网）
- ✅ 课表下载（3-18 周）
- ✅ 本地存储（SQLite）
- ✅ 今日课表视图
- ✅ 周课表表格视图
- ✅ 周次切换
- ✅ 课程详情弹窗
- ✅ 完全离线使用

### 技术特点
- 📱 Jetpack Compose 现代化 UI
- 🏗️ MVVM 架构，代码清晰
- 💾 Room 数据库，数据持久化
- 🌐 Retrofit 网络请求
- 🔄 协程异步处理
- 🎨 Material Design 3 设计

## 📦 编译打包

### 环境要求
- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17
- Android SDK 34
- Gradle 8.2

### 编译步骤

1. **打开项目**
   ```bash
   # 用 Android Studio 打开 kb-app-native 目录
   ```

2. **同步 Gradle**
   - File → Sync Project with Gradle Files

3. **编译 Debug 版**
   ```bash
   ./gradlew assembleDebug
   ```
   APK 输出位置：`app/build/outputs/apk/debug/app-debug.apk`

4. **编译 Release 版（签名）**
   ```bash
   ./gradlew assembleRelease
   ```
   APK 输出位置：`app/build/outputs/apk/release/app-release-unsigned.apk`

### 签名配置（Release 版）

创建 `keystore.properties`：
```properties
storePassword=你的密钥库密码
keyPassword=你的密钥密码
keyAlias=你的密钥别名
storeFile=/path/to/keystore.jks
```

修改 `app/build.gradle.kts`：
```kotlin
android {
    signingConfigs {
        create("release") {
            // 读取 keystore.properties
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

生成签名密钥：
```bash
keytool -genkey -v -keystore keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias
```

## 📱 安装使用

### 安装 APK
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 使用流程

1. **首次打开**
   - 点击"绑定课表"
   - 输入 6 位邀请码（如：SAKZX7）
   - 自动下载课表（3-18 周）

2. **查看课表**
   - 默认显示"今日"课表
   - 切换到"周课表"查看整周
   - 点击课程查看详情

3. **切换周次**
   - 在周课表界面使用"上周"/"下周"按钮
   - 切换到"今日"自动回到当前周

4. **离线使用**
   - 绑定后完全离线
   - 数据存在手机里
   - 无需网络连接

## 🔧 API 配置

当前 API 地址：
```kotlin
private const val BASE_URL = "https://overhuman-ed-lineally.ngrok-free.dev/"
```

如需修改，编辑 `app/src/main/java/com/kbapp/schedule/data/remote/ApiService.kt`

## 📊 数据库设计

### schedules 表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | LONG | 主键 |
| inviteCode | STRING | 邀请码 |
| weekNumber | INT | 周次 |
| dayOfWeek | INT | 星期几 (1-7) |
| coursesJson | STRING | 课程 JSON |

### invite_codes 表
| 字段 | 类型 | 说明 |
|------|------|------|
| code | STRING | 邀请码（主键） |
| isValid | BOOLEAN | 是否有效 |
| timestamp | LONG | 时间戳 |

## 🎨 UI 设计

### 颜色主题
- 主色：`#4F46E5` (Indigo)
- 渐变：`#667EEA` → `#764BA2`
- 背景：`#F9FAFB`

### 字体
- 标题：22sp Bold
- 正文：14-15sp
- 辅助：11-12sp

## 📝 待办事项

- [ ] 添加上课提醒通知
- [ ] 课表导出为图片
- [ ] 桌面小部件
- [ ] 主题切换
- [ ] 用户系统

## 📄 许可证

MIT License

---

**版本**: v1.0.0  
**创建日期**: 2026-03-18  
**开发者**: 夫夫 (Fufu)
