package xyz.lukix.edutrack.dto;

import lombok.Data;
import xyz.lukix.edutrack.util.XssCleaner;

@Data
public class CourseDTO {
    private Long id;
    private String lessonCode;  //课程代码
    private String name;        //课程名
    private Integer credit;     //学分
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