package com.heartsignal.dev.domain.rds;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "user_info")
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
    @JoinColumn(name = "user_id")
    @MapsId
    @ToString.Exclude
    User user;

}