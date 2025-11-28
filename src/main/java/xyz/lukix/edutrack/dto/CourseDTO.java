package xyz.lukix.edutrack.dto;

import lombok.Data;
import xyz.lukix.edutrack.util.XssCleaner;

@Data
public class CourseDTO {
    private String lessonCode;
    private String name;
    private Integer credit;
    private Long teacherId;
    
    /**
     * 清理所有字段以防止XSS攻击
     */
    public void cleanXss() {
        if (this.lessonCode != null) {
            this.lessonCode = XssCleaner.clean(this.lessonCode);
        }
        if (this.name != null) {
            this.name = XssCleaner.clean(this.name);
        }
    }
}