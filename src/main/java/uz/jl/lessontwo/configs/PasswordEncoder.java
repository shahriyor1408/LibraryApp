package uz.jl.lessontwo.configs;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(8));
    }

    public static boolean match(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

}
