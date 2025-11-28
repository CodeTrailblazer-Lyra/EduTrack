package xyz.lukix.edutrack.util;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    
    // 成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(200, "Success", data);
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(200, message, data);
    }
    
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<T>(200, "Success", null);
    }
    
    // 错误响应
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<T>(code, message, null);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<T>(500, message, null);
    }
    
    // 404 Not Found
    public static <T> ApiResponse<T> notFound() {
        return new ApiResponse<T>(404, "Resource not found", null);
    }
    
    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<T>(404, message, null);
    }
    
    // 400 Bad Request
    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<T>(400, message, null);
    }
    
    // 409 Conflict (用于重复数据等)
    public static <T> ApiResponse<T> conflict(String message) {
        return new ApiResponse<T>(409, message, null);
    }
}