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
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teachNum;    //教职工号
    private String name;        //姓名

    // 一个教师教多门课程
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();
    
    /**
     * 清理所有字段以防止XSS攻击
     */
    @PrePersist
    @PreUpdate
    public void cleanXss() {
        if (this.teachNum != null) {
            this.teachNum = XssCleaner.clean(this.teachNum);
        }
        if (this.name != null) {
            this.name = XssCleaner.clean(this.name);
        }
    }
}