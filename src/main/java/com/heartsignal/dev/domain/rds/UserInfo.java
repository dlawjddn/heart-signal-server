package com.heartsignal.dev.domain.rds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserInfo {

    @Id
    @Column(name = "user_info_id")
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

    public UserInfo(){}
}