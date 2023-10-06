package com.heartsignal.dev.domain.rds;


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
@NamedStoredProcedureQuery(
        name = "createTeam", 
        procedureName = "createTeam",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "leader_id"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "title"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "user_nicknames")
        }
)

public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;            //  Leader인 유저를 저장

    private String title;

    private int status; //0이면 false, 1이면 true
    private Timestamp createdAt;

    /**
     * TODO
     * 리더를 포함할지 말지는 정해야 할듯
     */

    @OneToMany(mappedBy = "team")
    private List<User> members;

    protected Team(){}

    public void updateStatus(int status) {
        this.status = status;
    }
}
