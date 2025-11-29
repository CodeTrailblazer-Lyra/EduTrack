package xyz.lukix.edutrack.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Integer score;
    private String semester;
    private Boolean passed;
    private LocalDateTime enrolledAt;
}