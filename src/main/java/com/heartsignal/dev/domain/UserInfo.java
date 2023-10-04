package com.heartsignal.dev.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_info")
@Getter
@Setter
public class UserInfo {

    @Id
    private Long id;

    private String gender;

    @Column(unique = true)
    private String nickname;

    private String mbti;
    private String lookAlike;
    private String selfInfo;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;          //UserInfo의 Id는 User의 Id랑 동일
}