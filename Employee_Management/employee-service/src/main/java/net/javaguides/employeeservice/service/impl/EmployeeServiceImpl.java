package net.javaguides.employeeservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import net.javaguides.employeeservice.dto.APIResponseDto;
import net.javaguides.employeeservice.dto.DepartmentDto;
import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.dto.OrganizationDto;
import net.javaguides.employeeservice.entity.Employee;
import net.javaguides.employeeservice.exception.ResourceNotFoundException;
import net.javaguides.employeeservice.mapper.EmployeeMapper;
import net.javaguides.employeeservice.repository.EmployeeRepository;
import net.javaguides.employeeservice.service.DepartmentAPIClient;
import net.javaguides.employeeservice.service.EmployeeService;
import net.javaguides.employeeservice.service.OrganizationAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    // private RestTemplate restTemplate;

    // private WebClient webClient;

    private DepartmentAPIClient departmentApiClient;

    private OrganizationAPIClient organizationAPIClient;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.INSTANCE.mapToEmployeeDto(savedEmployee);
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultResponse")
    // @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultResponse")
    public APIResponseDto getEmployeeById(Long id) {
        logger.info("getEmployeeById");

        Employee employee = employeeRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Employee", "id", id)
        );
        EmployeeDto employeeDto = EmployeeMapper.INSTANCE.mapToEmployeeDto(employee);

        // ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(
        //    "http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDto.class
        // );
        // DepartmentDto departmentDto = responseEntity.getBody();

        // DepartmentDto departmentDto = webClient.get()
             // .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
             // .retrieve()
             // .bodyToMono(DepartmentDto.class)
             // .block();

        DepartmentDto departmentDto = departmentApiClient.getDepartment(employee.getDepartmentCode());
        OrganizationDto organizationDto = organizationAPIClient.getOrganization(employee.getOrganizationCode());

        return new APIResponseDto(employeeDto, departmentDto, organizationDto);
    }

    public APIResponseDto getDefaultResponse(Long id, Exception exception) {
        logger.info("getDefaultResponse");

        Employee employee = employeeRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Employee", "id", id)
        );
        EmployeeDto employeeDto = EmployeeMapper.INSTANCE.mapToEmployeeDto(employee);

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and development department");

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setOrganizationName("ABC");
        organizationDto.setOrganizationDescription("ABC OrganizationDescription");
        organizationDto.setOrganizationCode("ABC_ORG");

        return new APIResponseDto(employeeDto, departmentDto, organizationDto);
    }
}
