package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final PetRepository petRepository;

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository,
                           EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO,
                                      List<Long> employeeIds, List<Long> petIds) {

        List<Pet> pets = petRepository.findAllById(petIds);
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        Schedule schedule = new Schedule(
                scheduleDTO.getId(),
                employees,
                pets,
                scheduleDTO.getDate(),
                scheduleDTO.getActivities()
        );
        Schedule addedSchedule = scheduleRepository.save(schedule);
        scheduleDTO.setId(addedSchedule.getId());
        return scheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<ScheduleDTO>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertSchedule(schedule));
        });

        return scheduleDTOS;
    }

    public List<ScheduleDTO> getScheduleForPet(Long petId) {

        Pet pet = petRepository.getOne(petId);
        List<Schedule> schedules = scheduleRepository.findByPets(pet);

        List<ScheduleDTO> scheduleDTOS = new ArrayList<ScheduleDTO>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertSchedule(schedule));
        });

        return scheduleDTOS;
    }

    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {

        Employee employee = employeeRepository.getOne(employeeId);
        List<Schedule> schedules = scheduleRepository.findByEmployees(employee);

        List<ScheduleDTO> scheduleDTOS = new ArrayList<ScheduleDTO>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertSchedule(schedule));
        });

        return scheduleDTOS;
    }

    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {

        Customer customer = customerRepository.getOne(customerId);
        List<Schedule> schedules = scheduleRepository.findByPetsIn(customer.getPets());

        List<ScheduleDTO> scheduleDTOS = new ArrayList<ScheduleDTO>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertSchedule(schedule));
        });

        return scheduleDTOS;
    }

    public ScheduleDTO convertSchedule(Schedule schedule) {

        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());

        return new ScheduleDTO(schedule.getId(), employeeIds, petIds,
                schedule.getDate(), schedule.getActivities());
    }
}
