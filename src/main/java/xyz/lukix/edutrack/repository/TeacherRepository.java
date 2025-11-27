package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}