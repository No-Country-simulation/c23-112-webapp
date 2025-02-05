package Back_end.authentication.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String userId;

    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String userId) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
