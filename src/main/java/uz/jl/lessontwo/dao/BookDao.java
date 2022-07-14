package uz.jl.lessontwo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import uz.jl.lessontwo.configs.HibernateConfigurer;
import uz.jl.lessontwo.domain.Book;
import uz.jl.lessontwo.domain.Uploads;

import java.util.List;

public class BookDao implements Dao {
    private static BookDao instance;

    public static BookDao getInstance() {
        if (instance == null) {
            instance = new BookDao();
        }
        return instance;
    }

    public List<Book> getAll(String search) {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Query<Book> query = currentSession.createQuery("select t from Book t where lower(t.name) like :query", Book.class);
        query.setParameter("query", "%" + search + "%");
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

    public void save(Uploads uploads) {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        currentSession.persist(uploads);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public List<Book> viewAllBooks(int i, int recordsPerPage, String search) {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Query<Book> query = currentSession.createQuery("select t from Book t where lower(t.name) like :query", Book.class);
        query.setParameter("query", "%" + search + "%");
        query.setFirstResult(i);
        query.setMaxResults(recordsPerPage);
        List<Book> books = query.getResultList();
        currentSession.getTransaction().commit();
        currentSession.close();
        return books;
    }
}
