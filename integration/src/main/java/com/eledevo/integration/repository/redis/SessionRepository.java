package com.eledevo.integration.repository.redis;

import com.eledevo.integration.entity.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, String> {
}
