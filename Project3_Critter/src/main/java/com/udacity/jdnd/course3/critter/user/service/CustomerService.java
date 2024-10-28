package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private final PetRepository petRepository;
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO, List<Long> petIds) {

        List<Pet> customerPets = new ArrayList<>();
        Customer newCustomer = new Customer();

        if (petIds != null && !petIds.isEmpty()) {
            customerPets = petIds.stream().map(petRepository::getOne).collect(Collectors.toList());
        }
        newCustomer.setPets(customerPets);
        newCustomer.setId(customerDTO.getId());
        newCustomer.setName(customerDTO.getName());
        newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        newCustomer.setNotes(customerDTO.getNotes());
        Customer addedCustomer = customerRepository.save(newCustomer);
        customerDTO.setId(addedCustomer.getId());
        return customerDTO;
    }

    public List<CustomerDTO> getAllCustomers() {

        List<CustomerDTO> customerDTOS = new ArrayList<CustomerDTO>();
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(customer -> {
            customerDTOS.add(convertCustomer(customer));
        });

        return customerDTOS;
    }

    public CustomerDTO getOwnerByPet(Long petId) {

        Customer customer = petRepository.getOne(petId).getCustomer();
        return convertCustomer(customer);
    }

    public CustomerDTO convertCustomer(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId)
                .collect(Collectors.toList()));
        return customerDTO;
    }


}
