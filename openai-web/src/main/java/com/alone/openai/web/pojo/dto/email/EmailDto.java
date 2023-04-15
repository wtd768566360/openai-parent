package com.alone.openai.web.pojo.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    
    private String account;

    private String email;
}
