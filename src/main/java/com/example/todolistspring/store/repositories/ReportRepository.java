package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.ReportEntity;
import com.example.todolistspring.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий отчета
 */
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    List<ReportEntity> findByCompletedTasksContainsOrOverdueTasksContains(TaskEntity task, TaskEntity task1);
}
