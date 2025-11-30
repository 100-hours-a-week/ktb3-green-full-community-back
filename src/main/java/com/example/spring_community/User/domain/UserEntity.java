package com.example.spring_community.User.domain;

import com.example.spring_community.Image.domain.ImageEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.query.sqm.FetchClauseType;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private ImageEntity profileImg;

    protected UserEntity() {}

    @Builder(toBuilder = true)
    public UserEntity(Long userId, String email, String password, String nickname, ImageEntity profileImg, Boolean active, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.active = active;
        this.role = role;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImg(ImageEntity profileImg) {
        this.profileImg = profileImg;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
