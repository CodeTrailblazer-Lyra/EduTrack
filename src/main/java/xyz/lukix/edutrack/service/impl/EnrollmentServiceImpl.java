package xyz.lukix.edutrack.service.impl;

import org.springframework.stereotype.Service;
import xyz.lukix.edutrack.dto.EnrollmentDTO;
import xyz.lukix.edutrack.entity.Enrollment;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.repository.EnrollmentRepository;
import xyz.lukix.edutrack.repository.StudentRepository;
import xyz.lukix.edutrack.repository.CourseRepository;
import xyz.lukix.edutrack.service.EnrollmentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    
    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    
    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        // 检查学生和课程是否存在
        Optional<Student> studentOpt = studentRepository.findById(enrollmentDTO.getStudentId());
        Optional<Course> courseOpt = courseRepository.findById(enrollmentDTO.getCourseId());
        
        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            return null;
        }
        
        // 检查是否已经选过这门课
        if (enrollmentRepository.existsByStudentIdAndCourseId(
                enrollmentDTO.getStudentId(), 
                enrollmentDTO.getCourseId())) {
            throw new RuntimeException("该学生已选过此课程");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(studentOpt.get());
        enrollment.setCourse(courseOpt.get());
        enrollment.setScore(enrollmentDTO.getScore());
        enrollment.setSemester(enrollmentDTO.getSemester());
        enrollment.setPassed(enrollmentDTO.getPassed());
        enrollment.setEnrolledAt(enrollmentDTO.getEnrolledAt() != null ? 
                enrollmentDTO.getEnrolledAt() : LocalDateTime.now());
        
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(savedEnrollment);
    }
    
    @Override
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(id);
        if (enrollmentOpt.isEmpty()) {
            return null;
        }
        
        Enrollment enrollment = enrollmentOpt.get();
        
        // 如果要更新学生或课程，需要检查是否存在
        if (enrollmentDTO.getStudentId() != null && 
            !enrollmentDTO.getStudentId().equals(enrollment.getStudent().getId())) {
            Optional<Student> studentOpt = studentRepository.findById(enrollmentDTO.getStudentId());
            if (studentOpt.isEmpty()) {
                throw new RuntimeException("学生不存在，ID: " + enrollmentDTO.getStudentId());
            }
            enrollment.setStudent(studentOpt.get());
        }
        
        if (enrollmentDTO.getCourseId() != null && 
            !enrollmentDTO.getCourseId().equals(enrollment.getCourse().getId())) {
            Optional<Course> courseOpt = courseRepository.findById(enrollmentDTO.getCourseId());
            if (courseOpt.isEmpty()) {
                throw new RuntimeException("课程不存在，ID: " + enrollmentDTO.getCourseId());
            }
            enrollment.setCourse(courseOpt.get());
        }
        
        // 更新其他字段
        if (enrollmentDTO.getScore() != null) {
            enrollment.setScore(enrollmentDTO.getScore());
        }
        
        if (enrollmentDTO.getSemester() != null) {
            enrollment.setSemester(enrollmentDTO.getSemester());
        }
        
        if (enrollmentDTO.getPassed() != null) {
            enrollment.setPassed(enrollmentDTO.getPassed());
        }
        
        if (enrollmentDTO.getEnrolledAt() != null) {
            enrollment.setEnrolledAt(enrollmentDTO.getEnrolledAt());
        }
        
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(savedEnrollment);
    }
    
    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
    
    @Override
    public List<EnrollmentDTO> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 将Enrollment实体转换为EnrollmentDTO
     * @param enrollment Enrollment实体
     * @return EnrollmentDTO
     */
    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        if (enrollment.getStudent() != null) {
            dto.setStudentId(enrollment.getStudent().getId());
        }
        if (enrollment.getCourse() != null) {
            dto.setCourseId(enrollment.getCourse().getId());
        }
        dto.setScore(enrollment.getScore());
        dto.setSemester(enrollment.getSemester());
        dto.setPassed(enrollment.getPassed());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }
}