package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

       Employee employee = new Employee(employeeDTO.getId(), employeeDTO.getName(),
               employeeDTO.getSkills(), employeeDTO.getDaysAvailable());

       Employee addedEmployee = employeeRepository.save(employee);
       employeeDTO.setId(addedEmployee.getId());
       return employeeDTO;
    }

    public EmployeeDTO getEmployee(Long employeeId) {

        Employee employee = employeeRepository.getOne(employeeId);
        return convertEmployee(employee);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {

        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {

        List<EmployeeDTO> employeeDTOS = new ArrayList<EmployeeDTO>();
        List<Employee> employees = employeeRepository.findByDaysAvailable(date.getDayOfWeek())
                .stream().filter(employee -> employee.getSkills().containsAll(skills)).collect(Collectors.toList());

        employees.forEach(employee -> {
            employeeDTOS.add(convertEmployee(employee));
        });
        return employeeDTOS;
    }

    public EmployeeDTO convertEmployee(Employee employee) {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable((employee.getDaysAvailable()));
        return employeeDTO;
    }
}
