package xyz.lukix.edutrack.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import xyz.lukix.edutrack.controller.StuController;
import xyz.lukix.edutrack.controller.TeacherController;
import xyz.lukix.edutrack.controller.CourseController;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.entity.Teacher;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.service.StuService;
import xyz.lukix.edutrack.service.TeacherService;
import xyz.lukix.edutrack.service.CourseService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({StuController.class, TeacherController.class, CourseController.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class ApiDocumentationTest extends BaseApiTest {

    @MockBean
    private StuService stuService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private CourseService courseService;

    private final FieldDescriptor[] studentFieldDescriptors = {
            fieldWithPath("id").description("学生ID"),
            fieldWithPath("stuNum").description("学号"),
            fieldWithPath("name").description("学生姓名"),
            fieldWithPath("major").description("专业")
    };

    private final FieldDescriptor[] teacherFieldDescriptors = {
            fieldWithPath("id").description("教师ID"),
            fieldWithPath("teachNum").description("教职工号"),
            fieldWithPath("name").description("教师姓名")
    };

    private final FieldDescriptor[] courseFieldDescriptors = {
            fieldWithPath("id").description("课程ID"),
            fieldWithPath("lessonCode").description("课程代码"),
            fieldWithPath("name").description("课程名称"),
            fieldWithPath("credit").description("学分")
    };

    @Test
    public void getAllStudents() throws Exception {
        List<Student> students = Arrays.asList(createStudent(1L, "S001", "张三", "计算机科学"));
        when(stuService.getAllStu()).thenReturn(students);

        this.mockMvc.perform(get("/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("students-get-all",
                        responseFields(
                                fieldWithPath("[]").description("学生列表"),
                                fieldWithPath("[].id").description("学生ID"),
                                fieldWithPath("[].stuNum").description("学号"),
                                fieldWithPath("[].name").description("学生姓名"),
                                fieldWithPath("[].major").description("专业")
                        )));
    }

    @Test
    public void getStudentById() throws Exception {
        Student student = createStudent(1L, "S001", "张三", "计算机科学");
        when(stuService.getStuById(1L)).thenReturn(student);

        this.mockMvc.perform(get("/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("students-get-by-id",
                        pathParameters(
                                parameterWithName("id").description("学生ID")
                        ),
                        responseFields(studentFieldDescriptors)));
    }

    @Test
    public void createStudent() throws Exception {
        Student student = createStudent(1L, "S001", "张三", "计算机科学");
        when(stuService.createStu(org.mockito.ArgumentMatchers.any(Student.class))).thenReturn(student);

        this.mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stuNum\":\"S001\",\"name\":\"张三\",\"major\":\"计算机科学\"}"))
                .andExpect(status().isOk())
                .andDo(document("students-create",
                        requestFields(
                                fieldWithPath("stuNum").description("学号"),
                                fieldWithPath("name").description("学生姓名"),
                                fieldWithPath("major").description("专业")
                        ),
                        responseFields(studentFieldDescriptors)));
    }

    @Test
    public void updateStudent() throws Exception {
        Student student = createStudent(1L, "S001", "张三", "计算机科学");
        when(stuService.updateStu(org.mockito.ArgumentMatchers.eq(1L), 
                org.mockito.ArgumentMatchers.any(Student.class))).thenReturn(student);

        this.mockMvc.perform(put("/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stuNum\":\"S001\",\"name\":\"张三\",\"major\":\"计算机科学\"}"))
                .andExpect(status().isOk())
                .andDo(document("students-update",
                        pathParameters(
                                parameterWithName("id").description("学生ID")
                        ),
                        requestFields(
                                fieldWithPath("stuNum").description("学号"),
                                fieldWithPath("name").description("学生姓名"),
                                fieldWithPath("major").description("专业")
                        ),
                        responseFields(studentFieldDescriptors)));
    }

    @Test
    public void deleteStudent() throws Exception {
        this.mockMvc.perform(delete("/students/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("students-delete",
                        pathParameters(
                                parameterWithName("id").description("学生ID")
                        )));
    }

    @Test
    public void getAllTeachers() throws Exception {
        List<Teacher> teachers = Arrays.asList(createTeacher(1L, "T001", "李老师"));
        when(teacherService.getAllTeachers()).thenReturn(teachers);

        this.mockMvc.perform(get("/teachers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("teachers-get-all",
                        responseFields(
                                fieldWithPath("[]").description("教师列表"),
                                fieldWithPath("[].id").description("教师ID"),
                                fieldWithPath("[].teachNum").description("教职工号"),
                                fieldWithPath("[].name").description("教师姓名")
                        )));
    }

    @Test
    public void getTeacherById() throws Exception {
        Teacher teacher = createTeacher(1L, "T001", "李老师");
        when(teacherService.getTeacherById(1L)).thenReturn(teacher);

        this.mockMvc.perform(get("/teachers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("teachers-get-by-id",
                        pathParameters(
                                parameterWithName("id").description("教师ID")
                        ),
                        responseFields(teacherFieldDescriptors)));
    }

    @Test
    public void createTeacher() throws Exception {
        Teacher teacher = createTeacher(1L, "T001", "李老师");
        when(teacherService.createTeacher(org.mockito.ArgumentMatchers.any(Teacher.class))).thenReturn(teacher);

        this.mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"teachNum\":\"T001\",\"name\":\"李老师\"}"))
                .andExpect(status().isOk())
                .andDo(document("teachers-create",
                        requestFields(
                                fieldWithPath("teachNum").description("教职工号"),
                                fieldWithPath("name").description("教师姓名")
                        ),
                        responseFields(teacherFieldDescriptors)));
    }

    @Test
    public void updateTeacher() throws Exception {
        Teacher teacher = createTeacher(1L, "T001", "李老师");
        when(teacherService.updateTeacher(org.mockito.ArgumentMatchers.eq(1L), 
                org.mockito.ArgumentMatchers.any(Teacher.class))).thenReturn(teacher);

        this.mockMvc.perform(put("/teachers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"teachNum\":\"T001\",\"name\":\"李老师\"}"))
                .andExpect(status().isOk())
                .andDo(document("teachers-update",
                        pathParameters(
                                parameterWithName("id").description("教师ID")
                        ),
                        requestFields(
                                fieldWithPath("teachNum").description("教职工号"),
                                fieldWithPath("name").description("教师姓名")
                        ),
                        responseFields(teacherFieldDescriptors)));
    }

    @Test
    public void deleteTeacher() throws Exception {
        this.mockMvc.perform(delete("/teachers/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("teachers-delete",
                        pathParameters(
                                parameterWithName("id").description("教师ID")
                        )));
    }

    @Test
    public void getAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(createCourse(1L, "C001", "数学", 4));
        when(courseService.getAllCourses()).thenReturn(courses);

        this.mockMvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("courses-get-all",
                        responseFields(
                                fieldWithPath("[]").description("课程列表"),
                                fieldWithPath("[].id").description("课程ID"),
                                fieldWithPath("[].lessonCode").description("课程代码"),
                                fieldWithPath("[].name").description("课程名称"),
                                fieldWithPath("[].credit").description("学分")
                        )));
    }

    @Test
    public void getCourseById() throws Exception {
        Course course = createCourse(1L, "C001", "数学", 4);
        when(courseService.getCourseById(1L)).thenReturn(course);

        this.mockMvc.perform(get("/courses/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("courses-get-by-id",
                        pathParameters(
                                parameterWithName("id").description("课程ID")
                        ),
                        responseFields(courseFieldDescriptors)));
    }

    @Test
    public void createCourse() throws Exception {
        Course course = createCourse(1L, "C001", "数学", 4);
        when(courseService.createCourse(org.mockito.ArgumentMatchers.any(Course.class))).thenReturn(course);

        this.mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lessonCode\":\"C001\",\"name\":\"数学\",\"credit\":4}"))
                .andExpect(status().isOk())
                .andDo(document("courses-create",
                        requestFields(
                                fieldWithPath("lessonCode").description("课程代码"),
                                fieldWithPath("name").description("课程名称"),
                                fieldWithPath("credit").description("学分")
                        ),
                        responseFields(courseFieldDescriptors)));
    }

    @Test
    public void updateCourse() throws Exception {
        Course course = createCourse(1L, "C001", "数学", 4);
        when(courseService.updateCourse(org.mockito.ArgumentMatchers.eq(1L), 
                org.mockito.ArgumentMatchers.any(Course.class))).thenReturn(course);

        this.mockMvc.perform(put("/courses/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lessonCode\":\"C001\",\"name\":\"数学\",\"credit\":4}"))
                .andExpect(status().isOk())
                .andDo(document("courses-update",
                        pathParameters(
                                parameterWithName("id").description("课程ID")
                        ),
                        requestFields(
                                fieldWithPath("lessonCode").description("课程代码"),
                                fieldWithPath("name").description("课程名称"),
                                fieldWithPath("credit").description("学分")
                        ),
                        responseFields(courseFieldDescriptors)));
    }

    @Test
    public void deleteCourse() throws Exception {
        this.mockMvc.perform(delete("/courses/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("courses-delete",
                        pathParameters(
                                parameterWithName("id").description("课程ID")
                        )));
    }

    private Student createStudent(Long id, String stuNum, String name, String major) {
        Student student = new Student();
        student.setId(id);
        student.setStuNum(stuNum);
        student.setName(name);
        student.setMajor(major);
        return student;
    }

    private Teacher createTeacher(Long id, String teachNum, String name) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setTeachNum(teachNum);
        teacher.setName(name);
        return teacher;
    }

    private Course createCourse(Long id, String lessonCode, String name, Integer credit) {
        Course course = new Course();
        course.setId(id);
        course.setLessonCode(lessonCode);
        course.setName(name);
        course.setCredit(credit);
        return course;
    }
}