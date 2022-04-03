package com.eureka.persons;

import com.eureka.persons.ex.NotFoundException;
import com.eureka.persons.person.Person;
import com.eureka.persons.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@RestController
@RequestMapping("/persons")
public class PersonsController {
    private PersonService personService;

    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles requests to list all persons.
     */
    //TODO find all persons using the functions already implemented and sort them by id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> list() {
        List<Person> all=personService.findAll();
        Collections.sort(all, Comparator.comparing(Person::getId));
        return all;
    }

    /**
     * Handles requests to create a person.
     */
    //TODO save a person to the db or throw PersonsException
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Person person, BindingResult result) {
           if(result.hasErrors()){
               throw new PersonsException(HttpStatus.INTERNAL_SERVER_ERROR, "Error!");
           }
           else {
               personService.save(person);
           }
    }

    /**
     * Returns the {@code Person} instance with id {@code id}
     *
     * @param id
     * @return
     */
    //TODO find a person by id or throw NotFoundException
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object show(@PathVariable Long id) {
        Optional<Person> person=personService.findById(id);
        if(person.isPresent()){
            return person;
        }
        else {
            throw new NotFoundException(Person.class, id);
        }
    }

    /**
     * Updates the {@code Person} instance with id {@code id}
     *
     * @param updatedPerson
     * @param id
     * @return
     */
    //TODO update an existing person if found else throw NotFoundException
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Person updatedPerson, @PathVariable Long id) {
        Optional<Person> person=personService.findById(id);
        person.ifPresent(person1 -> {
            personService.updatePerson(updatedPerson, person1);
        });
        if(!person.isPresent()){
            throw new NotFoundException(Person.class, id);
        }
    }

    /**
     * Delete the {@code Person} instance with id {@code id}
     *
     * @param id
     */
    //TODO delete a person
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Person> person=personService.findById(id);
        person.ifPresent(thePerson ->personService.delete(thePerson));
        if(!person.isPresent()){
            throw new NotFoundException(Person.class, id);
        }
    }
}