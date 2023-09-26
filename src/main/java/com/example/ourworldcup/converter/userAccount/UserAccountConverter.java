package com.example.ourworldcup.converter.userAccount;

import com.example.ourworldcup.auth.dto.OAuthAttributes;
import com.example.ourworldcup.auth.dto.UserAccountDto;
import com.example.ourworldcup.controller.userAccount.dto.UserAccountResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.repository.UserAccountRepository;
import com.example.ourworldcup.repository.WorldcupRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserAccountConverter {
    private final UserAccountRepository userAccountRepository;
    private final WorldcupRepository worldcupRepository;
    private static UserAccountRepository staticUserAccountRepository;
    private static WorldcupRepository staticWorldcupRepository;

    @PostConstruct
    public void init() {
        this.staticUserAccountRepository = userAccountRepository;
        this.staticWorldcupRepository = worldcupRepository;
    }

    public static UserAccountDto toUserAccountDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        String registrationId = oAuth2User.getAttribute("registrationId");
        String userNameAttributeName = oAuth2User.getAttribute("userNameAttributeName");
        Map<String, Object> cloned = new HashMap<>(attributes);
        cloned.put("response", attributes);
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, cloned);
        return UserAccountDto.builder()
                .email(oAuthAttributes.getEmail())
                .authProviderType(oAuthAttributes.getAuthProviderType())
                .name(oAuthAttributes.getName())
                .picture(oAuthAttributes.getPicture())
                .build();
    }

    public static UserAccountResponseDto.BasicDto toUserAccountResponseBasicDto(UserAccount userAccount) {
        return UserAccountResponseDto.BasicDto.builder()
                .id(userAccount.getId())
                .userId(userAccount.getUserId())
                .userName(userAccount.getUserName())
                .nickName(userAccount.getNickName())
                .email(userAccount.getEmail())
                .picture(userAccount.getPicture())
                .build();
    }

    public static UserAccountResponseDto.WithWorldcupsDto toUserAccountResponseWithWorldcupsDto(UserAccount userAccount) {
        List<WorldcupResponseDto.BasicDto> worldcups = userAccount.getMembers().stream()
                .map(m -> WorldcupConverter.toWorldcupResponseBasicDto(m.getWorldcup()))
                .toList();

        return UserAccountResponseDto.WithWorldcupsDto.builder()
                .id(userAccount.getId())
                .userId(userAccount.getUserId())
                .userName(userAccount.getUserName())
                .nickName(userAccount.getNickName())
                .email(userAccount.getEmail())
                .picture(userAccount.getPicture())
                .worldcups(worldcups)
                .build();
    }
}
