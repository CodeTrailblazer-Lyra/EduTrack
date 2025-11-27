package xyz.lukix.edutrack.controller;


import org.springframework.web.bind.annotation.*;
import xyz.lukix.edutrack.entity.Student;
import xyz.lukix.edutrack.service.StuService;

import java.util.List;

@RestController
@RequestMapping("/students")

public class StuController {

    private final StuService stuService;

    public StuController(StuService stuService) {
        this.stuService = stuService;
    }

    @GetMapping
    public List<Student> getAllStu() {
        return stuService.getAllStu();
    }

    @GetMapping("/{id}")
    public Student getStuById(
            @PathVariable("id") Long id) {
        return stuService.getStuById(id);
    }

    @PostMapping
    public Student createStu(
            @RequestBody Student student) {
        return stuService.createStu(student);
    }

    @PutMapping("/{id}")
    public Student updateStu(
            @PathVariable("id") Long id,
            @RequestBody Student student) {
        return stuService.updateStu(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStu(
            @PathVariable("id") Long id) {
        stuService.deleteStu(id);
    }
}