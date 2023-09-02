package by.tms.gradework_pm.util.validation;


import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<UniqueValue, String> {

    EmployeeRepository employeeRepository;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        Employee emp;
        try {
            emp = employeeRepository.findByEmail(value)
                    .orElseThrow(() -> new BusinessException("No such email"));
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

        return emp == null;
    }
}
