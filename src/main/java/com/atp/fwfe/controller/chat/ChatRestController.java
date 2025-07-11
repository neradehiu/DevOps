package com.atp.fwfe.controller.chat;

import com.atp.fwfe.dto.chat.ChatMessageResponse;
import com.atp.fwfe.model.account.Account;
import com.atp.fwfe.service.chat.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = {
        "http://10.0.2.2:8000",
        "http://127.0.0.1:8000"
}, allowCredentials = "true")
public class ChatRestController {

    @Autowired
    private ChatMessageService messageService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/received")
    public List<ChatMessageResponse> getReceivedMessages(Principal principal) {
        return messageService.findReceivedMessages(principal.getName())
                .stream()
                .map(m -> new ChatMessageResponse(
                        m.getId(),
                        m.getSender(),
                        m.getReceiver(),
                        m.getContent(),
                        m.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        m.getType().toString(),
                        m.getReadBy().stream().map(Account::getUsername).toList(),
                        false
                ))
                .toList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    @PutMapping("/mark-read/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id, Principal principal){
        messageService.markAsRead(id, principal.getName());
        return ResponseEntity.ok("Đã đánh dấu đã đọc!");
    }
}
