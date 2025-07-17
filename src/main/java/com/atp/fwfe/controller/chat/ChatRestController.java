package com.atp.fwfe.controller.chat;

import com.atp.fwfe.dto.chat.ChatMessageResponse;
import com.atp.fwfe.model.account.Account;
import com.atp.fwfe.service.chat.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = {
        "http://10.0.2.2:8000",
        "http://127.0.0.1:8000"
}, allowCredentials = "true")
public class ChatRestController {

    @Autowired
    private ChatMessageService messageService;

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
                        (m.getReadBy() == null || m.getReadBy().isEmpty())
                                ? new ArrayList<>() // fallback: tránh null
                                : m.getReadBy().stream().map(Account::getUsername).toList(),
                        false
                ))
                .toList();
    }


    @PutMapping("/mark-read/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id, Principal principal) {
        messageService.markAsRead(id, principal.getName());
        return ResponseEntity.ok("Đã đánh dấu đã đọc!");
    }

    @GetMapping("/chat/private/inbox")
    public ResponseEntity<List<String>> getPrivateInbox(@RequestParam String myUsername) {
        List<String> senders = messageService.getSendersTo(myUsername);
        return ResponseEntity.ok(senders);
    }

}
