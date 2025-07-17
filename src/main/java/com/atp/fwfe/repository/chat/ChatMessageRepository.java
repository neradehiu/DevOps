package com.atp.fwfe.repository.chat;

import com.atp.fwfe.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByReceiverOrderByTimestampDesc(String receiver);

    @Query("SELECT DISTINCT m.receiver FROM ChatMessage m WHERE m.receiver IS NOT NULL")
    List<String> findAllReceivers();

    @Query("SELECT m.id FROM ChatMessage m WHERE m.receiver = :receiver ORDER BY m.timestamp DESC OFFSET 50")
    List<Long> findOldMessageIdsToDelete(@Param("receiver") String receiver);

    void deleteByIdIn(List<Long> ids);

    @Query("SELECT DISTINCT m.sender FROM ChatMessage m WHERE m.receiver = :receiver")
    List<String> findDistinctSenderUsernamesTo(@Param("receiver") String receiver);


}
