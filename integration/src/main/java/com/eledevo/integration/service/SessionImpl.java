package com.eledevo.integration.service;

import com.eledevo.integration.entity.Session;
import com.eledevo.integration.repository.redis.SessionRepository;
import lombok.RequiredArgsConstructor;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor

public class SessionImpl implements SessionInterface {

    private final SessionRepository sessionRepository;

    @Override
    public Session createASession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> getSession(String id) {
        return sessionRepository.findById(id);
    }

    @Override
    public List<Session> getAllSessions() {
        return Stream.iterate(sessionRepository.findAll().iterator(), Iterator::hasNext, UnaryOperator.identity()).map(Iterator::next).collect(Collectors.toList());
    }
}
