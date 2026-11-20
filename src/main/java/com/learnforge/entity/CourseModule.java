package com.learnforge.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Table: modules  (modules -> lessons is one-to-many)
 *
 * The class is called CourseModule (not Module) to avoid a clash with java.lang.Module.
 */
@Entity
@Table(name = "modules")
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true, length = 150)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @OrderBy("lessonOrder ASC, id ASC")
    private List<Lesson> lessons = new ArrayList<>();

    public CourseModule() {
    }

    public CourseModule(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }
}
