package com.eledevo.integration.controller;

import com.eledevo.integration.dto.request.EmployeeRequest;
import com.eledevo.integration.dto.response.EmployeeResponse;
import com.eledevo.integration.entity.Employee;
import com.eledevo.integration.repository.redis.BaseRedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/hello")
@RequiredArgsConstructor
@Slf4j

public class HelloController {
    @Value("${application.kafka.eledevo-topic}")
    private String eledevoTopic;

    @Value("${application.kafka.eledevo_topic}")
    private String eledevo_Topic;
    private final String LIST_EMPLOYEE = "list_employee";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final BaseRedisRepository redisService;

    private List<Employee> listResponse = new ArrayList<>();

    @KafkaListener(topics = "response", groupId = "myGroup")
    public void consumeMsg(String request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = mapper.readValue(request, new TypeReference<List<Employee>>() {});
        System.out.println(employees);
        listResponse = employees;
    }

    @GetMapping("/world")
    public Object helloWorld() {
        kafkaTemplate.send(eledevoTopic, "path");
        return listResponse;
    }

    @PostMapping("/eledevo")
    public String helloEledevo(@RequestBody EmployeeRequest employeeRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = objectMapper.writeValueAsString(employeeRequest);
        kafkaTemplate.send(eledevo_Topic, msg);
        return "Thêm mới thành công";
    }

    @GetMapping("/vietnam")
    @PreAuthorize("hasAuthority('management:read')")
    public String helloVietNam() {
        return "Hello Việt Nam";
    }

}
