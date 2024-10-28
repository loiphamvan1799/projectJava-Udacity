package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private final PetRepository petRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public PetDTO savePet(PetDTO petDTO) {

        Customer customer = customerRepository.getOne(petDTO.getOwnerId());
        List<Pet> pets = new ArrayList<Pet>();
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setCustomer(customer);
        Pet addPet = petRepository.save(pet);
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);
        petDTO.setId(addPet.getId());
        return petDTO;
    }

    public PetDTO getPet(long id) {

        Pet pet = petRepository.getOne(id);
        return convertPet(pet);
    }

    public List<PetDTO> getAllPets() {

        List<Pet> pets = petRepository.findAll();
        List<PetDTO> petDTOS = new ArrayList<PetDTO>();
        pets.forEach(pet -> {
            petDTOS.add(convertPet(pet));
        });

        return petDTOS;
    }

    public  List<PetDTO> getPetsByOwner(Long ownerId) {

        List<PetDTO> petDTOS = new ArrayList<PetDTO>();
        List<Pet> pets = petRepository.findAllByCustomerId(ownerId);
        pets.forEach(pet -> {
            petDTOS.add(convertPet(pet));
        });
        return petDTOS;
    }

    public PetDTO convertPet(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        return petDTO;
    }
}
