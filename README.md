# EduTrack 教育管理系统

EduTrack 是一个基于 Spring Boot 和 MyBatis 的教育管理系统后端服务，用于管理学生、教师、课程和选课信息。

## 🏗️ 技术架构

### 后端技术栈
- **Spring Boot 4.0.0** - 核心框架
- **MyBatis 3.5.14** - 数据访问层框架
- **PostgreSQL** - 关系型数据库
- **Maven 3.5+** - 项目构建工具
- **Java 21** - 编程语言

### 主要功能模块
- 学生信息管理
- 教师信息管理
- 课程信息管理
- 选课管理
- 成绩管理

## 📁 项目结构

```
EduTrackServer/
├── src/main/java/xyz/lukix/edutrack/
│   ├── config/                  # 配置类
│   ├── controller/              # 控制器层
│   ├── dto/                     # 数据传输对象
│   ├── entity/                  # 实体类
│   ├── repository/              # 数据访问层
│   ├── service/                 # 业务逻辑层
│   └── util/                   # 工具类
├── src/main/resources/
│   ├── mapper/                 # MyBatis XML 映射文件
│   └── application.yml         # 配置文件
└── pom.xml                     # Maven 配置文件
```

## 🚀 快速开始

### 环境要求
- Java 21+
- Maven 3.5+
- PostgreSQL 数据库

### 安装步骤

1. 克隆项目到本地：
```bash
git clone <项目地址>
cd EduTrackServer
```

2. 配置数据库连接：
在 `src/main/resources/application.yml` 中修改数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database
    username: your_username
    password: your_password
```

3. 编译和运行项目：
```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

4. 访问 API 文档：
项目启动后，可通过以下地址访问 Swagger API 文档：
```
http://localhost:8080/swagger-ui.html
```

## 📊 API 接口

### 学生管理
- `GET /students` - 获取所有学生
- `GET /students/{id}` - 根据ID获取学生
- `GET /students/num/{stuNum}` - 根据学号获取学生
- `POST /students` - 创建学生
- `PUT /students/{id}` - 更新学生
- `DELETE /students/{id}` - 删除学生

### 教师管理
- `GET /teachers` - 获取所有教师
- `GET /teachers/{id}` - 根据ID获取教师
- `GET /teachers/num/{teachNum}` - 根据教职工号获取教师
- `POST /teachers` - 创建教师
- `PUT /teachers/{id}` - 更新教师
- `DELETE /teachers/{id}` - 删除教师

### 课程管理
- `GET /courses` - 获取所有课程
- `GET /courses/{id}` - 根据ID获取课程
- `GET /courses/code/{lessonCode}` - 根据课程代码获取课程
- `POST /courses` - 创建课程
- `PUT /courses/{id}` - 更新课程
- `DELETE /courses/{id}` - 删除课程

## 🔧 数据库设计

### 主要实体表

#### 学生表 (student)
| 字段名 | 类型 | 描述 |
|-------|------|-----|
| id | BIGINT | 主键 |
| stu_num | VARCHAR | 学号 |
| name | VARCHAR | 姓名 |
| major | VARCHAR | 专业 |

#### 教师表 (teacher)
| 字段名 | 类型 | 描述 |
|-------|------|-----|
| id | BIGINT | 主键 |
| teach_num | VARCHAR | 教职工号 |
| name | VARCHAR | 姓名 |

#### 课程表 (course)
| 字段名 | 类型 | 描述 |
|-------|------|-----|
| id | BIGINT | 主键 |
| lesson_code | VARCHAR | 课程代码 |
| name | VARCHAR | 课程名称 |
| credit | INTEGER | 学分 |
| teacher_id | BIGINT | 教师ID |

#### 选课表 (enrollment)
| 字段名 | 类型 | 描述 |
|-------|------|-----|
| id | BIGINT | 主键 |
| student_id | BIGINT | 学生ID |
| course_id | BIGINT | 课程ID |
| score | INTEGER | 成绩 |
| semester | VARCHAR | 学期 |
| passed | BOOLEAN | 是否通过 |
| enrolled_at | TIMESTAMP | 选课时间 |

## 📦 依赖管理

项目使用 Maven 进行依赖管理，主要依赖包括：

```xml
<!-- Spring Boot 核心依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- 数据库相关 -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- MyBatis -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>

<!-- API 文档 -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

## 🛠️ 开发指南

### 代码规范
- 使用 Lombok 简化实体类代码
- 遵循 RESTful API 设计规范
- 使用 DTO 进行数据传输
- 统一的响应格式封装

### 数据访问层
项目使用 MyBatis 作为 ORM 框架，通过 XML 映射文件定义 SQL 语句：

```java
// Mapper 接口定义
public interface StudentRepository {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    void insert(Student student);
    // ...
}
```

### 业务逻辑层
服务层负责处理业务逻辑，通过依赖注入使用 Repository：

```java
@Service
public class StuServiceImpl implements StuService {
    private final StudentRepository studentRepository;
    
    public StudentDTO createStu(Student student) {
        // 业务逻辑处理
        studentRepository.insert(student);
        return convertToDTO(student);
    }
    // ...
}
```

## 📝 许可证

本项目采用 MIT 许可证，详情请见 [LICENSE](LICENSE) 文件。

## 👥 贡献者

- 项目开发团队

## 📞 联系方式

如有问题或建议，请联系项目维护者。