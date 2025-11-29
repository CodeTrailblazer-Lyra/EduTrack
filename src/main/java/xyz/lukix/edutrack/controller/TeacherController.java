package xyz.lukix.edutrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.dto.TeacherDTO;
import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.service.TeacherService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@Tag(name = "教师管理", description = "教师相关操作API")
public class TeacherController {
    
    private final TeacherService teacherService;
    
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "获取所有教师", description = "返回系统中所有教师的列表")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取教师列表",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDTO.class))})
    })
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(ApiResponse.success(teachers));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取教师", description = "根据教师ID返回特定教师的信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取教师信息",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "教师未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<TeacherDTO>> getTeacherById(
            @Parameter(description = "教师ID") @PathVariable("id") Long id) {
        TeacherDTO teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            return ResponseEntity.ok(ApiResponse.success(teacher));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + id.toString()));
        }
    }
    
    @GetMapping("/num/{teachNum}")
    @Operation(summary = "根据教职工号获取教师", description = "根据教师教职工号返回特定教师的信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取教师信息",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "教师未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<TeacherDTO>> getTeacherByTeachNum(
            @Parameter(description = "教师教职工号") @PathVariable("teachNum") String teachNum) {
        TeacherDTO teacher = teacherService.getTeacherByTeachNum(teachNum);
        if (teacher != null) {
            return ResponseEntity.ok(ApiResponse.success(teacher));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with employee number: " + teachNum));
        }
    }

    @PostMapping
    @Operation(summary = "创建教师", description = "创建一个新的教师记录")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "教师信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "教师创建成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "教职工号已存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<TeacherDTO>> createTeacher(
            @RequestBody Teacher teacher) {
        try {
            TeacherDTO createdTeacher = teacherService.createTeacher(teacher);
            return ResponseEntity.status(201).body(ApiResponse.success("Teacher created successfully", createdTeacher));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新教师", description = "更新指定ID的教师记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "教师更新成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "教师未找到",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "教职工号已存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacher(
            @Parameter(description = "教师ID") @PathVariable("id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "教师信息") @RequestBody Teacher teacher) {
        try {
            TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacher);
            if (updatedTeacher != null) {
                return ResponseEntity.ok(ApiResponse.success("Teacher updated successfully", updatedTeacher));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + id.toString()));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除教师", description = "删除指定ID的教师记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "教师删除成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "教师未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<String>> deleteTeacher(
            @Parameter(description = "教师ID") @PathVariable("id") Long id) {
        TeacherDTO teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok(ApiResponse.success("Teacher deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Teacher not found with id: " + id.toString()));
        }
    }
}