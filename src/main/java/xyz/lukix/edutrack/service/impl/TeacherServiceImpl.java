package xyz.lukix.edutrack.service.impl;

import org.springframework.stereotype.Service;
import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.repository.TeacherRepository;
import xyz.lukix.edutrack.service.TeacherService;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        // 检查教职工号是否已存在
        if (teacher.getTeachNum() != null && 
            teacherRepository.existsByTeachNum(teacher.getTeachNum())) {
            throw new RuntimeException("教职工号已存在: " + teacher.getTeachNum());
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher) {
        if (teacherRepository.existsById(id)) {
            // 检查教职工号是否已存在（排除自己）
            if (teacher.getTeachNum() != null && 
                teacherRepository.existsByTeachNumAndIdNot(teacher.getTeachNum(), id)) {
                throw new RuntimeException("教职工号已存在: " + teacher.getTeachNum());
            }
            
            teacher.setId(id);
            return teacherRepository.save(teacher);
        }
        return null;
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
    
    @Override
    public Teacher getTeacherByTeachNum(String teachNum) {
        return teacherRepository.findByTeachNum(teachNum).orElse(null);
    }
}