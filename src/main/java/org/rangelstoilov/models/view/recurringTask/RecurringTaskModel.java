package org.rangelstoilov.models.view.recurringTask;

import org.rangelstoilov.custom.enums.Period;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RecurringTaskModel {
    private String id;

    @NotNull
    @NotEmpty(message = "Title can not be empty")
    private String title;

    private String notes;

    @NotNull(message = "Please provide a recurring period")
    @Enumerated
    private Period resetPeriod;

    private Integer orderNumber;

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
