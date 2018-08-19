package org.rangelstoilov.entities;

import org.hibernate.annotations.GenericGenerator;
import org.rangelstoilov.custom.enums.Period;
import org.rangelstoilov.custom.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "habits")
public class Habit {
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
    private Period resetPeriod;

    @Column(nullable = false)
    @Enumerated
    private Status status;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Habit() {
        this.createdOn = new Date();
        this.count = 0;
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

    public Period getResetPeriod() {
        return this.resetPeriod;
    }

    public void setResetPeriod(Period resetPeriod) {
        this.resetPeriod = resetPeriod;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
