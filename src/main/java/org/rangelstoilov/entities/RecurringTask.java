package org.rangelstoilov.entities;

import org.hibernate.annotations.GenericGenerator;
import org.rangelstoilov.custom.enums.Period;
import org.rangelstoilov.custom.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "recurring_tasks")
public class RecurringTask {
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
    private Integer count;

    @Column
    private Date createdOn;

    @Column(nullable = false)
    @Enumerated
    private Status status;

    @Column(nullable = false)
    @Enumerated
    private Period resetPeriod;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable = false)
    private Integer orderNumber;

    public RecurringTask() {
        this.count = 0;
        this.createdOn = new Date();
        this.status = Status.ACTIVE;
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

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Period getResetPeriod() {
        return this.resetPeriod;
    }

    public void setResetPeriod(Period resetPeriod) {
        this.resetPeriod = resetPeriod;
    }

    public Integer getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
