package com.company.taskportal.security;

import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.User;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.TaskRepository;
import com.company.taskportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("taskSecurity")
@RequiredArgsConstructor
public class TaskSecurityService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public boolean canAccessTask(Long taskId, Authentication authentication) {

        User user = userRepository
                .findByUsernameAndDeletedFalse(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        // ADMIN can access everything
        if ("ADMIN".equals(user.getRole().name())) {
            return true;
        }

        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found."));

        // Employee can access only assigned tasks
        if (task.getAssignedTo() != null
                && task.getAssignedTo().getEmail().equals(user.getEmail())) {
            return true;
        }

        // Manager access (can be enhanced later)
        return "MANAGER".equals(user.getRole().name());
    }
}