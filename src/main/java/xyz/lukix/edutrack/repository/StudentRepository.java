package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStuNum(String stuNum);
    boolean existsByStuNumAndIdNot(String stuNum, Long id);
    Optional<Student> findByStuNum(String stuNum);
}