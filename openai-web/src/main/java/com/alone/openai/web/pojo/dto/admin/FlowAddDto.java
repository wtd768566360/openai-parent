package com.alone.openai.web.pojo.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowAddDto {

    private String userId;

    private Integer number;
}
