package com.heartsignal.dev.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)  //명시적으로 그냥 남겨놓기, null가능!
    private Team team;

    private Boolean chatStatus;
    private int reportCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 추가정보, 현재 DB설계상에는 없어서 추가
     */
    private String refreshToken;
    private String socialId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_chat_id", nullable = true)       //명시적으로 그냥 남겨놓기, null가능!
    private BarChat barChat;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateRoleToUser() {
        this.role = Role.USER;
    }

    public User(){}
}