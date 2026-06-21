package com.example.agenttoolbox.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * CMD 命令执行工具（Windows 命令提示符）
 * 注意：在 Android 系统上 CMD 命令不可用，此工具仅用于兼容 Windows 环境
 */
public class CmdTool implements Tool {

    @Override
    public String getName() {
        return "cmd";
    }

    @Override
    public String getDescription() {
        return "在 Windows 系统上执行 CMD 命令，返回命令输出结果。Android 设备上不可用";
    }

    @Override
    public JSONObject getInputSchema() {
        JSONObject schema = new JSONObject();
        try {
            schema.put("type", "object");

            JSONObject properties = new JSONObject();

            JSONObject command = new JSONObject();
            command.put("type", "string");
            command.put("description", "要执行的 CMD 命令，如 \"dir\"、\"ipconfig\"、\"netstat -an\"");
            properties.put("command", command);

            JSONObject timeout = new JSONObject();
            timeout.put("type", "integer");
            timeout.put("description", "命令超时时间（秒），默认 30 秒");
            timeout.put("default", 30);
            properties.put("timeout", timeout);

            schema.put("properties", properties);

            JSONArray requiredArray = new JSONArray();
            requiredArray.put("command");
            schema.put("required", requiredArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schema;
    }

    @Override
    public String execute(JSONObject arguments) throws Exception {
        // Android 系统不支持 CMD 命令
        return "错误: CMD 命令在 Android 系统上不可用。请使用 shell 工具执行 shell 命令。\n\n" +
               "在 Windows PC 上，CMD 命令可通过远程服务执行。";
    }
}
