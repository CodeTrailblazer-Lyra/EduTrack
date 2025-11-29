package xyz.lukix.edutrack.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String lessonCode;  //课程代码
    private String name;        //课程名
    private Integer credit;     //学分
    private Long teacherId;
}