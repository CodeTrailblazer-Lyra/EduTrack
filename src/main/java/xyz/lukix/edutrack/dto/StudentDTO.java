package xyz.lukix.edutrack.dto;

import lombok.Data;
import xyz.lukix.edutrack.util.XssCleaner;

@Data
public class StudentDTO {
    private String stuNum;
    private String name;
    private String major;
    
    /**
     * 清理所有字段以防止XSS攻击
     */
    public void cleanXss() {
        if (this.stuNum != null) {
            this.stuNum = XssCleaner.clean(this.stuNum);
        }
        if (this.name != null) {
            this.name = XssCleaner.clean(this.name);
        }
        if (this.major != null) {
            this.major = XssCleaner.clean(this.major);
        }
    }
}