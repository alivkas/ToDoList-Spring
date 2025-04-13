package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий отчета
 */
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}
