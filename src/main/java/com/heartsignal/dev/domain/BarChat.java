package com.heartsignal.dev.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BarChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bar_id", nullable = false)
    private Bar bar;

    @OneToMany(mappedBy = "barChat", cascade = CascadeType.ALL)
    private List<User> users;

}
