# 📤 手动上传到 GitHub 指南

## ⚠️ 问题

自动推送需要 GitHub 认证（用户名/密码或 Token）。

---

## ✅ 解决方案

### 方案 A：使用 GitHub Desktop（最简单）

1. **下载 GitHub Desktop**
   - Windows/macOS: https://desktop.github.com/

2. **登录 GitHub**
   - 打开 GitHub Desktop
   - 登录你的账号

3. **添加项目**
   - File → Add Local Repository
   - 选择：`/root/.openclaw/workspace/projects/kb-app-native`
   - 点击 Add

4. **提交并推送**
   - 输入提交信息："课表 APP v1.0"
   - 点击 Commit to master
   - 点击 Publish repository
   - 完成！

---

### 方案 B：使用 Git 命令行（需要 Token）

1. **创建 Personal Access Token**
   - 打开：https://github.com/settings/tokens
   - 点击 "Generate new token (classic)"
   - 勾选：`repo` 权限
   - 点击 Generate
   - **复制 Token**（只显示一次！）

2. **使用 Token 推送**
   ```bash
   cd /root/.openclaw/workspace/projects/kb-app-native
   
   # 设置 Git 用户
   git config user.email "你的邮箱"
   git config user.name "你的用户名"
   
   # 推送（替换 YOUR_TOKEN 和仓库地址）
   git push https://YOUR_TOKEN@github.com/chenhaha-666/-.git master
   ```

---

### 方案 C：网页上传（最简单，但文件多）

1. **打开 GitHub 仓库**
   - https://github.com/chenhaha-666/-.git

2. **点击 "uploading an existing file"**

3. **拖拽文件**
   - 将整个项目文件夹拖进去
   - 等待上传

4. **提交**
   - 输入提交信息
   - 点击 Commit changes

---

### 方案 D：使用 PWA 版本（立即可用）

如果上传太麻烦，先用 PWA 版本：

```
https://overhuman-ed-lineally.ngrok-free.dev
```

功能和 APK 一样！

---

## 📱 编译 APK

上传成功后：

1. 打开 GitHub 仓库
2. 点击 **Actions** 标签
3. 启用 Actions
4. 等待编译完成（10 分钟）
5. 下载 APK

---

**推荐：使用 GitHub Desktop 最简单！** 📚
