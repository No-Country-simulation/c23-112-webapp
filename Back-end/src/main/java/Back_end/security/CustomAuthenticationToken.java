package Back_end.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String userId;

    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String userId) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }

}
