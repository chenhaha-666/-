# 📘 Android Studio 编译指南

## ✅ 选择方案 A

使用 Android Studio 编译是最简单的方式！

---

## 📥 步骤 1：下载 Android Studio

### 下载地址
**官方下载**：https://developer.android.com/studio

**国内镜像**（更快）：
- 腾讯云：https://mirrors.cloud.tencent.com/AndroidStudio/
- 清华大学：https://mirrors.tuna.tsinghua.edu.cn/android-studio/

### 安装
**Windows**：
1. 下载 `android-studio-2023.x.x.xx-windows.exe`
2. 双击运行
3. 按提示安装（默认设置即可）

**macOS**：
1. 下载 `.dmg` 文件
2. 拖拽到 Applications 文件夹

**Linux**：
```bash
# 解压
tar -xzf android-studio-*.tar.gz -C /opt/

# 启动
cd /opt/android-studio/bin
./studio.sh
```

---

## 📂 步骤 2：打开项目

1. **启动 Android Studio**

2. **打开项目**
   - File → Open
   - 选择目录：`/root/.openclaw/workspace/projects/kb-app-native`
   - 点击 OK

3. **等待 Gradle 同步**
   - 首次打开会自动下载依赖
   - 底部有进度条
   - 等待 "BUILD SUCCESSFUL" 提示
   - 大约需要 5-10 分钟（首次）

---

## 🔨 步骤 3：编译 APK

### 编译 Debug 版（用于测试）

**方法 1：菜单**
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

**方法 2：快捷键**
- Windows/Linux: `Ctrl + Shift + A` → 输入 "Build APK"
- macOS: `Cmd + Shift + A` → 输入 "Build APK"

**方法 3：Gradle 面板**
1. View → Tool Windows → Gradle
2. 展开 `kb-app-native → app → Tasks → build`
3. 双击 `assembleDebug`

---

## 📦 步骤 4：获取 APK 文件

### 编译完成后

1. **点击"locate"链接**（右下角弹窗）
2. **或者手动查找**：
   ```
   /root/.openclaw/workspace/projects/kb-app-native/app/build/outputs/apk/debug/app-debug.apk
   ```

### APK 信息
- **文件名**: `app-debug.apk`
- **大小**: 约 15-20MB
- **签名**: Debug 签名（可直接安装）

---

## 📱 步骤 5：安装到手机

### 方法 A：USB 连接（推荐）

1. **手机开启开发者选项**
   - 设置 → 关于手机 → 连续点击"版本号"7 次
   - 设置 → 开发者选项 → 开启"USB 调试"

2. **连接电脑**
   ```bash
   # 检查设备
   adb devices
   
   # 安装 APK
   adb install /root/.openclaw/workspace/projects/kb-app-native/app/build/outputs/apk/debug/app-debug.apk
   ```

3. **成功提示**
   ```
   Success
   ```

### 方法 B：直接传输

1. **将 APK 发送到手机**
   - 微信文件传输助手
   - QQ
   - 邮件

2. **在手机上安装**
   - 点击下载的 APK 文件
   - 允许"未知来源"
   - 点击安装

---

## ⚠️ 常见问题

### Q1: Gradle 同步失败

**错误**: `Could not resolve all files`

**解决**:
1. File → Invalidate Caches / Restart
2. 等待重新索引
3. 如果还不行，删除 `.gradle` 目录重新同步

### Q2: SDK 未找到

**错误**: `Android SDK not found`

**解决**:
1. File → Project Structure
2. SDK Location → 选择 Android SDK 路径
3. 或者设置环境变量：
   ```bash
   export ANDROID_HOME=/opt/android-sdk
   ```

### Q3: JDK 版本不对

**错误**: `Unsupported class file major version`

**解决**:
1. File → Settings → Build, Execution, Deployment → Build Tools → Gradle
2. Gradle JDK → 选择 JDK 17

### Q4: 编译内存不足

**错误**: `OutOfMemoryError`

**解决**:
修改 `gradle.properties`：
```properties
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
```

---

## 🎯 快速验证

安装后测试：

1. **打开 APP**
   - 图标：📚 我的课表
   - 启动后显示渐变背景

2. **绑定课表**
   - 点击"绑定课表"
   - 输入邀请码：`SAKZX7`
   - 等待下载

3. **查看课表**
   - 自动显示今日课表
   - 切换到"周课表"查看表格
   - 点击课程查看详情

4. **离线测试**
   - 关闭网络
   - 打开 APP 仍能查看课表

---

## 📊 预计时间

| 步骤 | 时间 |
|------|------|
| 下载 Android Studio | 10-30 分钟 |
| 安装 | 5 分钟 |
| 首次打开项目 | 5-10 分钟 |
| 编译 APK | 2-5 分钟 |
| **总计** | **约 30-50 分钟** |

---

## 💡 提示

1. **首次编译慢**：需要下载所有依赖
2. **后续编译快**：使用缓存，约 1-2 分钟
3. **Debug 版无需签名**：可以直接安装
4. **Release 版需要签名**：上架应用商店用

---

## 📞 需要帮助？

如果遇到问题：
1. 查看 Android Studio 底部"Build"面板
2. 查看错误日志
3. 截图错误信息
4. 提供详细描述

---

**现在可以开始下载 Android Studio 了！** 📚

下载完成后，按照步骤操作即可！
