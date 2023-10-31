package com.ichubtou.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ichubtou.websocket.entity.ChatMessage;
import com.ichubtou.websocket.entity.ChatRoom;
import com.ichubtou.websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    //양방향 데이터 통신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {

        String payload = textMessage.getPayload();
        log.info("payload {}", payload);

        ChatMessage message = objectMapper.readValue(textMessage.getPayload(), ChatMessage.class);
        ChatRoom room = chatService.findRoomById(message.getRoomId());
        room.handleActions(session, message, chatService);
    }
}
