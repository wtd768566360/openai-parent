package com.alone.openai.admin.pojo.dto.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDto {

    private String apiKey;

    private String origin;
}
