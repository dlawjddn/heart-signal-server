package com.heartsignal.dev.dto.bar.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarInfoDTO {
    private Long barID;
    private String groupName;
    private String name;
}
