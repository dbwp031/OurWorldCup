package com.example.ourworldcup.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VsResultDto {
    Long sourceGameId;
    Long targetGameId;
    Long score;
    String targetPlayerName;
}
