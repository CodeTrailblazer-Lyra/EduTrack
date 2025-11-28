package xyz.lukix.edutrack.service.impl;

import org.springframework.stereotype.Service;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.repository.StudentRepository;
import xyz.lukix.edutrack.service.StuService;

import java.util.List;

@Service
public class StuServiceImpl implements StuService {
    private final StudentRepository studentRepository;

    public StuServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStu() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStuById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student createStu(Student student) {
        // 检查学号是否已存在
        if (student.getStuNum() != null && 
            studentRepository.existsByStuNum(student.getStuNum())) {
            throw new RuntimeException("学号已存在: " + student.getStuNum());
        }
        return studentRepository.save(student);
    }

    @Override
    public Student updateStu(Long id, Student student) {
        if (studentRepository.existsById(id)) {
            // 检查学号是否已存在（排除自己）
            if (student.getStuNum() != null && 
                studentRepository.existsByStuNumAndIdNot(student.getStuNum(), id)) {
                throw new RuntimeException("学号已存在: " + student.getStuNum());
            }
            
            student.setId(id);
            return studentRepository.save(student);
        }
        return null;
    }

    @Override
    public void deleteStu(Long id) {
        studentRepository.deleteById(id);
    }
    
    @Override
    public Student getStuByStuNum(String stuNum) {
        return studentRepository.findByStuNum(stuNum).orElse(null);
    }
}