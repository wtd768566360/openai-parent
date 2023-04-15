package com.alone.openai.admin.pojo.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {

    private String oldPassword;

    private String newPassword;
}
