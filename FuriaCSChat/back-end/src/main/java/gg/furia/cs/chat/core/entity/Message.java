package gg.furia.cs.chat.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "TB_MESSAGE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    public static final int TEXT_MAX_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = TEXT_MAX_LENGTH)
    private String text;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_message"))
    private User user;
}
