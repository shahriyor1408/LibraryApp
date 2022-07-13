package uz.jl.lessontwo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import uz.jl.lessontwo.configs.HibernateConfigurer;
import uz.jl.lessontwo.domain.Book;

import java.util.List;

public class BookDao implements Dao {
    private static BookDao instance;

    public static BookDao getInstance() {
        if (instance == null) {
            instance = new BookDao();
        }
        return instance;
    }

    public List<Book> getAll() {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Query<Book> query = currentSession.createQuery("select t from Book t", Book.class);
        List<Book> books = query.getResultList();
        currentSession.getTransaction().commit();
        currentSession.close();
        return books;
    }

    public void save(Book book) {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        currentSession.persist(book);
        currentSession.getTransaction().commit();
        currentSession.close();
    }
}
