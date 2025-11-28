package xyz.lukix.edutrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.lukix.edutrack.util.XssCleaner;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stuNum;   //学号
    private String name;     //姓名
    private String major;    //专业

    // 一个学生有多条选课记录
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();
    
    /**
     * 清理所有字段以防止XSS攻击
     */
    @PrePersist
    @PreUpdate
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