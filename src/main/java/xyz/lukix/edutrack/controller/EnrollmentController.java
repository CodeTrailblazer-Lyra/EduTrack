package xyz.lukix.edutrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.dto.EnrollmentDTO;
import xyz.lukix.edutrack.service.EnrollmentService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@Tag(name = "选课管理", description = "选课相关操作API")
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;
    
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    
    @GetMapping
    @Operation(summary = "获取所有选课记录", description = "返回系统中所有选课记录的列表")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取选课记录列表",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class))})
    })
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取选课记录", description = "根据选课记录ID返回特定选课记录的信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取选课记录信息",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "选课记录未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<EnrollmentDTO>> getEnrollmentById(
            @Parameter(description = "选课记录ID") @PathVariable("id") Long id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment != null) {
            return ResponseEntity.ok(ApiResponse.success(enrollment));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Enrollment record not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    @PostMapping
    @Operation(summary = "创建选课记录", description = "创建一个新的选课记录")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "选课记录信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "选课记录创建成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "学生或课程信息无效",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "该学生已选过此课程",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<EnrollmentDTO>> createEnrollment(
            @RequestBody EnrollmentDTO enrollmentDTO) {
        // DTO中的cleanXss方法会清理数据
        enrollmentDTO.cleanXss();
        
        try {
            EnrollmentDTO enrollment = enrollmentService.createEnrollment(enrollmentDTO);
            if (enrollment != null) {
                return ResponseEntity.status(201).body(ApiResponse.success("Enrollment record created successfully", enrollment));
            } else {
                return ResponseEntity.status(400).body(ApiResponse.badRequest("Invalid student or course information"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新选课记录", description = "更新指定ID的选课记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "选课记录更新成功",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "选课记录未找到",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "学生或课程不存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<EnrollmentDTO>> updateEnrollment(
            @Parameter(description = "选课记录ID") @PathVariable("id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "选课记录信息") @RequestBody EnrollmentDTO enrollmentDTO) {
        // DTO中的cleanXss方法会清理数据
        enrollmentDTO.cleanXss();
        
        try {
            EnrollmentDTO enrollment = enrollmentService.updateEnrollment(id, enrollmentDTO);
            if (enrollment != null) {
                return ResponseEntity.ok(ApiResponse.success("Enrollment record updated successfully", enrollment));
            } else {
                return ResponseEntity.status(404).body(ApiResponse.notFound("Enrollment record not found with id: " + escapeHtml(id.toString())));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(ApiResponse.conflict(escapeHtml(e.getMessage())));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除选课记录", description = "删除指定ID的选课记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "选课记录删除成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "选课记录未找到",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<String>> deleteEnrollment(
            @Parameter(description = "选课记录ID") @PathVariable("id") Long id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment != null) {
            enrollmentService.deleteEnrollment(id);
            return ResponseEntity.ok(ApiResponse.success("Enrollment record deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Enrollment record not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "根据学生ID获取选课记录", description = "根据学生ID返回该学生的所有选课记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取选课记录列表",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class))})
    })
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getEnrollmentsByStudentId(
            @Parameter(description = "学生ID") @PathVariable("studentId") Long studentId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }
    
    @GetMapping("/course/{courseId}")
    @Operation(summary = "根据课程ID获取选课记录", description = "根据课程ID返回该课程的所有选课记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功获取选课记录列表",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class))})
    })
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getEnrollmentsByCourseId(
            @Parameter(description = "课程ID") @PathVariable("courseId") Long courseId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        return ResponseEntity.ok(ApiResponse.success(enrollments));
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