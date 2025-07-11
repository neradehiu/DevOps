package com.atp.fwfe.controller.chat;

import com.atp.fwfe.dto.chat.ChatMessageRequest;
import com.atp.fwfe.dto.chat.ChatMessageResponse;
import com.atp.fwfe.model.account.Account;
import com.atp.fwfe.model.chat.ChatMessage;
import com.atp.fwfe.service.chat.ChatMessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = {
        "http://10.0.2.2:8000",
        "http://127.0.0.1:8000"
}, allowCredentials = "true")
public class ChatWebsocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService messageService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    @MessageMapping("/chat.group")
    public void sendGroupMessage(@Valid ChatMessageRequest request, Principal principal){
        ChatMessage message = new ChatMessage();
        message.setSender(principal.getName());
        message.setContent(request.getContent());
        message.setType(ChatMessage.MessageType.GROUP);

        ChatMessage saved = messageService.save(message);
        ChatMessageResponse response = mapToResponse(saved, principal.getName());

        messagingTemplate.convertAndSend(
                "/topic/chat/group",
                response
        );

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Valid ChatMessageRequest request, Principal principal){
        ChatMessage message = new ChatMessage();
        message.setSender(principal.getName());
        message.setReceiver(request.getReceiver());
        message.setContent(request.getContent());
        message.setType(ChatMessage.MessageType.PRIVATE);

        ChatMessage saved = messageService.save(message);
        ChatMessageResponse response = mapToResponse(saved, principal.getName());

        messagingTemplate.convertAndSendToUser(
                request.getReceiver(),
                "/queue/messages",
                response
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    @MessageMapping("/chat.markRead")
    public void markAsRead(Long messageId, Principal principal){
        messageService.markAsRead(messageId, principal.getName());
    }

    private ChatMessageResponse mapToResponse(ChatMessage message, String currentUsername){
        List<String> readByUsers = message.getReadBy().stream()
                .map(Account::getUsername)
                .collect(Collectors.toList());

        return new ChatMessageResponse(
                message.getId(),
                message.getSender(),
                message.getReceiver(),
                message.getContent(),
                message.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                message.getType().toString(),
                readByUsers.isEmpty() ? null : readByUsers,
                message.getSender().equals(currentUsername)
        );
    }


}
