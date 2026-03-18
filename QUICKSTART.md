# 🚀 快速开始指南

## 方式一：Android Studio（推荐）

### 1. 打开项目
1. 启动 Android Studio
2. File → Open
3. 选择 `/root/.openclaw/workspace/projects/kb-app-native` 目录
4. 点击 OK

### 2. 等待 Gradle 同步
- Android Studio 会自动下载依赖
- 首次同步需要 5-10 分钟
- 等待底部进度条完成

### 3. 编译 APK
**Debug 版**（无签名，用于测试）：
```bash
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

**Release 版**（需要签名，用于发布）：
```bash
Build → Generate Signed Bundle / APK
→ 选择 APK
→ 创建或选择密钥库
→ 填写签名信息
→ 点击 Build
```

### 4. 获取 APK 文件
编译完成后，点击"locate"找到 APK 文件：
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

### 5. 安装到手机
**方法 A：USB 连接**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**方法 B：直接传输**
- 将 APK 文件传到手机
- 在手机上点击安装（需要允许"未知来源"）

---

## 方式二：命令行编译

### 环境准备
```bash
# 设置环境变量
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ANDROID_HOME=/path/to/android/sdk

# 确认 Gradle 可用
gradle --version
```

### 编译命令
```bash
cd /root/.openclaw/workspace/projects/kb-app-native

# Debug 版
./build.sh

# Release 版
./build.sh release
```

---

## 方式三：在线编译（无需环境）

如果本地环境配置困难，可以使用：

### GitHub Actions（推荐）
1. 将代码上传到 GitHub
2. 添加 `.github/workflows/android.yml`
3. 自动编译生成 APK
4. 在 Actions 页面下载 APK

### 在线编译服务
- https://www.codemagic.io/
- https://appcircle.io/
- https://nevercode.io/

---

## 📱 安装后使用

### 首次打开
1. 点击"绑定课表"
2. 输入邀请码：`SAKZX7`
3. 等待下载课表（约 10 秒）
4. 自动显示今日课表

### 功能说明
- **今日**：显示当天课表
- **周课表**：5 行 7 列表格，显示整周课程
- **点击课程**：查看详情（地点、老师、时间）
- **切换周次**：上周/下周按钮
- **切换课表**：输入新的邀请码

---

## ⚠️ 常见问题

### Q1: Gradle 同步失败
**解决**：
- 检查网络连接
- File → Invalidate Caches / Restart
- 删除 `.gradle` 目录重新同步

### Q2: SDK 版本不匹配
**解决**：
- Tools → SDK Manager
- 安装 Android 34 (API 34)

### Q3: 编译内存不足
**解决**：
修改 `gradle.properties`：
```properties
org.gradle.jvmargs=-Xmx4096m
```

### Q4: 找不到签名密钥
**解决**（仅 Release 版需要）：
```bash
keytool -genkey -v -keystore keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias
```

### Q5: APK 无法安装
**解决**：
- 手机设置 → 安全 → 允许"未知来源"
- 确认 APK 文件完整
- 尝试 Debug 版本

---

## 📊 编译时间参考

| 电脑配置 | 首次编译 | 后续编译 |
|----------|----------|----------|
| 低配 (4G RAM) | 10-15 分钟 | 2-3 分钟 |
| 中配 (8G RAM) | 5-8 分钟 | 1-2 分钟 |
| 高配 (16G+ RAM) | 3-5 分钟 | 30 秒 -1 分钟 |

---

## 🎯 下一步

编译成功后：
1. ✅ 安装到手机测试
2. ✅ 测试所有功能
3. ✅ 反馈问题或需求
4. ✅ 准备上架应用商店（可选）

---

**有问题随时联系！** 📚
