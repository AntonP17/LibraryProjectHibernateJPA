package org.example.dao;

import org.example.model.Book;
import org.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //+ получение всех книг
    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<Book>(Book.class));
    }

    //+ получение книг, взятых person
    public List<Book> getBooksByPersonId(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }

    //+ получение данных у кого книга
    public Person getOwnerByBookId(int bookId) {
        String sql = "SELECT person.firstname, person.lastname, person.patronymic " +
                "FROM person " +
                "JOIN book " +
                "ON person.id = book.person_id " +
                "WHERE book.id = ?";

        List<Person> persons = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Person>(Person.class), bookId);

        if (persons.isEmpty()) {
            return null; // Возвращаем null, если нет владельца
        } else {
            return persons.get(0); // Возвращаем владельца, если он есть
        }
    }

    //+ получение конкретной книги
    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    //- сохранение
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES(?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }

    // освобождение книги
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
    }

    // назначение книги
    public void assign(int bookId, int personId) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", personId, bookId);
    }

    //- обновление
    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET tittle=?, author=?, year=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    // удаление
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

}
