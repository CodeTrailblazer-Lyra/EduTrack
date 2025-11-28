package xyz.lukix.edutrack.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import xyz.lukix.edutrack.util.XssCleaner;

/**
 * XSS防护的HTTP请求包装器
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) {
            return XssCleaner.clean(value);
        }
        return value;
    }
    
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] cleanedValues = new String[length];
            for (int i = 0; i < length; i++) {
                cleanedValues[i] = XssCleaner.clean(values[i]);
            }
            return cleanedValues;
        }
        return values;
    }
    
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value != null) {
            return XssCleaner.clean(value);
        }
        return value;
    }
}