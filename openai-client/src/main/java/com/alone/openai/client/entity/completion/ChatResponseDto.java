package com.alone.openai.client.entity.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponseDto {


    private String id;
    private String object;
    private Long created;
    private String model;
    private List<ChatChoice> choices;

    @Data
    public static class ChatChoice {

        private Long index;

        private Delta delta;

        @JsonProperty("finish_reason")
        private String finishReason;
    }

    @Data
    public static class Delta {
        private String role;
        private String content;
    }
}
