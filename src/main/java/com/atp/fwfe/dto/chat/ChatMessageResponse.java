package com.atp.fwfe.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {

    private Long id;
    private String sender;
    private String receiver;
    private String content;
    private String timestamp;
    private String type;
    private List<String> readByUsers;
    private boolean isSender;
}
