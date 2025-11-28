package xyz.lukix.edutrack.service;

import xyz.lukix.edutrack.entity.Teacher;

import java.util.List;

public interface TeacherService {
    //查询所有教师
    List<Teacher> getAllTeachers();

    //根据ID查询教师
    Teacher getTeacherById(Long id);

    //创建教师
    Teacher createTeacher(Teacher teacher);

    //更新教师
    Teacher updateTeacher(Long id, Teacher teacher);

    //删除教师
    void deleteTeacher(Long id);
    
    //根据教职工号查询教师
    Teacher getTeacherByTeachNum(String teachNum);
}