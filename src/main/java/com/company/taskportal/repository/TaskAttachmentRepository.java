package com.company.taskportal.repository;



import com.company.taskportal.entity.AttachmentType;

import com.company.taskportal.entity.Employee;

import com.company.taskportal.entity.Task;

import com.company.taskportal.entity.TaskAttachment;

import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;

import java.util.Optional;



public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, Long> {



    Optional<TaskAttachment> findByIdAndDeletedFalse(Long id);



    List<TaskAttachment> findByDeletedFalse();



    List<TaskAttachment> findByTaskAndDeletedFalse(Task task);



    List<TaskAttachment> findByUploadedByAndDeletedFalse(Employee uploadedBy);



    List<TaskAttachment> findByAttachmentTypeAndDeletedFalse(

            AttachmentType attachmentType

    );



    List<TaskAttachment> findByTaskAndAttachmentTypeAndDeletedFalse(

            Task task,

            AttachmentType attachmentType

    );



    List<TaskAttachment> findByTaskAndActiveTrueAndDeletedFalse(Task task);



    List<TaskAttachment> findByUploadedByAndActiveTrueAndDeletedFalse(

            Employee uploadedBy

    );



    long countByTaskAndDeletedFalse(Task task);



    long countByUploadedByAndDeletedFalse(Employee uploadedBy);



}