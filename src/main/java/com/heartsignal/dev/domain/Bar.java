package com.heartsignal.dev.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name ="bars")
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String group;
    private String location;
    private String concept;

    @OneToOne(mappedBy = "bar")
    private BarChat barChat;

}