package xyz.lukix.edutrack.service;

import xyz.lukix.edutrack.dto.EnrollmentDTO;
import xyz.lukix.edutrack.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentDTO> getAllEnrollments();
    EnrollmentDTO getEnrollmentById(Long id);
    EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO);
    EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO);
    void deleteEnrollment(Long id);
    
    // 根据学生ID查找选课记录
    List<EnrollmentDTO> getEnrollmentsByStudentId(Long studentId);
    
    // 根据课程ID查找选课记录
    List<EnrollmentDTO> getEnrollmentsByCourseId(Long courseId);
}