package com.library.message_service.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String chatId;
    Long userId1;
    Long userId2;
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    List<Message> messages;
}
