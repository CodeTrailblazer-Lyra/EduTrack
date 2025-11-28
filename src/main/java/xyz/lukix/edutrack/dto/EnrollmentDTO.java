package xyz.lukix.edutrack.dto;

import lombok.Data;
import xyz.lukix.edutrack.util.XssCleaner;

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
    
    /**
     * 清理所有字段以防止XSS攻击
     */
    public void cleanXss() {
        if (this.semester != null) {
            this.semester = XssCleaner.clean(this.semester);
        }
    }
}