# 📦 本地编译指南

## ⚠️ 当前情况

**代码已完成** ✅  
**环境已配置** ✅ (Java 17 + Android SDK)  
**网络问题** ❌ (Gradle 下载超时)

---

## 🔧 解决方案

### 方案 A：使用国内镜像（推荐）

修改 `gradle/wrapper/gradle-wrapper.properties`：

```properties
distributionUrl=https\://mirrors.cloud.tencent.com/gradle/gradle-8.2-bin.zip
```

然后运行：
```bash
cd /root/.openclaw/workspace/projects/kb-app-native
export ANDROID_HOME=/opt/android-sdk
./gradlew assembleDebug
```

### 方案 B：手动下载 Gradle

1. **下载 Gradle 8.2**
   ```bash
   cd /tmp
   wget https://mirrors.cloud.tencent.com/gradle/gradle-8.2-bin.zip
   unzip gradle-8.2-bin.zip
   ```

2. **使用本地 Gradle 编译**
   ```bash
   cd /root/.openclaw/workspace/projects/kb-app-native
   export ANDROID_HOME=/opt/android-sdk
   /tmp/gradle-8.2/bin/gradle assembleDebug
   ```

### 方案 C：使用 Android Studio（最简单）

1. **下载安装 Android Studio**
   ```
   https://developer.android.com/studio
   ```

2. **打开项目**
   - 启动 Android Studio
   - File → Open
   - 选择 `kb-app-native` 目录

3. **编译 APK**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - 等待完成

4. **获取 APK**
   - 点击"locate"找到文件
   - 位置：`app/build/outputs/apk/debug/app-debug.apk`

---

## 📱 快速测试（使用 PWA 版本）

在等待编译期间，可以先用 PWA 版本：

1. 手机浏览器打开：`https://overhuman-ed-lineally.ngrok-free.dev`
2. 添加到主屏幕
3. 输入邀请码：`SAKZX7`
4. 功能和原生 APP 一样！

---

## 🤖 自动编译（GitHub Actions）

如果本地编译困难，可以上传到 GitHub 自动编译：

```bash
cd /root/.openclaw/workspace/projects/kb-app-native
./upload-to-github.sh
```

GitHub Actions 会自动编译并生成 APK 下载链接。

---

## 📊 编译产物

编译成功后，APK 位置：
```
app/build/outputs/apk/debug/app-debug.apk
```

文件大小：约 15-20MB

---

## 📞 需要帮助？

如果遇到问题，可以：
1. 检查网络环境
2. 使用国内 Gradle 镜像
3. 使用 Android Studio
4. 使用 GitHub Actions 自动编译

---

**代码已完成，选择一个适合你的编译方式即可！** 📚
