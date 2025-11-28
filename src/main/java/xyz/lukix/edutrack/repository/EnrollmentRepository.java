package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Enrollment;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
}