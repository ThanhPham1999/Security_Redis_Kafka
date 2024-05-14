package com.eledevo.integration.entity;

import com.eledevo.integration.constant.TokenType;
import jakarta.persistence.*;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Integer id;
    @Column(unique = true)

    public String token;
    @Enumerated(EnumType.STRING)

    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id")

    public User user;
}
