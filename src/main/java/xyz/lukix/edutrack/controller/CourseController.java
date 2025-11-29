package xyz.lukix.edutrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.dto.CourseDTO;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.service.CourseService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "课程管理", description = "课程相关操作API")
public class CourseController {
    
    private final CourseService courseService;
    
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "获取所有课程", description = "返回系统中所有课程的列表")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取课程列表",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))})
    })
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取课程", description = "根据课程ID返回特定课程的信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取课程信息",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseById(
            @Parameter(description = "课程ID") @PathVariable("id") Long id) {
        CourseDTO course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(ApiResponse.success(course));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with id: " + id.toString()));
        }
    }
    
    @GetMapping("/code/{lessonCode}")
    @Operation(summary = "根据课程代码获取课程", description = "根据课程代码返回特定课程的信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取课程信息",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseByLessonCode(
            @Parameter(description = "课程代码") @PathVariable("lessonCode") String lessonCode) {
        CourseDTO course = courseService.getCourseByLessonCode(lessonCode);
        if (course != null) {
            return ResponseEntity.ok(ApiResponse.success(course));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with code: " + lessonCode));
        }
    }

    @PostMapping
    @Operation(summary = "创建课程", description = "创建一个新的课程记录")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "课程信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "课程创建成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "课程代码已存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<CourseDTO>> createCourse(
            @RequestBody Course course) {
        try {
            CourseDTO createdCourse = courseService.createCourse(course);
            return ResponseEntity.status(201).body(ApiResponse.success("Course created successfully", createdCourse));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新课程", description = "更新指定ID的课程记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "课程更新成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程未找到",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "课程代码已存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourse(
            @Parameter(description = "课程ID") @PathVariable("id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "课程信息") @RequestBody Course course) {
        try {
            CourseDTO updatedCourse = courseService.updateCourse(id, course);
            if (updatedCourse != null) {
                return ResponseEntity.ok(ApiResponse.success("Course updated successfully", updatedCourse));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with id: " + id.toString()));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除课程", description = "删除指定ID的课程记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "课程删除成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @Parameter(description = "课程ID") @PathVariable("id") Long id) {
        CourseDTO course = courseService.getCourseById(id);
        if (course != null) {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(ApiResponse.success("Course deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Course not found with id: " + id.toString()));
        }
    }
}