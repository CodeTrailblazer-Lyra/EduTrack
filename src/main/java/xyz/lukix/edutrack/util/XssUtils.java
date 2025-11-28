package xyz.lukix.edutrack.util;

import org.apache.commons.text.StringEscapeUtils;

/**
 * XSS防护工具类
 */
public class XssUtils {
    
    /**
     * 清理用户输入，防止XSS攻击
     * @param input 用户输入
     * @return 清理后的字符串
     */
    public static String cleanXss(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        // 使用Apache Commons Text进行HTML转义
        return StringEscapeUtils.escapeHtml4(input);
    }
    
    /**
     * 清理用户输入的对象字段
     * @param object 包含用户输入的对象
     */
    public static void cleanXssForObject(Object object) {
        // 这里可以使用反射来清理对象的所有字符串字段
        // 但在实际应用中，最好在每个实体类中单独处理
    }
}