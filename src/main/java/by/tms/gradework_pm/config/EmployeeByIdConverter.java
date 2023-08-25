package by.tms.gradework_pm.config;

import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
@RequiredArgsConstructor
public class EmployeeByIdConverter implements Converter<String, Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee convert(String id) {
        Long tmpId = Long.parseLong(id);
        return employeeRepository.findById(tmpId)
                .orElseThrow(RuntimeException::new);
    }
}
