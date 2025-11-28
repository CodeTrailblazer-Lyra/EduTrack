package xyz.lukix.edutrack.service;

import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {
    //查询所有教师
    List<TeacherDTO> getAllTeachers();

    //根据ID查询教师
    TeacherDTO getTeacherById(Long id);

    //创建教师
    TeacherDTO createTeacher(Teacher teacher);

    //更新教师
    TeacherDTO updateTeacher(Long id, Teacher teacher);

    //删除教师
    void deleteTeacher(Long id);
    
    //根据教职工号查询教师
    TeacherDTO getTeacherByTeachNum(String teachNum);
}