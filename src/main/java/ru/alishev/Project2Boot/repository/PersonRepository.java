package ru.alishev.Project2Boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.Project2Boot.models.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}