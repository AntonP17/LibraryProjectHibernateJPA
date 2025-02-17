package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30 , message = "Name should be between 2 and 30 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30 , message = "Name should be between 2 and 30 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30 , message = "Name should be between 2 and 30 characters")
    @Column(name = "patronymic")
    private String patronymic;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Book> books = new ArrayList<>();

    public Person() {
    }

    public Person(String firstName, String lastName, String patronymic, int yearOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.yearOfBirth = yearOfBirth;
    }

    @PreRemove // логика при удалении человека освобождаются все книги
    private void preRemove() {
        for (Book book : books) {
            book.setOwner(null);
        }
        books.clear();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int dayOfBirth) {
        this.yearOfBirth = dayOfBirth;
    }
}
