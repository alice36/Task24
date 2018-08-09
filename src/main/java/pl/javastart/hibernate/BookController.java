package pl.javastart.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    @PersistenceUnit
    @Autowired
    private EntityManagerFactory factory;

    @GetMapping("/")
    public String homeAdd(Model model) {

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);

        List<Book> allBooks = query.getResultList();
        model.addAttribute("allBooks",allBooks);

        return "index";
    }

    @GetMapping("/addbookside")
    public String homeAddBook(Model model) {
        Book newBook = new Book();
        newBook.setTitle("Dzieci z Bullerbyn");
        model.addAttribute("newBook", newBook);
        model.addAttribute("allCategories",Category.values());

        return "bookform";
    }

    @PostMapping("/addbook")
    public String addBook(Book book) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(book);
        entityManager.getTransaction().commit();
        return "redirect:/";
    }

    @GetMapping("/deletebookside")
    public String homeDeleteBook(Model model) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);

        List<Book> allBooks = query.getResultList();
        model.addAttribute("allBooks", allBooks);
        model.addAttribute("delBook", new Book());

        return "bookdelete";
    }

    @PostMapping("/deletebook")
    public String deleteBook(Book book) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Book book1 = entityManager.find(Book.class, book.getId());

        entityManager.remove(book1);
        transaction.commit();

        return "redirect:/";
    }

    @GetMapping("/editbookside")
    public String homeEditBook(Model model) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);

        List<Book> allBooks = query.getResultList();
        model.addAttribute("allBooks",allBooks);
        model.addAttribute("newBook",new Book());
        return "bookedit";
    }

    @PostMapping("/editbook")
    public String editBook(Book book, Model model) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Book book1 = entityManager.find(Book.class, book.getId());

        model.addAttribute("bookEdit",book1);
        model.addAttribute("allCategories",Category.values());

        return "bookeditform";
    }

    @PostMapping("/editedbook")
    public String editBook(Book book) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Book book1 = entityManager.find(Book.class, book.getId());

        transaction.begin();
        book1.setTitle(book.getTitle());
        book1.setAuthor(book.getAuthor());
        book1.setCategory(book.getCategory());
        book1.setIsbn(book.getIsbn());
        book1.setIssueDate(book.getIssueDate());
        transaction.commit();

        return "redirect:/";
    }

    @GetMapping("/sortlist")
    public String editBook( Model model) {
        String field = "";
        List<String> stringList = new ArrayList<>();
        stringList.add("tytul");
        stringList.add("isbn");
        stringList.add("data wydania");

        model.addAttribute("list", stringList);
//        model.addAttribute("field", field);
        return "sortview";
    }

    @PostMapping("/sorted")
    public String editBook(@RequestParam("field") String field) {



        return "redirect:/";
    }
}
