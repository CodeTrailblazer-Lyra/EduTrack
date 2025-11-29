package xyz.lukix.edutrack.repository;

import xyz.lukix.edutrack.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    
    List<Course> findAll();
    
    Optional<Course> findById(Long id);
    
    void insert(Course course);
    
    void update(Course course);
    
    void deleteById(Long id);
    
    boolean existsByLessonCode(String lessonCode);
    
    boolean existsByLessonCodeAndIdNot(String lessonCode, Long id);
    
    Optional<Course> findByLessonCode(String lessonCode);
    
    boolean existsById(Long id);
}