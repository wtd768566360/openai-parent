package com.alone.openai.web.pojo.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryDto {

    private String account;

    private String name;

    private String phone;

    private String email;

    private String password;

    private String code;
}
