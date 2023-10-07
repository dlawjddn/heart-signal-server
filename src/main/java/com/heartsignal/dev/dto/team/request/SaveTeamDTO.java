package com.heartsignal.dev.dto.team.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveTeamDTO {
    private List<String> nicknames;
    @NotBlank(message = "시그널 제목 입력은 필수 입니다.")
    @Pattern(regexp = "^(?!.*[-/*'\";]).{1,20}$", message = "유효하지 않은 문자 또는 길이 초과")
    private String title;

}
