package xyz.lukix.edutrack.service.impl;

import org.springframework.stereotype.Service;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.repository.CourseRepository;
import xyz.lukix.edutrack.repository.TeacherRepository;
import xyz.lukix.edutrack.service.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course createCourse(Course course) {
        // 检查课程代码是否已存在
        if (course.getLessonCode() != null && 
            courseRepository.existsByLessonCode(course.getLessonCode())) {
            throw new RuntimeException("课程代码已存在: " + course.getLessonCode());
        }
        
        // 如果设置了教师，验证教师是否存在
        if (course.getTeacher() != null && course.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(course.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("教师不存在，ID: " + course.getTeacher().getId()));
            course.setTeacher(teacher);
        }
        
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        if (courseRepository.existsById(id)) {
            // 检查课程代码是否已存在（排除自己）
            if (course.getLessonCode() != null && 
                courseRepository.existsByLessonCodeAndIdNot(course.getLessonCode(), id)) {
                throw new RuntimeException("课程代码已存在: " + course.getLessonCode());
            }
            
            // 如果设置了教师，验证教师是否存在
            if (course.getTeacher() != null && course.getTeacher().getId() != null) {
                Teacher teacher = teacherRepository.findById(course.getTeacher().getId())
                        .orElseThrow(() -> new RuntimeException("教师不存在，ID: " + course.getTeacher().getId()));
                course.setTeacher(teacher);
            }
            
            course.setId(id);
            return courseRepository.save(course);
        }
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    
    @Override
    public Course getCourseByLessonCode(String lessonCode) {
        return courseRepository.findByLessonCode(lessonCode).orElse(null);
    }
}