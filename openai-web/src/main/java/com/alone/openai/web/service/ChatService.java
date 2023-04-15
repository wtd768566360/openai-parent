package com.alone.openai.web.service;

import com.alone.openai.client.entity.completion.ChatRequestDto;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    String put(List<ChatRequestDto.Message> messages);

    Flux<ServerSentEvent<String>> search(String message);

    Flux<ServerSentEvent<String>> contextSearch(String key);
}
