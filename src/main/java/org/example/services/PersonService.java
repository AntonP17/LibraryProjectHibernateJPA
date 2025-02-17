package org.example.services;

import org.example.model.Person;
import org.example.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // получаем всех
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    // получаем одного
    public Person showById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    // сохраняем
    public void save(Person person) {
        personRepository.save(person);
    }

    // удаляем
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    // обновляем
    public void update(int id, Person updatedPerson) {
        Person person = personRepository.findById(id).orElse(null);

        if (person != null) {
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setPatronymic(updatedPerson.getPatronymic());
            person.setYearOfBirth(updatedPerson.getYearOfBirth());
            personRepository.save(person);
        }
    }


}
