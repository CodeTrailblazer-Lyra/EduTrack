package xyz.lukix.edutrack.service.impl;

import org.springframework.stereotype.Service;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.dto.StudentDTO;
import xyz.lukix.edutrack.repository.StudentRepository;
import xyz.lukix.edutrack.service.StuService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StuServiceImpl implements StuService {
    private final StudentRepository studentRepository;

    public StuServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> getAllStu() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStuById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public StudentDTO createStu(Student student) {
        // 检查学号是否已存在
        if (student.getStuNum() != null && 
            studentRepository.existsByStuNum(student.getStuNum())) {
            throw new RuntimeException("学号已存在: " + student.getStuNum());
        }
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    @Override
    public StudentDTO updateStu(Long id, Student student) {
        if (studentRepository.existsById(id)) {
            // 检查学号是否已存在（排除自己）
            if (student.getStuNum() != null && 
                studentRepository.existsByStuNumAndIdNot(student.getStuNum(), id)) {
                throw new RuntimeException("学号已存在: " + student.getStuNum());
            }
            
            student.setId(id);
            Student savedStudent = studentRepository.save(student);
            return convertToDTO(savedStudent);
        }
        return null;
    }

    @Override
    public void deleteStu(Long id) {
        studentRepository.deleteById(id);
    }
    
    @Override
    public StudentDTO getStuByStuNum(String stuNum) {
        return studentRepository.findByStuNum(stuNum)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    /**
     * 将Student实体转换为StudentDTO
     * @param student Student实体
     * @return StudentDTO
     */
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setStuNum(student.getStuNum());
        dto.setName(student.getName());
        dto.setMajor(student.getMajor());
        return dto;
    }
}