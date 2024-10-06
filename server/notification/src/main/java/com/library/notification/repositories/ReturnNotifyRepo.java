package com.library.notification.repositories;

import com.library.notification.entities.ReturnNotify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnNotifyRepo extends JpaRepository<ReturnNotify, Long> {
}
