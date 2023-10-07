package com.heartsignal.dev.dto.userInfo.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveAdditionalInfoDTO {
    @NotBlank(message = "닉네임 입력은 필수 입니다.")
    @Pattern(regexp = "^[A-Za-z가-힣0-9]{1,10}$", message = "닉네임 형식에 맞지 않습니다.")
    private String nickname;
    @NotBlank(message = "mbti 입력은 필수 입니다.")
    private String mbti;
    @NotBlank(message = "얼굴상 입력은 필수 입니다.")
    private String face;
    @NotBlank(message = "자기소개 입력은 필수 입니다.")
    private String selfInfo;
    @NotBlank(message = "성별 입력은 필수 입니다.")
    private String gender;
}
