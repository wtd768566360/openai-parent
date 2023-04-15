package com.alone.openai.web.pojo.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    private String account;

    private String name;

    private String email;

    private String token;

}
