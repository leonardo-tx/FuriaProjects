package gg.furia.cs.chat.core.repository;

import gg.furia.cs.chat.core.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
