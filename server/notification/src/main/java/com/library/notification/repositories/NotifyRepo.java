package com.library.notification.repositories;

import com.library.notification.entities.Notify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyRepo extends JpaRepository<Notify, Long> {
    Page<Notify> findByUserId(Long userId, Pageable pageable);
}
