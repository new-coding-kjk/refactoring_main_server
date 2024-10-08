package com.example.refactoring_main.oauth.provider;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class GoogleMemberInfo implements OAuth2MemberInfo {

    private Map<String, Object> attributes;

    public GoogleMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("nameU").toString();
    }
}
