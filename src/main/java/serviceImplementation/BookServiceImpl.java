package serviceImplementation;

import service.BookService;

// lambda implementation
public class BookServiceImpl{
    BookService subtractCopy = (book) -> {
        int copies = book.getCopies();
        if (copies != 0) {
            book.setCopies(copies - 1);
        }
    };

    BookService addCopy = book -> {
        int copies = book.getCopies();
        book.setCopies(copies + 1);
    };
}

