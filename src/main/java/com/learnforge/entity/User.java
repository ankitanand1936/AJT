package com.learnforge.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Table: users
 * roles -> users (many users have one role)
 * users -> quiz_results (one-to-many)
 * users -> certificates (one-to-one in practice)
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;   // BCrypt hash

    // Eager (default for ManyToOne) so the login process can read the role
    // without an open Hibernate session.
    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<QuizResult> quizResults = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Certificate certificate;

    public User() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public List<QuizResult> getQuizResults() { return quizResults; }
    public void setQuizResults(List<QuizResult> quizResults) { this.quizResults = quizResults; }
    public Certificate getCertificate() { return certificate; }
    public void setCertificate(Certificate certificate) { this.certificate = certificate; }
}
