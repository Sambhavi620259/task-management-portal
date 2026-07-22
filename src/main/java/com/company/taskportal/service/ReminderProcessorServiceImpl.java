package com.company.taskportal.service;

import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskReminder;
import com.company.taskportal.repository.TaskReminderRepository;
import com.company.taskportal.service.EmailService;
import com.company.taskportal.service.ReminderProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReminderProcessorServiceImpl implements ReminderProcessorService {

    private final TaskReminderRepository taskReminderRepository;
    private final EmailService emailService;

    @Override
    public void processDueReminders() {

        List<TaskReminder> reminders =
                taskReminderRepository
                        .findByReminderDateTimeLessThanEqualAndSentFalseAndDeletedFalse(
                                LocalDateTime.now()
                        );

        if (reminders.isEmpty()) {
            log.debug("No due reminders found.");
            return;
        }

        for (TaskReminder reminder : reminders) {

            try {

                switch (reminder.getReminderType()) {

                    case EMAIL ->
                            sendEmailReminder(reminder);

                    case SMS ->
                            sendSmsReminder(reminder);

                    case PUSH_NOTIFICATION ->
                            sendPushReminder(reminder);

                    case IN_APP ->
                            sendInAppReminder(reminder);
                }

                reminder.setSent(true);
                reminder.setSentAt(LocalDateTime.now());

                taskReminderRepository.save(reminder);

                log.info("Reminder {} processed successfully.", reminder.getId());

            } catch (Exception ex) {

                log.error(
                        "Failed to process reminder {}",
                        reminder.getId(),
                        ex
                );
            }
        }
    }

    private void sendEmailReminder(TaskReminder reminder) {
        Task task = reminder.getTask();
        if (Boolean.TRUE.equals(task.getEmailNotification())) {
            emailService.sendTaskReminderEmail(task);
        }
    }

    private void sendSmsReminder(TaskReminder reminder) {
        log.info("Sending SMS reminder for Task {}", reminder.getTask().getTaskCode());
        // TODO: Integrate SMS Service
    }

    private void sendPushReminder(TaskReminder reminder) {
        log.info("Sending PUSH reminder for Task {}", reminder.getTask().getTaskCode());
        // TODO: Integrate Push Notification Service
    }

    private void sendInAppReminder(TaskReminder reminder) {
        log.info("Sending IN-APP reminder for Task {}", reminder.getTask().getTaskCode());
        // TODO: Integrate In-App Notification Service
    }

}