package xyz.lukix.edutrack.repository;

import xyz.lukix.edutrack.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    
    List<Student> findAll();
    
    Optional<Student> findById(Long id);
    
    void insert(Student student);
    
    void update(Student student);
    
    void deleteById(Long id);
    
    boolean existsByStuNum(String stuNum);
    
    boolean existsByStuNumAndIdNot(String stuNum, Long id);
    
    Optional<Student> findByStuNum(String stuNum);
    
    boolean existsById(Long id);
}