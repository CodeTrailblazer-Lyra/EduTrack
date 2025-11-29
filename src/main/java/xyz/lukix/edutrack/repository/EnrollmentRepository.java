package xyz.lukix.edutrack.repository;

import xyz.lukix.edutrack.entity.Enrollment;

import java.util.List;

public interface EnrollmentRepository {
    
    List<Enrollment> findAll();
    
    Enrollment findById(Long id);
    
    void insert(Enrollment enrollment);
    
    void update(Enrollment enrollment);
    
    void deleteById(Long id);
    
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    
    List<Enrollment> findByStudentId(Long studentId);
    
    List<Enrollment> findByCourseId(Long courseId);
    
    boolean existsById(Long id);
}