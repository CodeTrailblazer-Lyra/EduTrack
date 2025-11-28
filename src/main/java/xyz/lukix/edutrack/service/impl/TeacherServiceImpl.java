package xyz.lukix.edutrack.service.impl;

import org.springframework.stereotype.Service;
import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.dto.TeacherDTO;
import xyz.lukix.edutrack.repository.TeacherRepository;
import xyz.lukix.edutrack.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public TeacherDTO createTeacher(Teacher teacher) {
        // 检查教职工号是否已存在
        if (teacher.getTeachNum() != null && 
            teacherRepository.existsByTeachNum(teacher.getTeachNum())) {
            throw new RuntimeException("教职工号已存在: " + teacher.getTeachNum());
        }
        Teacher savedTeacher = teacherRepository.save(teacher);
        return convertToDTO(savedTeacher);
    }

    @Override
    public TeacherDTO updateTeacher(Long id, Teacher teacher) {
        if (teacherRepository.existsById(id)) {
            // 检查教职工号是否已存在（排除自己）
            if (teacher.getTeachNum() != null && 
                teacherRepository.existsByTeachNumAndIdNot(teacher.getTeachNum(), id)) {
                throw new RuntimeException("教职工号已存在: " + teacher.getTeachNum());
            }
            
            teacher.setId(id);
            Teacher savedTeacher = teacherRepository.save(teacher);
            return convertToDTO(savedTeacher);
        }
        return null;
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
    
    @Override
    public TeacherDTO getTeacherByTeachNum(String teachNum) {
        return teacherRepository.findByTeachNum(teachNum)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    /**
     * 将Teacher实体转换为TeacherDTO
     * @param teacher Teacher实体
     * @return TeacherDTO
     */
    private TeacherDTO convertToDTO(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setTeachNum(teacher.getTeachNum());
        dto.setName(teacher.getName());
        return dto;
    }
}