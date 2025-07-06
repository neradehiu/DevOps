package com.atp.fwfe.service.chat;

import com.atp.fwfe.model.account.Account;
import com.atp.fwfe.model.chat.ChatMessage;
import com.atp.fwfe.repository.account.AccRepository;
import com.atp.fwfe.repository.chat.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatRepository;

    @Autowired
    private AccRepository accRepository;

    public ChatMessage save(ChatMessage message){
        message.setReadBy(new HashSet<>());
        return chatRepository.save(message);
    }

    public void markAsRead(Long messageId, String readerUsername) {
        ChatMessage message = chatRepository.findById(messageId).orElseThrow(() -> new RuntimeException("Tin nhắn không tồn tại"));
        Account reader = accRepository.findByUsername(readerUsername).orElseThrow(()-> new RuntimeException("Người dùng không tồn tại"));

        if(!message.getReadBy().contains(reader)) {
            message.getReadBy().add(reader);
            chatRepository.save(message);
        }
    }

    public List<ChatMessage> findAll() {
        return chatRepository.findAll();
    }

    public List<ChatMessage> findReceivedMessages(String username) {
        return chatRepository.findByReceiverOrderByTimestampDesc(username);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void cleanOldMessagesForAllUsers(){
        List<String> allReceivers = chatRepository.findAllReceivers();
        int totalDeleted = 0;

        for (String receiver : allReceivers) {
            List<Long> oldIds = chatRepository.findOldMessageIdsToDelete(receiver);
            if (!oldIds.isEmpty()) {
                chatRepository.deleteByIdIn(oldIds);
                totalDeleted += oldIds.size();
            }
        }

        System.out.println("🧹 Đã tự động xoá " + totalDeleted + " tin nhắn cũ.");
    }

}
