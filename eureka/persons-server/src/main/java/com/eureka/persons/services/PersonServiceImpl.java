package com.eureka.persons.services;

import com.eureka.persons.PersonRepo;
import com.eureka.persons.person.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public List<Person> findAll() {
        return personRepo.findAll();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepo.findById(id);
    }


    @Override
    public Person save(Person person) {
        personRepo.save(person);
        return person;
    }

    @Override
    public void delete(Person person) {
        personRepo.delete(person);
    }

    public void updatePerson(Person updatePerson, Person found){
        found.setFirstName(updatePerson.getFirstName());
        found.setLastName(updatePerson.getLastName());
        found.setHiringDate(updatePerson.getHiringDate());
        found.setPassword(updatePerson.getPassword());
        found.setUsername(updatePerson.getUsername());
        found.setNewPassword(updatePerson.getNewPassword());
        found.setCreatedAt(updatePerson.getCreatedAt());
        found.setModifiedAt(updatePerson.getModifiedAt());
        found.setVersion(updatePerson.getVersion());
        personRepo.save(found);
    }
}

