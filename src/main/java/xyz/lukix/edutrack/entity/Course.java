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
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonCode;  //课程代码
    private String name;        //课程名
    private Integer credit;     //学分

    // 所属教师
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // 该课程被多个学生选
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();
    
    /**
     * 清理所有字段以防止XSS攻击
     */
    @PrePersist
    @PreUpdate
    public void cleanXss() {
        if (this.lessonCode != null) {
            this.lessonCode = XssCleaner.clean(this.lessonCode);
        }
        if (this.name != null) {
            this.name = XssCleaner.clean(this.name);
        }
    }
}