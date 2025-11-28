package xyz.lukix.edutrack.service;

import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    //查询所有课程
    List<CourseDTO> getAllCourses();

    //根据ID查询课程
    CourseDTO getCourseById(Long id);

    //创建课程
    CourseDTO createCourse(Course course);

    //更新课程
    CourseDTO updateCourse(Long id, Course course);

    //删除课程
    void deleteCourse(Long id);
    
    //根据课程代码查询课程
    CourseDTO getCourseByLessonCode(String lessonCode);
}