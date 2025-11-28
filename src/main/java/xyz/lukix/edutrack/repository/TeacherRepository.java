package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByTeachNum(String teachNum);
    boolean existsByTeachNumAndIdNot(String teachNum, Long id);
    Optional<Teacher> findByTeachNum(String teachNum);
}