package xyz.lukix.edutrack.service;

import xyz.lukix.edutrack.entity.Student;

import java.util.List;

public interface StuService {
    //查询所有学生
    List<Student> getAllStu();

    //根据ID查询学生
    Student getStuById(Long id);

    //创建学生
    Student createStu(Student student);

    //更新学生
    Student updateStu(Long id, Student student);

    //删除学生
    void deleteStu(Long id);
    
    //根据学号查询学生
    Student getStuByStuNum(String stuNum);
}