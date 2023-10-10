package com.heartsignal.dev.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MeetLeaveStatusDTO {

    private boolean leaveClicked;
}
