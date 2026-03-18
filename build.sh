#!/bin/bash
# 课表 APP 编译脚本

set -e

echo "╔════════════════════════════════════════╗"
echo "║     课表 APP 编译工具                   ║"
echo "╚════════════════════════════════════════╝"

# 检查环境变量
if [ -z "$ANDROID_HOME" ]; then
    echo "❌ 错误：未设置 ANDROID_HOME 环境变量"
    echo "请设置：export ANDROID_HOME=/path/to/android/sdk"
    exit 1
fi

if [ -z "$JAVA_HOME" ]; then
    echo "❌ 错误：未设置 JAVA_HOME 环境变量"
    echo "请设置：export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64"
    exit 1
fi

echo "✅ ANDROID_HOME: $ANDROID_HOME"
echo "✅ JAVA_HOME: $JAVA_HOME"

# 进入项目目录
cd "$(dirname "$0")"

# 检查 Gradle
if ! command -v gradle &> /dev/null; then
    echo "⚠️  Gradle 未安装，使用 Gradle Wrapper..."
    GRADLE_CMD="./gradlew"
    if [ ! -f "$GRADLE_CMD" ]; then
        echo "❌ Gradle Wrapper 不存在"
        exit 1
    fi
else
    GRADLE_CMD="gradle"
fi

# 编译参数
BUILD_TYPE="debug"
if [ "$1" == "release" ]; then
    BUILD_TYPE="release"
fi

echo ""
echo "🔨 开始编译 $BUILD_TYPE 版本..."
echo ""

# 执行编译
$GRADLE_CMD assemble${BUILD_TYPE^} --stacktrace

# 输出结果
echo ""
echo "╔════════════════════════════════════════╗"
echo "║   ✅ 编译完成！                        ║"
echo "╚════════════════════════════════════════╝"
echo ""

APK_PATH="app/build/outputs/apk/$BUILD_TYPE/app-$BUILD_TYPE.apk"
if [ -f "$APK_PATH" ]; then
    echo "📦 APK 文件：$APK_PATH"
    echo "📊 文件大小：$(du -h "$APK_PATH" | cut -f1)"
    echo ""
    echo "📱 安装到手机："
    echo "   adb install $APK_PATH"
else
    echo "❌ APK 文件未找到"
    exit 1
fi
