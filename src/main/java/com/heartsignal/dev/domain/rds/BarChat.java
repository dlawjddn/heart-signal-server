package com.heartsignal.dev.domain.rds;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BarChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bar_chat_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "bar_id", nullable = false)
    private Bar bar;

    @OneToMany(mappedBy = "barChat", cascade = CascadeType.ALL)
    private List<User> users;

    public BarChat(){}

}
