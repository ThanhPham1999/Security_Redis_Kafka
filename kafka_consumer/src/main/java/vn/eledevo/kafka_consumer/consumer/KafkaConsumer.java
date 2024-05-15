package vn.eledevo.kafka_consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.eledevo.kafka_consumer.entity.Employee;
import vn.eledevo.kafka_consumer.repository.EmployeeRepository;
import vn.eledevo.kafka_consumer.repository.redis.BaseRedisRepository;


import java.util.List;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final EmployeeRepository employeeRepository;

    @Value("${application.kafka.eledevo-topic}")
    private String eledevoTopic;
    private final KafkaTemplate<String, List<Employee>> kafkaTemplate;

    @KafkaListener(topics = "request", groupId = "myGroup")
    public void  consumeMsg(String request) {
        log.info(format("Consuming the message from eledevo Topic:: %s", request));
        List<Employee> listResponse = employeeRepository.findAll();
        kafkaTemplate.send(eledevoTopic, listResponse);
    }

    @KafkaListener(topics = "addRequest", groupId = "myGroup")
    public void  addEmployee(String request) throws JsonProcessingException {
        log.info(format("Consuming the message from eledevo Topic:: %s", request));
        ObjectMapper objectMapper = new ObjectMapper();
        Employee data = objectMapper.readValue(request, Employee.class );
        employeeRepository.save(data);
    }
}
