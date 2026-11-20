package com.learnforge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Table: certificates
 * users -> certificates is one-to-one in practice: the UNIQUE constraint on user_id
 * allows at most one certificate per student.
 */
@Entity
@Table(name = "certificates")
public class Certificate {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "certificate_code", nullable = false, unique = true, length = 40)
    private String certificateCode;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    public Certificate() {
    }

    public Certificate(User user, String certificateCode) {
        this.user = user;
        this.certificateCode = certificateCode;
        this.issuedAt = LocalDateTime.now();
    }

    /** Readable date for JSP pages (not stored in the database). */
    public String getIssuedAtText() {
        return issuedAt == null ? "" : issuedAt.format(FORMAT);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getCertificateCode() { return certificateCode; }
    public void setCertificateCode(String certificateCode) { this.certificateCode = certificateCode; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
}
