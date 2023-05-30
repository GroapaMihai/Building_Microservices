package net.javaguides.employeeservice.service;

import net.javaguides.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Eureka Client will internally use load balancing to pick from available DEPARTMENT-SERVICE instances
// DEPARTMENT-SERVICE is the spring.application.name property value of the department-service microservice
@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentAPIClient {

    @GetMapping("/api/departments/{departmentCode}")
    DepartmentDto getDepartment(@PathVariable("departmentCode") String departmentCode);
}
