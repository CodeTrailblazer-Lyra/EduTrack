package xyz.lukix.edutrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.service.StuService;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "学生管理接口")
public class StuController {
    
    private final StuService stuService;
    
    public StuController(StuService stuService) {
        this.stuService = stuService;
    }

    @GetMapping
    @Operation(summary = "获取所有学生列表")
    public List<Student> getAllStu() {
        return stuService.getAllStu();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取学生信息")
    public Student getStuById(
            @Parameter(description = "学生ID")
            @PathVariable("id") Long id) {
        return stuService.getStuById(id);
    }

    @PostMapping
    @Operation(summary = "创建学生")
    public Student createStu(
            @Parameter(description = "学生对象")
            @RequestBody Student student) {
        return stuService.createStu(student);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新学生信息")
    public Student updateStu(
            @Parameter(description = "学生ID")
            @PathVariable("id") Long id,
            @Parameter(description = "学生对象")
            @RequestBody Student student) {
        return stuService.updateStu(id, student);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除学生")
    public void deleteStu(
            @Parameter(description = "学生ID")
            @PathVariable("id") Long id) {
        stuService.deleteStu(id);
    }
}