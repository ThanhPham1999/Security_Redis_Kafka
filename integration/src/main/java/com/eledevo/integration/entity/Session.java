package com.eledevo.integration.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@NoArgsConstructor
@RedisHash(timeToLive = 60L)
public class Session {
    @Id
    private String id;
    private String name;
    @TimeToLive
    private Long expirationInSeconds;

}
