package xyz.lukix.edutrack.controller;


import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Course;
import xyz.lukix.edutrack.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(
            @PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public Course createCourse(
            @RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    public Course updateCourse(
            @PathVariable("id") Long id,
            @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(
            @PathVariable("id") Long id) {
        courseService.deleteCourse(id);
    }
}