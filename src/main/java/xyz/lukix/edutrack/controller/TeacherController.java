package xyz.lukix.edutrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@Tag(name = "教师管理接口")
public class TeacherController {
    
    private final TeacherService teacherService;
    
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "获取所有教师列表")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取教师信息")
    public Teacher getTeacherById(
            @Parameter(description = "教师ID")
            @PathVariable("id") Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    @Operation(summary = "创建教师")
    public Teacher createTeacher(
            @Parameter(description = "教师对象")
            @RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新教师信息")
    public Teacher updateTeacher(
            @Parameter(description = "教师ID")
            @PathVariable("id") Long id,
            @Parameter(description = "教师对象")
            @RequestBody Teacher teacher) {
        return teacherService.updateTeacher(id, teacher);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除教师")
    public void deleteTeacher(
            @Parameter(description = "教师ID")
            @PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
    }
}