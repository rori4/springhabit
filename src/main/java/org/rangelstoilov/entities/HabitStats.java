package org.rangelstoilov.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "habit_stats")
public class HabitStats {
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
    @JoinColumn(name="habit_id", nullable=false)
    private Habit habit;

    @Column
    private Date createdOn;

    public HabitStats() {
        this.createdOn = new Date();
    }

    public HabitStats(Integer result) {
        this.createdOn = new Date();
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

    public Habit getHabit() {
        return this.habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
