package com.example.ourworldcup.domain.match;

import com.example.ourworldcup.domain.AuditingFields;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.game.Game;
import jakarta.persistence.*;
import lombok.*;

@ToString(callSuper = true)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Round extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worldcup worldcup;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    private Long roundNum;

    private Long item1;

    private Long item2;

    private Long selectedItem;

    public void setSelectedItem(Long selectedItemId) {
        this.selectedItem = selectedItemId;
    }
}
