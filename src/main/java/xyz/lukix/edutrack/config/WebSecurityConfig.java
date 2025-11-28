package xyz.lukix.edutrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.HtmlUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebSecurityConfig {
    
    /**
     * 创建XSS防护过滤器
     */
    @Bean
    public OncePerRequestFilter xssFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
                    throws ServletException, IOException {
                
                // 包装请求以清理参数
                XssHttpServletRequestWrapper wrappedRequest = new XssHttpServletRequestWrapper(request);
                filterChain.doFilter(wrappedRequest, response);
            }
        };
    }
}