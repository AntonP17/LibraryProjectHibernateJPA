package org.example.services;

import org.example.model.Book;
import org.example.model.Person;
import org.example.repositories.BookRepository;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final PersonService personService;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonService personService, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personService = personService;
        this.personRepository = personRepository;
    }

    //+ получение всех книг
   public List<Book> index(int page, int booksPerPage, boolean sortByYear) {
      // return bookRepository.findAll();
      // Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page, booksPerPage));
      // return bookPage.getContent();
        Sort sort = sortByYear ? Sort.by("year") : Sort.unsorted();
       return bookRepository.findAll(PageRequest.of(page, booksPerPage, sort)).getContent();
   }

   // получение конкретной книги
   public Book show(int id){
       return bookRepository.findById(id).orElse(null);
   }

   // назначение книги
   public void assign(int bookId, int personId) {
//       Person person = personService.showById(personId);
//       if (person != null) {
//           bookRepository.assignBook(bookId, person);
//       }
       Optional<Book> bookOptional = bookRepository.findById(bookId);
       Optional<Person> personOptional = personRepository.findById(personId);

       if (bookOptional.isPresent() && personOptional.isPresent()){
           Book book = bookOptional.get();
           Person person = personOptional.get();
           book.setOwner(person);
           bookRepository.save(book);
       }

   }

   // поиск по префиксу
   public List<Book> findByPrefix(String prefix){

        List<Book> books = bookRepository.findBookByTitleStartingWith(prefix);

        if (books.isEmpty()) {
            return Collections.emptyList();
        } else {
            return books;
        }
   }

    //+ получение книг, взятых person
    public List<Book> getBooksByPersonId(int peopleId){
      //  return bookRepository.getBooksByPeopleId(peopleId);
        return bookRepository.findBookByOwnerId(peopleId);
    }

    //+ получение данных у кого книга
    public Person getOwnerByBookId(int bookId){
       // return bookRepository.getOwnerByBookId(bookId);
        return bookRepository.findById(bookId)
                .map(Book::getOwner)
                .orElse(null);
    }

    // сохранение
    public void save(Book book){
        bookRepository.save(book);
    }

    // освобождение книги
    public void release(int id){
       // bookRepository.release(id);
         Optional<Book> bookOptional = bookRepository.findById(id);

         if (bookOptional.isPresent()){
             Book book = bookOptional.get();
             book.setOwner(null);
             bookRepository.save(book);
         }

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
