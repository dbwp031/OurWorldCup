package com.example.ourworldcup.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "ProfileConfigurer")
@RequiredArgsConstructor
public class ProfileConfigurer {
    // Constants
    private final List<String> onProfileList = List.of("dev", "prod", "local", "localDev", "localProd", "test");

    // Dependencies
    private final Environment environment;

    // Variables
    private String activeProfile;

    // Properties
    private String bucket;
    private static String staticBucket;

    // 생성될 때 필요한 로직이 있을 때 사용
    @PostConstruct
    public void init() {
        List<String> activeProfileList = List.of(environment.getActiveProfiles());
        for (String onProfile : onProfileList) {
            if (activeProfileList.contains(onProfile)) {
                activeProfile = onProfile;
                break;
            }
        }
        if (activeProfile.equals("dev") || activeProfile.equals("localDev")) {
//            bucket = environment.getProperty("cloud.aws.s3.dev-bucket");
            bucket = environment.getProperty("cloud.aws.s3.bucket");
        } else {
            bucket = environment.getProperty("cloud.aws.s3.bucket");
        }

        this.staticBucket = this.bucket;
    }
    public static String getBucket() {
        return staticBucket;
    }
}
