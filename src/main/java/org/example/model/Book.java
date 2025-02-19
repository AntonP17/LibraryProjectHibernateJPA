package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty (message = "Title should not be empty")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty (message = "Author name should not be empty")
    @Size(min = 2, max = 100, message = "Author name should be between 2 and 100 characters")
    @Column(name = "author")
    private String author;


    @Min(value = 0, message = "Year should be greater than 0")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "people_id")
    private Person owner;

//    @Temporal(TemporalType.TIMESTAMP) это до версии постгрес 5 и ниже
//    @Column(name = "timestamp_column")
//    private Date timestampColumn;
    @Column(name = "start_time")
    private LocalDate startTime;

    @Transient
    private boolean checkReturnDate;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public boolean isCheckReturnDate() {
        return checkReturnDate;
    }

    public void setCheckReturnDate(boolean checkReturnDate) {
        this.checkReturnDate = checkReturnDate;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
