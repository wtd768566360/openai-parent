package com.alone.openai.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alone.openai.client.chat.CompletionSend;
import com.alone.openai.client.entity.completion.ChatRequestDto;
import com.alone.openai.web.service.ChatService;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ChatServiceImpl implements ChatService {

    private final Cache<String, List<ChatRequestDto.Message>> chatParamsCache;

    private final CompletionSend completionSend;

    public ChatServiceImpl(@Qualifier("chat-params-cache") Cache<String, List<ChatRequestDto.Message>> chatParamsCache,
                           CompletionSend completionSend) {
        this.chatParamsCache = chatParamsCache;
        this.completionSend = completionSend;
    }

    @Override
    public String put(List<ChatRequestDto.Message> messages) {
        String key = StrUtil.uuid().replaceAll("-", "");
        chatParamsCache.put(key, messages);
        return key;
    }

    @Override
    public Flux<ServerSentEvent<String>> contextSearch(String key) {
        List<ChatRequestDto.Message> messages = null;
        try {
            messages = chatParamsCache.get(key, () -> {
                return null;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        completionSend.setNewlineCharacter("\\\\n");
        completionSend.setNbspCharacter("&nbsp");
        return completionSend.sendServerSentEvent(messages);
    }

    @Override
    public Flux<ServerSentEvent<String>> search(String message) {
        completionSend.setNewlineCharacter("</br>");
        completionSend.setNbspCharacter("&nbsp");
        return completionSend.sendServerSentEventByUser(message);
    }
}
