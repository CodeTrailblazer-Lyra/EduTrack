package xyz.lukix.edutrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.dto.StudentDTO;
import xyz.lukix.edutrack.service.StuService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StuController {

    private final StuService stuService;

    public StuController(StuService stuService) {
        this.stuService = stuService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStu() {
        List<StudentDTO> students = stuService.getAllStu();
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStuById(
            @PathVariable("id") Long id) {
        StudentDTO student = stuService.getStuById(id);
        if (student != null) {
            return ResponseEntity.ok(ApiResponse.success(student));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    @GetMapping("/num/{stuNum}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStuByStuNum(
            @PathVariable("stuNum") String stuNum) {
        // 路径变量现在通过过滤器自动清理，但仍保持显式清理以确保双重保护
        String cleanStuNum = xyz.lukix.edutrack.util.XssCleaner.clean(stuNum);
        StudentDTO student = stuService.getStuByStuNum(cleanStuNum);
        if (student != null) {
            return ResponseEntity.ok(ApiResponse.success(student));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with student number: " + escapeHtml(cleanStuNum)));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStu(
            @RequestBody Student student) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        student.cleanXss();
        
        try {
            StudentDTO createdStudent = stuService.createStu(student);
            return ResponseEntity.status(201).body(ApiResponse.success("Student created successfully", createdStudent));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStu(
            @PathVariable("id") Long id,
            @RequestBody Student student) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        student.cleanXss();
        
        try {
            StudentDTO updatedStudent = stuService.updateStu(id, student);
            if (updatedStudent != null) {
                return ResponseEntity.ok(ApiResponse.success("Student updated successfully", updatedStudent));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + escapeHtml(id.toString())));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStu(
            @PathVariable("id") Long id) {
        StudentDTO student = stuService.getStuById(id);
        if (student != null) {
            stuService.deleteStu(id);
            return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + escapeHtml(id.toString())));
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