package xyz.lukix.edutrack.service;

import xyz.lukix.edutrack.entity.Course;

import java.util.List;

public interface CourseService {
    //查询所有课程
    List<Course> getAllCourses();

    //根据ID查询课程
    Course getCourseById(Long id);

    //创建课程
    Course createCourse(Course course);

    //更新课程
    Course updateCourse(Long id, Course course);

    //删除课程
    void deleteCourse(Long id);
}