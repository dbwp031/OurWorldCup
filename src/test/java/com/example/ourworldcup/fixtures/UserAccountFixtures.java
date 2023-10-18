package com.example.ourworldcup.fixtures;

import com.example.ourworldcup.domain.userAccount.UserAccount;

public class UserAccountFixtures {
    private static final String TEST_USER_ID = "TEST_USER_ID";
    private static final String TEST_USERNAME = "TEST USERNAME";
    private static final String TEST_NICKNAME = "TEST NICKNAME";
    private static final String TEST_EMAIL = "TEST_USER_ID@example.com";
    private static final String TEST_PICTURE = "TEST PICTURE";

    public static UserAccount createDefaultUserAccount() {
        return createUserAccount(1L);
    }

    public static UserAccount createUserAccount(Long id) {
        return UserAccount.builder()
                .id(id)
                .userId(TEST_USER_ID)
                .userName(TEST_USERNAME)
                .nickName(TEST_NICKNAME)
                .email(TEST_EMAIL)
                .picture(TEST_PICTURE)
                .build();
    }
}
