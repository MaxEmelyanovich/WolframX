package framexteam.wolframx.chat.repository;
import framexteam.wolframx.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
}