package org.rangelstoilov.models.view.habit;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class HabitModel {

    private String id;

    @NotNull
    @NotEmpty(message = "Title can not be empty")
    private String title;

    private String notes;

    private Integer orderNumber;

    private Date createdOn;

    private List<HabitStatsModel> habitStats;

    public HabitModel() {
        this.createdOn = new Date();
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

    public Integer getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<HabitStatsModel> getHabitStats() {
        return this.habitStats;
    }

    public void setHabitStats(List<HabitStatsModel> habitStats) {
        this.habitStats = habitStats;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
