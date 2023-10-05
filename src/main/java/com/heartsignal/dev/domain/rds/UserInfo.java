package com.heartsignal.dev.domain.rds;

import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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