package ru.alishev.Project2Boot.models;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name="book")
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @NotEmpty(message = "Name of book not should be empty!")
    @Column(name="name")
    private String name;
    @NotEmpty(message = "Ex: Kartayev Tamerlan")
    @Column(name="author")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+")
    private String author;
    @Max(value=2022, message = "You not correct add book! Our book is till due 2022")
    private int year;
    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person owner;


    @Column(name="data_get")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;
    // private boolean reserved;
    @Transient
    private boolean reserved;

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved =reserved;
    }
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Book(){

    }
    public Book(String name, String author, int year, Date date) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
