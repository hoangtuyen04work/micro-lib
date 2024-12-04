package com.library.message_service.entities;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long sender;
    String content;
    @ManyToOne
    Chat chat;
}
