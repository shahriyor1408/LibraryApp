package uz.jl.lessontwo.configs;

import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.dao.Dao;
import uz.jl.lessontwo.dao.UserDao;
import uz.jl.lessontwo.service.FileStorageService;
import uz.jl.lessontwo.service.FileStorageServiceImpl;

import javax.swing.text.DefaultEditorKit;

public class ApplicationContextHolder {
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clazz) {
        return switch (clazz.getSimpleName()) {
            case "UserDao" -> (T) UserDao.getInstance();
            case "BookDao" -> (T) BookDao.getInstance();
            case "FileStorageServiceImpl" -> (T) FileStorageServiceImpl.getInstance();
            default -> throw new BeanNotFoundException("Bean not found");
        };
    }

    public static class BeanNotFoundException extends RuntimeException {
        public BeanNotFoundException(String message) {
            super(message);
        }
    }
}

