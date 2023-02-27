package ru.alishev.Project2Boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.Project2Boot.models.Book;


import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> searchBookByNameStartingWith(String s);

}
