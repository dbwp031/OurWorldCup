package com.example.ourworldcup.domain;

import com.example.ourworldcup.domain.relation.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class Worldcup extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 10000)
    private String content;

    @OneToOne(mappedBy = "worldcup", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Invitation invitation;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Item> items = new ArrayList<>();
    /*
     * mappedBy: 양방향 관계에서 주인 엔티티를 결정하기 위한 인자. (주인은 대체적으로 N쪽이 된다.)
     *   - 이때 인자값은 주인 엔티티에서 자신을 가리키는 객체의 변수명을 작성해주어야한다.
     * @ManyToOne 혹은 @OneToOne 관계는 기본적으로 해당 관계를 가지고 있는 애들이 주인이다. (상대는 @OneToMany이겠지?)
     * - 참고로 @OneToOne 양방향 관계는 mappedBy써서 주인관계를 지정해줘야함.
     *
     * -@OneToMany 혹은 @ManyToMany 관계에선 mappedBy 속성을 사용하여 연관 관계의 주인이 아닌 쪽을 지정한다.
     * mappedBy 속성의 값은 연관 관계의 주인 쪽에서 해당 관계를 참조하는 필드의 이름
     * */
    @ToString.Exclude
    @OneToMany(mappedBy = "worldcup", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    /*
     * 편의 매서드의 목적은 객체 간의 관계를 한 쪽에서 설정할 때, 다른 쪽에서도 자동으로 반영되기 위함입니다.
     * 연관관계 설정은 외래키를 관리하는 "주인" 엔티티에서 설정해주는게 일반적입니다.
     * 그렇기 때문에, 주인이 아닌 엔티티는 편의 메서드를 사용해서 주인 쪽에서 외래 키를 변경하도록 유도하는 것이 좋습니다.
     * */
    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
        invitation.setWorldcup(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worldcup worldcup = (Worldcup) o;
        return Objects.equals(id, worldcup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Item addItem(Item item) {
        item.setWorldcup(this);
        this.items.add(item);
        return item;
    }
}
