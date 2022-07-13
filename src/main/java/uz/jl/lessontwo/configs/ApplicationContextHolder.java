package uz.jl.lessontwo.configs;

import uz.jl.lessontwo.dao.Dao;
import uz.jl.lessontwo.dao.UserDao;

import javax.swing.text.DefaultEditorKit;

public class ApplicationContextHolder {
    public static <T> T getBean(Class<? extends Dao> clazz) {
        return switch (clazz.getSimpleName()) {
            case "UserDao" -> (T) UserDao.getInstance();
            default -> throw new BeanNotFoundException("Bean not found");
        };
    }

    public static class BeanNotFoundException extends RuntimeException {
        public BeanNotFoundException(String message) {
            super(message);
        }
    }
}

