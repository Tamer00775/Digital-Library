package ru.alishev.Project2Boot.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.Project2Boot.models.Book;
import ru.alishev.Project2Boot.models.Person;
import ru.alishev.Project2Boot.repository.BookRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> findAll(boolean b){
        if(b){
            return bookRepository.findAll(Sort.by("year"));
        }
        else
            return bookRepository.findAll();
    }

    public List<Book> findAll(int page, int page_of_books){
        return bookRepository.findAll(PageRequest.of(page, page_of_books)).getContent();
    }

    public Book find(int id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null) ;
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book book){
        book.setId(id);
        bookRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }
    public Person findOwner(int id){
        Person person = bookRepository.getOne(id).getOwner();
        Hibernate.initialize(person);
        return person;
    }
    @Transactional
    public void savePerson(Person person, int id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            book.get().setOwner(person);
            book.get().setDate(new Date());
            Hibernate.initialize(book);
        }
    }

    @Transactional
    public void deletePerson(int id){
        Book book = bookRepository.getOne(id);
        book.setOwner(null);
        book.setDate(null);
        bookRepository.save(book);
    }

    public List<Book> search(String s){
        return bookRepository.searchBookByNameStartingWith(s);
    }


}
