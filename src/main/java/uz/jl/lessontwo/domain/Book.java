package uz.jl.lessontwo.domain;

import jakarta.persistence.*;
import lombok.*;
import uz.jl.lessontwo.enums.BookStatus;
import uz.jl.lessontwo.enums.Language;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String author;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.PENDING;
    @Enumerated(EnumType.STRING)
    private Language language;
    private int pageCount;
    private int downloadCount;

    @AllArgsConstructor
    @Getter
    public enum Genre {
        CRIME("Crime"),
        HORROR("Horror"),
        CI_FI("Ci-fi"),
        DRAMA("Drama"),
        ROMANCE("Romance"),
        ROMANCE_DRAMA("Romance Drama"),
        FANTASY("Fantasy");
        private final String key;

        public String getKey() {
            return key;
        }
    }

}
