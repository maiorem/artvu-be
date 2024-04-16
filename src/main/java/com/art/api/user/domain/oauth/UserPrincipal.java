package com.art.api.user.domain.oauth;

import com.art.api.user.domain.entity.AuthSocial;
import com.art.api.user.domain.entity.RoleType;
import com.art.api.user.domain.entity.SocialJoinType;
import com.art.api.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {
    private final String userId;
    private final String password;
    private final SocialJoinType socialJoinType;
    private final RoleType roleType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return userId;
    }

//    public static UserPrincipal create(AuthSocial auth, String userId) {
//        return new UserPrincipal(
//                userId,
//                auth.getSocialJoinType(),
//                RoleType.USER,
//                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
//        );
//    }

//    public static UserPrincipal create(AuthSocial auth, Map<String, Object> attributes, String userId) {
//        UserPrincipal clientUserPrincipal = create(auth, userId);
//        clientUserPrincipal.setAttributes(attributes);
//        return clientUserPrincipal;
//    }
}
