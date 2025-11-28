package xyz.lukix.edutrack.service;

import xyz.lukix.edutrack.dto.EnrollmentDTO;
import xyz.lukix.edutrack.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();
    Enrollment getEnrollmentById(Long id);
    Enrollment createEnrollment(EnrollmentDTO enrollmentDTO);
    Enrollment updateEnrollment(Long id, EnrollmentDTO enrollmentDTO);
    void deleteEnrollment(Long id);
    
    // 根据学生ID查找选课记录
    List<Enrollment> getEnrollmentsByStudentId(Long studentId);
    
    // 根据课程ID查找选课记录
    List<Enrollment> getEnrollmentsByCourseId(Long courseId);
}