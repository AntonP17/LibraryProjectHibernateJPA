package org.example.services;

import org.example.model.Book;
import org.example.model.Person;
import org.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //+ получение всех книг
   public List<Book> index(){
       return bookRepository.findAll();
   }

   // получение конкретной книги
   public Book show(int id){
       return bookRepository.findById(id).orElse(null);
   }

   // назначение книги
    public void assign(int bookId, int personId){
        bookRepository.assignBook(bookId, personId);
    }

    //+ получение книг, взятых person
    public List<Book> getBooksByPersonId(int personId){
        return bookRepository.getBooksByPersonId(personId);
    }

    //+ получение данных у кого книга
    public Person getOwnerByBookId(int bookId){
        return bookRepository.getOwnerByBookId(bookId);
    }

    // сохранение
    public void save(Book book){
        bookRepository.save(book);
    }

    // освобождение книги
    public void release(int id){
        bookRepository.release(id);
    }

    // обновление
    public void update(int id, Book updatedBook){

        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setYear(updatedBook.getYear());
            bookRepository.save(updatedBook);
        }
    }

    // удаление
    public void delete(int id){
        bookRepository.deleteById(id);
    }
}
