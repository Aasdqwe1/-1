package com.example.agenttoolbox.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 工具管理器 - 注册和管理所有工具
 */
public class ToolManager {
    
    private Map<String, Tool> tools = new HashMap<>();
    private static ToolManager instance;
    
    private ToolManager() {
        // 注册所有内置工具
        registerTool(new MathCalculatorTool());
        registerTool(new HttpRequestTool());
        registerTool(new FileReadTool());
        registerTool(new FileWriteTool());
        registerTool(new FileListTool());
        registerTool(new ShellTool());
        registerTool(new CmdTool());
        registerTool(new PythonTool());
        registerTool(new ShTool());
        registerTool(new WebTool());
    }
    
    public static synchronized ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }
    
    /**
     * 注册工具
     */
    public void registerTool(Tool tool) {
        tools.put(tool.getName(), tool);
    }
    
    /**
     * 获取工具
     */
    public Tool getTool(String name) {
        return tools.get(name);
    }
    
    /**
     * 获取所有工具列表（MCP格式）
     */
    public JSONArray getToolsList() {
        JSONArray result = new JSONArray();
        for (Tool tool : tools.values()) {
            try {
                JSONObject toolObj = new JSONObject();
                toolObj.put("name", tool.getName());
                toolObj.put("description", tool.getDescription());
                toolObj.put("inputSchema", tool.getInputSchema());
                result.put(toolObj);
            } catch (JSONException e) {
                // 正常情况下不会发生
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 调用工具
     */
    public JSONObject callTool(String name, JSONObject arguments) {
        android.util.Log.d("ToolManager", "[CALL_TOOL] name=" + name);
        android.util.Log.d("ToolManager", "[CALL_TOOL_ARGS] " + (arguments != null ? arguments.toString().substring(0, Math.min(200, arguments.toString().length())) : "null"));
        
        JSONObject result = new JSONObject();
        JSONArray content = new JSONArray();
        JSONObject contentItem = new JSONObject();
        
        try {
            Tool tool = tools.get(name);
            if (tool == null) {
                android.util.Log.e("ToolManager", "[CALL_TOOL_NOT_FOUND] Tool not found: " + name);
                result.put("isError", true);
                contentItem.put("type", "text");
                contentItem.put("text", "工具不存在: " + name);
                content.put(contentItem);
                result.put("content", content);
                return result;
            }
            
            android.util.Log.d("ToolManager", "[TOOL_EXECUTE_START] Executing " + name);
            String output = tool.execute(arguments);
            android.util.Log.d("ToolManager", "[TOOL_EXECUTE_END] Completed " + name + ", output length=" + (output != null ? output.length() : 0));
            
            result.put("isError", false);
            contentItem.put("type", "text");
            contentItem.put("text", output);
            content.put(contentItem);
            result.put("content", content);
            
        } catch (Exception e) {
            android.util.Log.e("ToolManager", "[TOOL_EXECUTE_ERROR] Error executing " + name, e);
            try {
                result.put("isError", true);
                contentItem.put("type", "text");
                contentItem.put("text", "工具执行失败: " + e.getMessage());
                content.put(contentItem);
                result.put("content", content);
            } catch (JSONException ex) {
                // 正常情况下不会发生
                ex.printStackTrace();
            }
        }
        
        android.util.Log.d("ToolManager", "[CALL_TOOL_RESULT] " + result.toString().substring(0, Math.min(200, result.toString().length())));
        return result;
    }
    
}
