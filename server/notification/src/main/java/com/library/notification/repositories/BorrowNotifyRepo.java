package com.library.notification.repositories;

import com.library.notification.entities.BorrowNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowNotifyRepo extends JpaRepository<BorrowNotify, Long> {
}
