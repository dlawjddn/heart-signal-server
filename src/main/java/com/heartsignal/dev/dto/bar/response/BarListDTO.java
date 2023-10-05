package com.heartsignal.dev.dto.bar.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarListDTO {
    private String name;
    private List<BarContentDTO> pubs;
}
