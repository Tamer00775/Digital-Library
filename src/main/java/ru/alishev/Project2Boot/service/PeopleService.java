package ru.alishev.Project2Boot.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.Project2Boot.models.Book;
import ru.alishev.Project2Boot.models.Person;
import ru.alishev.Project2Boot.repository.PersonRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PersonRepository personRepository;
    @Autowired
    public PeopleService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public List<Person> findAll(){
        return personRepository.findAll();
    }
    public Person findOne(int id){
        Optional<Person> findedPerson = personRepository.findById(id);
        return findedPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }
    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        personRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Book> findBooksByPersonId(int id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();
            Date date = new Date();
            for(Book book : books){
                long time = date.getTime() - book.getDate().getTime();
                if(time > 864000000)
                    book.setReserved(false);
                else
                    book.setReserved(true);
            }
            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }

}

