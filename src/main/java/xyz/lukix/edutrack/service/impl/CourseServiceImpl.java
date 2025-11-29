package xyz.lukix.edutrack.service.impl;

import org.springframework.stereotype.Service;
import xyz.lukix.edutrack.dto.CourseDTO;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.repository.CourseRepository;
import xyz.lukix.edutrack.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CourseDTO getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    @Override
    public CourseDTO createCourse(Course course) {
        // 检查课程代码是否已存在
        if (course.getLessonCode() != null && 
            courseRepository.existsByLessonCode(course.getLessonCode())) {
            throw new RuntimeException("课程代码已存在: " + course.getLessonCode());
        }
        courseRepository.insert(course);
        return convertToDTO(course);
    }
    
    @Override
    public CourseDTO updateCourse(Long id, Course course) {
        if (courseRepository.existsById(id)) {
            // 检查课程代码是否已存在（排除自己）
            if (course.getLessonCode() != null && 
                courseRepository.existsByLessonCodeAndIdNot(course.getLessonCode(), id)) {
                throw new RuntimeException("课程代码已存在: " + course.getLessonCode());
            }
            
            course.setId(id);
            courseRepository.update(course);
            return convertToDTO(course);
        }
        return null;
    }
    
    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    
    @Override
    public CourseDTO getCourseByLessonCode(String lessonCode) {
        return courseRepository.findByLessonCode(lessonCode)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    /**
     * 将Course实体转换为CourseDTO
     * @param course Course实体
     * @return CourseDTO
     */
    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setLessonCode(course.getLessonCode());
        dto.setName(course.getName());
        dto.setCredit(course.getCredit());
        if (course.getTeacher() != null) {
            dto.setTeacherId(course.getTeacher().getId());
        }
        return dto;
    }
}