//package com.alone.openai.web.test;
//
//import com.alone.openai.client.api.ServiceClient;
//import com.alone.openai.client.entity.chat.ChatCompletion;
//import com.alone.openai.client.entity.chat.ChatCompletionResponse;
//import com.alone.openai.client.entity.chat.Message;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//public class OpenAIClientTest {
//
//    private ServiceClient serviceClient;
//
//    public OpenAIClientTest(ServiceClient serviceClient) {
//        this.serviceClient = serviceClient;
//    }
//
//    @Test
//    public void send(String[] args) {
//        List<Message> messageList = new ArrayList<>();
//        messageList.add(Message.of("怎么把大象放入冰箱"));
//        ChatCompletionResponse chatCompletionResponse = serviceClient.chatCompletion(ChatCompletion.builder().messages(messageList).build());
//
//    }
//}
