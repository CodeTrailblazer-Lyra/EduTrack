package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Course;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByLessonCode(String lessonCode);
    boolean existsByLessonCodeAndIdNot(String lessonCode, Long id);
    Optional<Course> findByLessonCode(String lessonCode);
}