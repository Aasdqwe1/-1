# ProGuard 规则
# 禁用代码优化和混淆，避免 R8 编译问题
-dontshrink
-dontoptimize
-dontobfuscate

# 保留所有类和成员（通配规则，兼容老版本ProGuard）
-keep class ** { *; }
-keep class **$** { *; }

# 显式保留项目核心包的所有类（R8对通配规则兼容性有限，必须显式声明）
-keep,includedescriptorclasses class com.example.agenttoolbox.** { *; }
-keep,includedescriptorclasses class com.example.agenttoolbox.mcp.** { *; }
-keep,includedescriptorclasses class com.example.agenttoolbox.tools.** { *; }

# 特别保留 JSON-RPC 相关类（防止 NoClassDefFoundError: JsonRpcRequest/JsonRpcResponse）
-keep class com.example.agenttoolbox.mcp.JsonRpcRequest { *; }
-keep class com.example.agenttoolbox.mcp.JsonRpcResponse { *; }
-keep class com.example.agenttoolbox.mcp.McpServer { *; }
-keep class com.example.agenttoolbox.mcp.McpServer$ClientHandler { *; }

# 保留 JSON 相关类
-keep class org.json.** { *; }

# 防止 R8 仅因类"看起来未使用"就删除类
-keepattributes *Annotation*,Signature,EnclosingMethod,InnerClasses,SourceFile,LineNumberTable

# 不警告 Android SDK 以外的引用（org.json 属于Android自带）
-dontwarn org.json.**
-dontwarn com.example.agenttoolbox.**
