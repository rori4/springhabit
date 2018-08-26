package org.rangelstoilov.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.rangelstoilov.custom.enums.TaskStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column
    private String notes;

    @Column
    private Date dueDate;

    @Column
    private Date createdOn;

    @Column
    private Date completedOn;

    @Column(nullable = false)
    private Integer orderNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="user_id",referencedColumnName="id",nullable=false)
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    @Enumerated
    private TaskStatus taskStatus;

    public ToDo() {
        this.createdOn = new Date();
        this.taskStatus = TaskStatus.ACTIVE;
    }

    public ToDo(User user, Integer orderNumber) {
        this.createdOn = new Date();
        this.taskStatus = TaskStatus.ACTIVE;
        this.user = user;
        this.orderNumber = orderNumber;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getCompletedOn() {
        return this.completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }
}
