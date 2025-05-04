package gg.furia.cs.chat.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.cookie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CookieProperties {
    private boolean httpOnly;
    private boolean secure;
    private String path;
}
