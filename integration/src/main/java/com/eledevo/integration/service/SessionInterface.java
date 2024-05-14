package com.eledevo.integration.service;


import com.eledevo.integration.entity.Session;

import java.util.List;
import java.util.Optional;

public interface SessionInterface {
    Session createASession(Session session);
    Optional<Session> getSession(String id);
    List<Session> getAllSessions();
}
