package org.example.repositories;

import org.example.model.Book;
import org.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    //+ получение книг, взятых person
//    @Query("SELECT b FROM Book b WHERE b.owner.id = :personId")
//    List<Book> getBooksByPeopleId(@Param("personId") int personId);
    List<Book> findBookByOwnerId(int ownerId);

    // поиск книги начинающийся на символы
    List<Book> findBookByTitleStartingWith(String prefix);

    //+ получение данных у кого книга , явное указание запроса
//    @Query("SELECT b.owner FROM Book b WHERE b.id = :bookId")
//    Person getOwnerByBookId(@Param("bookId") int bookId);

    // освобождение книги(установка person_id в NULL)
//    @Modifying
//    @Query("UPDATE Book b SET b.owner = NULL WHERE b.id = :bookId")
//    void release(@Param("bookId") int id);

    // Назначение книги человеку
//    @Modifying
//    @Query("UPDATE Book b SET b.owner = :person WHERE b.id = :bookId")
//    void assignBook(@Param("bookId") int bookId, @Param("person") Person person);


}
