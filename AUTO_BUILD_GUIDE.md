# 🤖 自动编译配置指南

## ✅ 已配置完成

GitHub Actions 自动编译工作流已创建完成！

---

## 📋 配置步骤

### 1️⃣ 上传代码到 GitHub

```bash
cd /root/.openclaw/workspace/projects/kb-app-native

# 初始化 Git（如果还没有）
git init

# 添加所有文件
git add .

# 提交
git commit -m "Initial commit: 课表 APP v1.0"

# 添加 GitHub 远程仓库（替换为你的仓库地址）
git remote add origin https://github.com/YOUR_USERNAME/kb-app-native.git

# 推送到 GitHub
git push -u origin main
```

### 2️⃣ 启用 GitHub Actions

1. 打开 GitHub 仓库页面
2. 点击 **Actions** 标签
3. 如果是首次使用，点击 **"I understand my workflows, go ahead and enable them"**
4. 会自动运行一次编译

### 3️⃣ 手动触发编译（可选）

1. 进入 **Actions** 页面
2. 左侧选择 **"Android CI"**
3. 点击右上角 **"Run workflow"**
4. 选择分支（main）
5. 点击 **"Run workflow"**

### 4️⃣ 下载 APK

编译完成后（约 5-10 分钟）：

1. 在 Actions 页面点击最近的运行记录
2. 滚动到底部 **"Artifacts"** 部分
3. 点击 **`app-debug`** 下载 Debug 版 APK
4. 解压后得到 `app-debug.apk`

---

## 📱 安装 APK

### 方法 A：USB 连接
```bash
adb install app-debug.apk
```

### 方法 B：发送到手机
1. 将 APK 文件发送到手机（微信/QQ/邮件）
2. 在手机上点击安装
3. 允许"未知来源"应用

---

## ⚙️ 高级配置（可选）

### 配置 Release 签名

#### 1. 生成签名密钥
```bash
keytool -genkey -v \
  -keystore keystore.jks \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias release
```

#### 2. 上传密钥到 GitHub Secrets

在 GitHub 仓库 → Settings → Secrets and variables → Actions

添加以下 Secrets：

| Name | Value |
|------|-------|
| `KEYSTORE_BASE64` | `base64 keystore.jks` 的内容 |
| `KEYSTORE_PASSWORD` | 你的密钥库密码 |
| `KEY_PASSWORD` | 你的密钥密码 |

#### 3. 编译 Release 版

手动触发 workflow 时会自动编译 Release 签名版。

---

## 📊 编译时间

| 阶段 | 预计时间 |
|------|----------|
| 检出代码 | 30 秒 |
| 设置 JDK | 1 分钟 |
| 下载依赖 | 3-5 分钟 |
| 编译 APK | 2-3 分钟 |
| 上传文件 | 30 秒 |
| **总计** | **7-10 分钟** |

---

## 🔧 故障排查

### Q1: Actions 不运行
**解决**：
- 检查 Actions 是否启用
- 查看仓库 Settings → Actions 权限

### Q2: 编译失败
**解决**：
- 点击失败的任务查看详细日志
- 检查 `build.gradle.kts` 配置
- 确认 Gradle 版本正确

### Q3: 下载 APK 失败
**解决**：
- 确认编译已完成（绿色✓）
- Artifact 保留 7 天，过期需要重新编译
- 尝试重新触发 workflow

### Q4: 依赖下载慢
**解决**：
- GitHub Actions 使用缓存
- 第二次编译会快很多
- 可以添加国内镜像源

---

## 🎯 自动编译触发条件

### 自动触发
- ✅ Push 到 main/master 分支
- ✅ 创建 Pull Request

### 手动触发
- ✅ 在 Actions 页面点击"Run workflow"

---

## 📁 项目结构

```
kb-app-native/
├── .github/
│   └── workflows/
│       └── android.yml      # GitHub Actions 配置 ✅
├── app/
│   ├── src/main/
│   │   ├── java/...         # Kotlin 源代码 ✅
│   │   ├── res/             # 资源文件 ✅
│   │   └── AndroidManifest.xml ✅
│   └── build.gradle.kts     # 构建配置 ✅
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties ✅
├── gradlew                  # Gradle Wrapper 脚本 ✅
├── build.gradle.kts         # 根构建配置 ✅
├── settings.gradle.kts      # 项目设置 ✅
└── README.md                # 项目说明 ✅
```

---

## 🚀 下一步

### 方案 A：上传到 GitHub（推荐）
1. 创建 GitHub 仓库
2. 推送代码
3. Actions 自动编译
4. 下载 APK 安装

### 方案 B：本地编译
1. 用 Android Studio 打开项目
2. 等待 Gradle 同步
3. Build → Build APK
4. 获取 APK 文件

---

## 💡 提示

- **首次编译慢**：需要下载所有依赖，之后会快很多
- **APK 有效期**：Artifact 保留 7 天，及时下载
- **签名版本**：Debug 版可以安装测试，Release 版需要签名配置
- **网络问题**：GitHub Actions 在国外，编译速度取决于网络

---

## 📞 需要帮助？

如果配置过程中遇到问题：
1. 查看 GitHub Actions 日志
2. 检查错误信息
3. 提供截图和详细描述

---

**配置完成！现在可以上传代码到 GitHub 了！** 🎉
