package xyz.lukix.edutrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.dto.TeacherDTO;
import xyz.lukix.edutrack.service.TeacherService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    
    private final TeacherService teacherService;
    
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(ApiResponse.success(teachers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> getTeacherById(
            @PathVariable("id") Long id) {
        TeacherDTO teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            return ResponseEntity.ok(ApiResponse.success(teacher));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    @GetMapping("/num/{teachNum}")
    public ResponseEntity<ApiResponse<TeacherDTO>> getTeacherByTeachNum(
            @PathVariable("teachNum") String teachNum) {
        // 路径变量现在通过过滤器自动清理，但仍保持显式清理以确保双重保护
        String cleanTeachNum = xyz.lukix.edutrack.util.XssCleaner.clean(teachNum);
        TeacherDTO teacher = teacherService.getTeacherByTeachNum(cleanTeachNum);
        if (teacher != null) {
            return ResponseEntity.ok(ApiResponse.success(teacher));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with employee number: " + escapeHtml(cleanTeachNum)));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TeacherDTO>> createTeacher(
            @RequestBody Teacher teacher) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        teacher.cleanXss();
        
        try {
            TeacherDTO createdTeacher = teacherService.createTeacher(teacher);
            return ResponseEntity.status(201).body(ApiResponse.success("Teacher created successfully", createdTeacher));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacher(
            @PathVariable("id") Long id,
            @RequestBody Teacher teacher) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        teacher.cleanXss();
        
        try {
            TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacher);
            if (updatedTeacher != null) {
                return ResponseEntity.ok(ApiResponse.success("Teacher updated successfully", updatedTeacher));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + escapeHtml(id.toString())));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTeacher(
            @PathVariable("id") Long id) {
        TeacherDTO teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok(ApiResponse.success("Teacher deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + escapeHtml(id.toString())));
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