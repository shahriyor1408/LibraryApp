package uz.jl.lessontwo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import uz.jl.lessontwo.configs.HibernateConfigurer;
import uz.jl.lessontwo.domain.Book;
import uz.jl.lessontwo.domain.Uploads;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Types;
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
        Query<Book> query = currentSession.createQuery("select t from Book t where lower(t.name) like :query and t.status = 'ACTIVE'", Book.class);
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
        Query<Book> query = currentSession.createQuery("select t from Book t where lower(t.name) like :query and t.status = 'ACTIVE'", Book.class);
        query.setParameter("query", "%" + search + "%");
        query.setFirstResult(i);
        query.setMaxResults(recordsPerPage);
        List<Book> books = query.getResultList();
        currentSession.getTransaction().commit();
        currentSession.close();
        return books;
    }

    public Uploads getByPath(String requestedFile) {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Query<Uploads> query = currentSession.createQuery("select t from Uploads t where t.path like :query", Uploads.class);
        query.setParameter("query", requestedFile);
        Uploads result = query.getSingleResult();
        currentSession.getTransaction().commit();
        currentSession.close();
        return result;
    }

    public List<Book> getAllPendingBooks() {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Query<Book> query = currentSession.createQuery("select t from Book t where t.status = 'PENDING'", Book.class);
        List<Book> resultList = query.getResultList();
        currentSession.getTransaction().commit();
        currentSession.close();
        return resultList;
    }

    public Book getById(String id) {
        SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.getTransaction().begin();
        Query<Book> query = currentSession.createQuery("select t from Book t where t.id = :id", Book.class);
        query.setParameter("id", id);
        Book result = query.getSingleResult();
        currentSession.getTransaction().commit();
        currentSession.close();
        return result;
    }

    public void confirm(String id) {
        Session session = HibernateConfigurer.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update Book set status = uz.jl.lessontwo.enums.BookStatus.ACTIVE where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deny(String id) {
        Session session = HibernateConfigurer.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Book where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
