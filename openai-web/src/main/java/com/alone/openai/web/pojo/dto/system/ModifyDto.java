package com.alone.openai.web.pojo.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDto {

    private String id;

    private String name;

    private String phone;

    private String email;
}
