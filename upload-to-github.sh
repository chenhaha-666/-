#!/bin/bash
# 一键上传到 GitHub 脚本

set -e

echo "╔════════════════════════════════════════╗"
echo "║   课表 APP - 上传到 GitHub              ║"
echo "╚════════════════════════════════════════╝"
echo ""

# 检查 Git
if ! command -v git &> /dev/null; then
    echo "❌ 错误：Git 未安装"
    echo "请安装：sudo apt-get install git"
    exit 1
fi

# 进入项目目录
cd "$(dirname "$0")"

# 检查是否已有 Git 仓库
if [ ! -d ".git" ]; then
    echo "📦 初始化 Git 仓库..."
    git init
fi

# 添加所有文件
echo "📝 添加文件..."
git add .

# 输入提交信息
echo ""
read -p "输入提交信息 (默认：Initial commit): " COMMIT_MSG
COMMIT_MSG=${COMMIT_MSG:-"Initial commit: 课表 APP v1.0"}

# 提交
echo "💾 提交代码..."
git commit -m "$COMMIT_MSG"

# 输入 GitHub 仓库地址
echo ""
echo "请输入 GitHub 仓库地址："
echo "格式：https://github.com/用户名/仓库名.git"
read -p "仓库地址：" REPO_URL

if [ -n "$REPO_URL" ]; then
    # 添加远程仓库
    echo "🔗 添加远程仓库..."
    git remote add origin "$REPO_URL" 2>/dev/null || git remote set-url origin "$REPO_URL"
    
    # 推送
    echo "🚀 推送到 GitHub..."
    git push -u origin main
    
    echo ""
    echo "╔════════════════════════════════════════╗"
    echo "║   ✅ 上传成功！                        ║"
    echo "╚════════════════════════════════════════╝"
    echo ""
    echo "📱 下一步："
    echo "1. 打开 GitHub 仓库页面"
    echo "2. 点击 'Actions' 标签"
    echo "3. 启用 GitHub Actions"
    echo "4. 等待自动编译完成（约 10 分钟）"
    echo "5. 下载 APK 文件安装"
    echo ""
    echo "📖 详细说明请查看：AUTO_BUILD_GUIDE.md"
else
    echo ""
    echo "⚠️  未输入仓库地址，跳过推送"
    echo ""
    echo "📝 手动添加远程仓库："
    echo "   git remote add origin https://github.com/用户名/仓库名.git"
    echo "   git push -u origin main"
fi
