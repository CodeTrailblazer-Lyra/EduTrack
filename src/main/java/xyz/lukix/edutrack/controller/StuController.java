package xyz.lukix.edutrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Student;
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
    public ResponseEntity<ApiResponse<List<Student>>> getAllStu() {
        List<Student> students = stuService.getAllStu();
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStuById(
            @PathVariable("id") Long id) {
        Student student = stuService.getStuById(id);
        if (student != null) {
            return ResponseEntity.ok(ApiResponse.success(student));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + id));
        }
    }
    
    @GetMapping("/num/{stuNum}")
    public ResponseEntity<ApiResponse<Student>> getStuByStuNum(
            @PathVariable("stuNum") String stuNum) {
        // 路径变量现在通过过滤器自动清理，但仍保持显式清理以确保双重保护
        String cleanStuNum = xyz.lukix.edutrack.util.XssCleaner.clean(stuNum);
        Student student = stuService.getStuByStuNum(cleanStuNum);
        if (student != null) {
            return ResponseEntity.ok(ApiResponse.success(student));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with student number: " + cleanStuNum));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStu(
            @RequestBody Student student) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        student.cleanXss();
        
        try {
            Student createdStudent = stuService.createStu(student);
            return ResponseEntity.status(201).body(ApiResponse.success("Student created successfully", createdStudent));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStu(
            @PathVariable("id") Long id,
            @RequestBody Student student) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        student.cleanXss();
        
        try {
            Student updatedStudent = stuService.updateStu(id, student);
            if (updatedStudent != null) {
                return ResponseEntity.ok(ApiResponse.success("Student updated successfully", updatedStudent));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + id));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStu(
            @PathVariable("id") Long id) {
        Student student = stuService.getStuById(id);
        if (student != null) {
            stuService.deleteStu(id);
            return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + id));
        }
    }
}