package com.example.refactoring_main.oauth;

import com.example.refactoring_main.config.auth.CustomerDetails;
import com.example.refactoring_main.config.filter.LoginFilter;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.oauth.provider.GoogleMemberInfo;
import com.example.refactoring_main.oauth.provider.OAuth2MemberInfo;
import com.example.refactoring_main.repository.MemberRepository;
import com.example.refactoring_main.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2MemberInfo oAuth2MemberInfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            log.info("구글 로그인 요청");
            oAuth2MemberInfo = new GoogleMemberInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            log.info("네이버 로그인 요청");
//            OAuth2MemberInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
        } else {
            log.info("우리는 구글 네이버만 지원 합니다.");
        }

        String provider = oAuth2MemberInfo.getProvider(); // google
        String providerId = oAuth2MemberInfo.getProviderId();
        String username = provider+"_"+providerId; // google_113207387552249574045
        String password = passwordEncoder.encode("겟인데어");
        String email = oAuth2MemberInfo.getEmail();
        Role role = Role.ROLE_USER;

        Member memberEntity = memberRepository.findByUsername(username);

        if(memberEntity == null) {
            log.info("로그인이 최초입니다.");
            memberEntity = Member.builder()
                    .username(username)
                    .password(password)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(memberEntity);
        }else {
            log.info("구글 로그인을 이미 한적이 있습니다. 당신은 자동회원가입니 되어있습니다.");
        }


        return new CustomerDetails(memberEntity,oAuth2User.getAttributes());
    }
}
