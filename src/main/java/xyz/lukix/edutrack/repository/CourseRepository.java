package xyz.lukix.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lukix.edutrack.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}