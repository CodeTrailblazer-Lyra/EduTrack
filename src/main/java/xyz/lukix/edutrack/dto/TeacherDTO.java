package xyz.lukix.edutrack.dto;

import lombok.Data;

@Data
public class TeacherDTO {
    private Long id;
    private String teachNum;    //教职工号
    private String name;        //姓名
}