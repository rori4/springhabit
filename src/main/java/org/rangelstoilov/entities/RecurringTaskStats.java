package org.rangelstoilov.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "recurring_task_stats")
public class RecurringTaskStats {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private Integer result;

    @ManyToOne
    @JoinColumn(name="recurring_task_id",referencedColumnName="id", nullable = false)
    private RecurringTask recurringTask;

    @Column
    private Date createdOn;

    public RecurringTaskStats() {
        this.createdOn = new Date();
    }

    public RecurringTaskStats(RecurringTask recurringTask, Integer result) {
        this.createdOn = new Date();
        this.recurringTask = recurringTask;
        this.result = result;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public RecurringTask getRecurringTask() {
        return this.recurringTask;
    }

    public void setRecurringTask(RecurringTask recurringTask) {
        this.recurringTask = recurringTask;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
