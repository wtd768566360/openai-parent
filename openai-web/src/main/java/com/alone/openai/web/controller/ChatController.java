package com.alone.openai.web.controller;

import com.alone.openai.client.entity.completion.ChatRequestDto;
import com.alone.openai.common.response.Restful;
import com.alone.openai.web.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "ChatGPT")
@RestController
@RequestMapping("chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Operation(summary = "参数存放")
    @PostMapping("params")
    public Mono<Restful> params(@RequestBody List<ChatRequestDto.Message> messages) {
        String key = chatService.put(messages);
        return Mono.just(Restful.builder().msg("参数存储成功").data(key).build());
    }

    @Operation(summary = "关联上下文的AI搜索,使用参数提前存储模式")
    @GetMapping("context/search/{key}")
    public Flux<ServerSentEvent<String>> contextSearch(@PathVariable String key) {
        return chatService.contextSearch(key);
    }

    @Operation(summary = "最简单的AI搜索,直接传参进来")
    @GetMapping("search")
    public Flux<ServerSentEvent<String>> search(@RequestParam String message) {
        return chatService.search(message);
    }
}
