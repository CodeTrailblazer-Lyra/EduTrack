package xyz.lukix.edutrack.repository;

import xyz.lukix.edutrack.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository {
    
    List<Teacher> findAll();
    
    Optional<Teacher> findById(Long id);
    
    void insert(Teacher teacher);
    
    void update(Teacher teacher);
    
    void deleteById(Long id);
    
    boolean existsByTeachNum(String teachNum);
    
    boolean existsByTeachNumAndIdNot(String teachNum, Long id);
    
    Optional<Teacher> findByTeachNum(String teachNum);
    
    boolean existsById(Long id);
}