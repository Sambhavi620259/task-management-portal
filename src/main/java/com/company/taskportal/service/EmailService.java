package com.company.taskportal.service;

import com.company.taskportal.entity.Task;

public interface EmailService {

    void sendEmail(String to,
                   String subject,
                   String body);

    void sendHtmlEmail(String to,
                       String subject,
                       String htmlBody);

    void sendTaskAssignmentEmail(Task task);

    void sendTaskReminderEmail(Task task);

    void sendOverdueTaskEmail(Task task);

    void sendTaskCompletionEmail(Task task);

}