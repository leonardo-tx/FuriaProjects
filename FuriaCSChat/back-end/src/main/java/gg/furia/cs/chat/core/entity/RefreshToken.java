package gg.furia.cs.chat.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_REFRESH_TOKEN")
public final class RefreshToken {
    @Id
    private String id;

    @Column(nullable = false)
    private Date expiration;

    @ManyToOne
    private User user;
}
