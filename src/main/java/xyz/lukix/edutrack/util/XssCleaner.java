package xyz.lukix.edutrack.util;

import org.apache.commons.text.StringEscapeUtils;

/**
 * XSS清理器
 */
public class XssCleaner {
    
    /**
     * 清理可能的XSS注入
     * @param value 需要清理的值
     * @return 清理后的值
     */
    public static String clean(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        
        // 使用Apache Commons Text进行HTML转义，防止XSS攻击
        value = StringEscapeUtils.escapeHtml4(value);
        
        // 移除潜在危险的JavaScript关键字
        value = value.replaceAll("(?i)javascript:", "")
                    .replaceAll("(?i)vbscript:", "")
                    .replaceAll("(?i)onload", "")
                    .replaceAll("(?i)onerror", "")
                    .replaceAll("(?i)onclick", "")
                    .replaceAll("(?i)onmouseover", "")
                    .replaceAll("(?i)onmouseout", "")
                    .replaceAll("(?i)onsubmit", "")
                    .replaceAll("(?i)onfocus", "")
                    .replaceAll("(?i)onblur", "");
        
        return value;
    }
}