package xyz.lukix.edutrack.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String stuNum;   //学号
    private String name;     //姓名
    private String major;    //专业
}