package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
