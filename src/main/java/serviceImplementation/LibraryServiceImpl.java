package serviceImplementation;

import baseModel.Person;
import model.Book;
import model.Library;
import service.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class LibraryServiceImpl implements LibraryService {

    Library library = new Library();

    @Override
    public void addBook(Book book) {
        library.getBooksList().computeIfAbsent(book.getBookTitle().toUpperCase(), k -> new ArrayList<>()).add(book);
    }

    @Override
    public void addQueue(Person person) {
        library.getQueue().add(person);
    }

    @Override
    public String issueBook(String bookTitle) {
        while (!library.getQueue().isEmpty()) {
            Person person1 = library.getQueue().poll();

            List<Book> booksList = library.getBooksList().get(bookTitle.toUpperCase());
            BookServiceImpl bookServiceImpl = new BookServiceImpl();

            return Optional.ofNullable(booksList).filter(list -> !list.isEmpty() && list.getFirst().getCopies() > 0).map(list -> {
                        Book availableBook = list.getFirst();
                        bookServiceImpl.subtractCopy.processCopy(availableBook);
                        assert person1 != null;
                        return "Book: " + bookTitle + ", is issued to "
                                + person1.getPersonGroup() + " " + person1.getName();
                    })
                    .orElse("Book: " + bookTitle + ", is not available.");
        }
        return "-1";
    }

    @Override
    public String returnBook(String bookTitle) {
        List<Book> booksList = library.getBooksList().get(bookTitle.toUpperCase());
        BookServiceImpl returnedBookImpl = new BookServiceImpl();

        booksList.forEach(book -> {
            returnedBookImpl.addCopy.processCopy(book);
            System.out.println(bookTitle + " has been returned and available for issue.");
        });

        // using Stream API and forEach
//        booksList.stream().filter(book -> book.getBookTitle().equalsIgnoreCase(bookTitle)).forEach(book -> returnedBookImpl.addCopy.processCopy(book));
        return "-1";
    }
}
