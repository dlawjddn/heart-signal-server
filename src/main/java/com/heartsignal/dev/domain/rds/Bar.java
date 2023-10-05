package com.heartsignal.dev.domain.rds;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name ="bars")
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bar_id")
    private Long id;

    private String name;
    @Column(name = "`group`")
    private String group;
    private String location;
    private String concept;

    @OneToOne(mappedBy = "bar", fetch = FetchType.LAZY)
    private BarChatRoom barChatRoom;

    public Bar(){}

}