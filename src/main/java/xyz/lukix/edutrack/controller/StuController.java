package xyz.lukix.edutrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.dto.StudentDTO;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.service.StuService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "学生管理", description = "学生相关操作API")
public class StuController {

    private final StuService stuService;

    public StuController(StuService stuService) {
        this.stuService = stuService;
    }

    @GetMapping
    @Operation(summary = "获取所有学生", description = "返回系统中所有学生的列表")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取学生列表",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))})
    })
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStu() {
        List<StudentDTO> students = stuService.getAllStu();
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取学生", description = "根据学生ID返回特定学生的信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取学生信息",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "学生未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<StudentDTO>> getStuById(
            @Parameter(description = "学生ID") @PathVariable("id") Long id) {
        StudentDTO student = stuService.getStuById(id);
        if (student != null) {
            return ResponseEntity.ok(ApiResponse.success(student));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + id.toString()));
        }
    }
    
    @GetMapping("/num/{stuNum}")
    @Operation(summary = "根据学号获取学生", description = "根据学生学号返回特定学生的信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取学生信息",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "学生未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<StudentDTO>> getStuByStuNum(
            @Parameter(description = "学生学号") @PathVariable("stuNum") String stuNum) {
        StudentDTO student = stuService.getStuByStuNum(stuNum);
        if (student != null) {
            return ResponseEntity.ok(ApiResponse.success(student));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with student number: " + stuNum));
        }
    }

    @PostMapping
    @Operation(summary = "创建学生", description = "创建一个新的学生记录")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "学生信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "学生创建成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "学号已存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<StudentDTO>> createStu(
            @RequestBody Student student) {
        try {
            StudentDTO createdStudent = stuService.createStu(student);
            return ResponseEntity.status(201).body(ApiResponse.success("Student created successfully", createdStudent));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新学生", description = "更新指定ID的学生记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "学生更新成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "学生未找到",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "学号已存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<StudentDTO>> updateStu(
            @Parameter(description = "学生ID") @PathVariable("id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "学生信息") @RequestBody Student student) {
        try {
            StudentDTO updatedStudent = stuService.updateStu(id, student);
            if (updatedStudent != null) {
                return ResponseEntity.ok(ApiResponse.success("Student updated successfully", updatedStudent));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + id.toString()));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除学生", description = "删除指定ID的学生记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "学生删除成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "学生未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<String>> deleteStu(
            @Parameter(description = "学生ID") @PathVariable("id") Long id) {
        StudentDTO student = stuService.getStuById(id);
        if (student != null) {
            stuService.deleteStu(id);
            return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Student not found with id: " + id.toString()));
        }
    }
}