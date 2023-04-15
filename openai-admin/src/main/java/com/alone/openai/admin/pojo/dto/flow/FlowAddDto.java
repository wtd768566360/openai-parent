package com.alone.openai.admin.pojo.dto.flow;

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
