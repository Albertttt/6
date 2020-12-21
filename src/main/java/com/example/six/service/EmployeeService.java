package com.example.six.service;


import com.example.six.entity.Employee;
import com.example.six.exception.NotFoundException;
import com.example.six.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee get(long id) {
        return employeeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee createOrSaveEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return get(id);
    }

    public Employee updateEmployee(Employee newEmployee, Long id) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(newEmployee.getFirstName());
            employee.setLastName(newEmployee.getLastName());
            employee.setEmail(newEmployee.getEmail());
            employee.setPassword(newEmployee.getPassword());
            employee.setUserName(newEmployee.getUserName());
            return employeeRepository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return employeeRepository.save(newEmployee);
        });
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
