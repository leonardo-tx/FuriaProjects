package gg.furia.cs.chat.core.repository;

import gg.furia.cs.chat.core.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
