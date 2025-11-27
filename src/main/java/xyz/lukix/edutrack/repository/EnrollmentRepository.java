package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}