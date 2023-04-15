package com.alone.openai.client.entity.completion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 有默认值的都是官网提供接口的默认值
 * 没有值的可以根据实际情况配置
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRequestDto implements Serializable {

    //信息体
    private List<Message> messages;

    //使用的聊天模型
    private String model = Model.GPT_3_DOT_5_TURBO.getName();

    //是否流式输出
    private Boolean stream = true;

    private Double temperature = 0.9;

    @JsonProperty("top_p")
    private Double topP = 0.9;

    private Integer n = 1;

    @JsonProperty("max_tokens")
    private Integer maxTokens = 2048;

    @JsonProperty("presence_penalty")
    private Double presencePenalty = 0.0;

    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty = 0.0;

    @JsonProperty("logit_bias")
    private Map<String, Object> logitBias;

//    private String stop = "\n";

    private String user;

    public void pushMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    public void pushMessage(String role, String content) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(new Message(role, content));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        //角色
        private String role;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public enum Role {

        SYSTEM("system"),

        USER("user"),

        ASSISTANT("assistant");

        private String name;
    }

    @Getter
    @AllArgsConstructor
    public enum Model {

        GPT_3_DOT_5_TURBO("gpt-3.5-turbo"),

        GPT_4("gpt-4"),

        GPT_4_32K("gpt-4-32k");

        private String name;
    }

}
