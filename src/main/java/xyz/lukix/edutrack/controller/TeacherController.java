package xyz.lukix.edutrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Teacher;
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
    public ResponseEntity<ApiResponse<List<Teacher>>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(ApiResponse.success(teachers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Teacher>> getTeacherById(
            @PathVariable("id") Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            return ResponseEntity.ok(ApiResponse.success(teacher));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + id));
        }
    }
    
    @GetMapping("/num/{teachNum}")
    public ResponseEntity<ApiResponse<Teacher>> getTeacherByTeachNum(
            @PathVariable("teachNum") String teachNum) {
        // 路径变量现在通过过滤器自动清理，但仍保持显式清理以确保双重保护
        String cleanTeachNum = xyz.lukix.edutrack.util.XssCleaner.clean(teachNum);
        Teacher teacher = teacherService.getTeacherByTeachNum(cleanTeachNum);
        if (teacher != null) {
            return ResponseEntity.ok(ApiResponse.success(teacher));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with employee number: " + cleanTeachNum));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Teacher>> createTeacher(
            @RequestBody Teacher teacher) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        teacher.cleanXss();
        
        try {
            Teacher createdTeacher = teacherService.createTeacher(teacher);
            return ResponseEntity.status(201).body(ApiResponse.success("Teacher created successfully", createdTeacher));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Teacher>> updateTeacher(
            @PathVariable("id") Long id,
            @RequestBody Teacher teacher) {
        // 实体中的@PrePersist和@PreUpdate注解会自动清理，这里额外调用以确保安全
        teacher.cleanXss();
        
        try {
            Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
            if (updatedTeacher != null) {
                return ResponseEntity.ok(ApiResponse.success("Teacher updated successfully", updatedTeacher));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + id));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTeacher(
            @PathVariable("id") Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok(ApiResponse.success("Teacher deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + id));
        }
    }
}