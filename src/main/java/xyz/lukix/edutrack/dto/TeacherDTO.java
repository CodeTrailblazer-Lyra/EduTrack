package xyz.lukix.edutrack.dto;

import lombok.Data;
import xyz.lukix.edutrack.util.XssCleaner;

@Data
public class TeacherDTO {
    private String teachNum;
    private String name;
    
    /**
     * 清理所有字段以防止XSS攻击
     */
    public void cleanXss() {
        if (this.teachNum != null) {
            this.teachNum = XssCleaner.clean(this.teachNum);
        }
        if (this.name != null) {
            this.name = XssCleaner.clean(this.name);
        }
    }
}