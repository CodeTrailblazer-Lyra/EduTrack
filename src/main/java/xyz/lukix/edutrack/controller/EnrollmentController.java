package xyz.lukix.edutrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.dto.EnrollmentDTO;
import xyz.lukix.edutrack.service.EnrollmentService;
import xyz.lukix.edutrack.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;
    
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentDTO>> getEnrollmentById(@PathVariable("id") Long id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment != null) {
            return ResponseEntity.ok(ApiResponse.success(enrollment));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Enrollment record not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentDTO>> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
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
    public ResponseEntity<ApiResponse<EnrollmentDTO>> updateEnrollment(
            @PathVariable("id") Long id,
            @RequestBody EnrollmentDTO enrollmentDTO) {
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
    public ResponseEntity<ApiResponse<String>> deleteEnrollment(@PathVariable("id") Long id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment != null) {
            enrollmentService.deleteEnrollment(id);
            return ResponseEntity.ok(ApiResponse.success("Enrollment record deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.notFound("Enrollment record not found with id: " + escapeHtml(id.toString())));
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getEnrollmentsByStudentId(@PathVariable("studentId") Long studentId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getEnrollmentsByCourseId(@PathVariable("courseId") Long courseId) {
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