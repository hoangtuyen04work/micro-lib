package com.library.book_service.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@EntityListeners(BookListener.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long numberBorrowed;
    String bookCode;
    String name;
    LocalDate publicationDate;
    String edition;
    Long numberPage;
    @Lob
    String shortDescription;
    Long price;
    String author;
    Long number;
    String imageUrl;
    String language;
    @ManyToMany()
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    List<Category> categories;
}
