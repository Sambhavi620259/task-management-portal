package com.company.taskportal.service;

import com.company.taskportal.entity.Task;
import com.company.taskportal.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(
            String to,
            String subject,
            String body
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(subject);

        message.setText(body);

        mailSender.send(message);

        log.info("Email sent successfully to {}", to);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    "UTF-8"
            );

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);

            log.info("HTML email sent successfully to {}", to);

        } catch (Exception ex) {

            log.error("Failed to send HTML email.", ex);

            throw new RuntimeException("Unable to send HTML email.", ex);
        }
    }

    @Override
    public void sendTaskReminderEmail(Task task) {

        if (task == null || task.getAssignedTo() == null || task.getAssignedTo().getEmail() == null) {
            return;
        }

        String employeeName = task.getAssignedTo().getFirstName() + " " +
                task.getAssignedTo().getLastName();

        String subject = "Task Reminder : " + task.getTaskCode();

        String body = """
            Dear %s,

            This is a reminder for your assigned task.

            Task Details
            -------------------------
            Task Code : %s
            Task Name : %s
            Due Date  : %s
            Priority  : %s
            Status    : %s

            Please complete the task before the due date.

            Regards,
            Task Management System
            """
                .formatted(
                        employeeName,
                        task.getTaskCode(),
                        task.getTaskName(),
                        task.getDueDate() != null ? task.getDueDate() : "N/A",
                        task.getPriority() != null ? task.getPriority().name() : "N/A",
                        task.getStatus() != null ? task.getStatus().name() : "N/A"
                );

        sendEmail(task.getAssignedTo().getEmail(), subject, body);
    }

    @Override
    public void sendOverdueTaskEmail(Task task) {

        if (task == null || task.getAssignedTo() == null || task.getAssignedTo().getEmail() == null) {
            return;
        }

        String employeeName = task.getAssignedTo().getFirstName() + " " +
                task.getAssignedTo().getLastName();

        String subject = "Overdue Task : " + task.getTaskCode();

        String body = """
            Dear %s,

            Your assigned task is overdue.

            Task Details
            -------------------------
            Task Code : %s
            Task Name : %s
            Due Date  : %s
            Priority  : %s

            Please complete this task immediately.

            Regards,
            Task Management System
            """
                .formatted(
                        employeeName,
                        task.getTaskCode(),
                        task.getTaskName(),
                        task.getDueDate() != null ? task.getDueDate() : "N/A",
                        task.getPriority() != null ? task.getPriority().name() : "N/A"
                );

        sendEmail(task.getAssignedTo().getEmail(), subject, body);
    }

    @Override
    public void sendTaskCompletionEmail(Task task) {

        if (task == null || task.getAssignedTo() == null || task.getAssignedTo().getEmail() == null) {
            return;
        }

        String employeeName = task.getAssignedTo().getFirstName() + " " +
                task.getAssignedTo().getLastName();

        String subject = "Task Completed : " + task.getTaskCode();

        String body = """
            Dear %s,

            Congratulations!

            The following task has been completed successfully.

            Task Details
            -------------------------
            Task Code    : %s
            Task Name    : %s
            Completed On : %s
            Completion   : %d%%

            Thank you for your contribution.

            Regards,
            Task Management System
            """
                .formatted(
                        employeeName,
                        task.getTaskCode(),
                        task.getTaskName(),
                        task.getCompletedDate() != null ? task.getCompletedDate() : "N/A",
                        task.getCompletionPercentage() != null ? task.getCompletionPercentage() : 100
                );

        sendEmail(task.getAssignedTo().getEmail(), subject, body);
    }
    @Override
    public void sendHtmlEmail(
            String to,
            String subject,
            String htmlBody) {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            mimeMessage,
                            true,
                            "UTF-8"
                    );

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);

            log.info("HTML email sent successfully to {}", to);

        } catch (Exception ex) {

            log.error("Unable to send email.", ex);

            throw new RuntimeException("Email sending failed.", ex);
        }
    }
}