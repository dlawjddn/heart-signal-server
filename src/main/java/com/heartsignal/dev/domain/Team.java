package com.heartsignal.dev.domain;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User leader;            //  Leader인 유저를 저장

    private String title;
    private Boolean status;
    private Timestamp createdAt;

    /**
     * TODO
     * 리더를 포함할지 말지는 정해야 할듯
     */

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members;
    protected Team(){}
}
