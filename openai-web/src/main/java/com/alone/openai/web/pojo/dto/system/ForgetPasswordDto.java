package com.alone.openai.web.pojo.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDto {

    private String account;

    private String email;

    private String password;

    private String code;
}
