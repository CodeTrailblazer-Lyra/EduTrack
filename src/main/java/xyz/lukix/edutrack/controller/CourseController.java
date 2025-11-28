package xyz.lukix.edutrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.dto.CourseDTO;
import xyz.lukix.edutrack.service.CourseService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    
    private final CourseService courseService;
    
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseById(
            @PathVariable("id") Long id) {
        CourseDTO course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(ApiResponse.success(course));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    @GetMapping("/code/{lessonCode}")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseByLessonCode(
            @PathVariable("lessonCode") String lessonCode) {
        // 路径变量现在通过过滤器自动清理，但仍保持显式清理以确保双重保护
        String cleanLessonCode = xyz.lukix.edutrack.util.XssCleaner.clean(lessonCode);
        CourseDTO course = courseService.getCourseByLessonCode(cleanLessonCode);
        if (course != null) {
            return ResponseEntity.ok(ApiResponse.success(course));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with code: " + escapeHtml(cleanLessonCode)));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseDTO>> createCourse(
            @RequestBody Course course) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        course.cleanXss();
        
        try {
            CourseDTO createdCourse = courseService.createCourse(course);
            return ResponseEntity.status(201).body(ApiResponse.success("Course created successfully", createdCourse));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourse(
            @PathVariable("id") Long id,
            @RequestBody Course course) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        course.cleanXss();
        
        try {
            CourseDTO updatedCourse = courseService.updateCourse(id, course);
            if (updatedCourse != null) {
                return ResponseEntity.ok(ApiResponse.success("Course updated successfully", updatedCourse));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with id: " + escapeHtml(id.toString())));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @PathVariable("id") Long id) {
        CourseDTO course = courseService.getCourseById(id);
        if (course != null) {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(ApiResponse.success("Course deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    /**
     * HTML转义函数，防止XSS攻击
     * @param value 需要转义的字符串
     * @return 转义后的字符串
     */
    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#x27;");
    }
}