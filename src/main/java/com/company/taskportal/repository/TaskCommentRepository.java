package com.company.taskportal.repository;

import com.company.taskportal.entity.CommentType;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    Optional<TaskComment> findByIdAndDeletedFalse(Long id);

    List<TaskComment> findByDeletedFalse();

    List<TaskComment> findByTaskAndDeletedFalse(Task task);

    List<TaskComment> findByEmployeeAndDeletedFalse(Employee employee);

    List<TaskComment> findByCommentTypeAndDeletedFalse(CommentType commentType);

    List<TaskComment> findByTaskAndCommentTypeAndDeletedFalse(
            Task task,
            CommentType commentType
    );

    long countByTaskAndDeletedFalse(Task task);

    long countByEmployeeAndDeletedFalse(Employee employee);

}