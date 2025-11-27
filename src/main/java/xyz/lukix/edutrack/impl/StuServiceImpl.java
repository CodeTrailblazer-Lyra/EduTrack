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
        return studentRepository.save(student);
    }

    @Override
    public Student updateStu(Long id, Student student) {
        if (studentRepository.existsById(id)) {
            student.setId(id);  // 使用Lombok生成的setId方法
            return studentRepository.save(student);
        }
        return null;
    }

    @Override
    public void deleteStu(Long id) {
        studentRepository.deleteById(id);
    }
}