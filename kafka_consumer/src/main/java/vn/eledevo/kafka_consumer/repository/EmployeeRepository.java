package vn.eledevo.kafka_consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.eledevo.kafka_consumer.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
