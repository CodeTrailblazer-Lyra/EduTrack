package xyz.lukix.edutrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "课程管理接口")
public class CourseController {
    
    private final CourseService courseService;
    
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "获取所有课程列表")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取课程信息")
    public Course getCourseById(
            @Parameter(description = "课程ID")
            @PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    @Operation(summary = "创建课程")
    public Course createCourse(
            @Parameter(description = "课程对象")
            @RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新课程信息")
    public Course updateCourse(
            @Parameter(description = "课程ID")
            @PathVariable("id") Long id,
            @Parameter(description = "课程对象")
            @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除课程")
    public void deleteCourse(
            @Parameter(description = "课程ID")
            @PathVariable("id") Long id) {
        courseService.deleteCourse(id);
    }
}