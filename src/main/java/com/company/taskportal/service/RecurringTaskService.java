package com.company.taskportal.service;

public interface RecurringTaskService {

    /**
     * Creates all recurring task instances that are due.
     */
    void generateRecurringTasks();

}