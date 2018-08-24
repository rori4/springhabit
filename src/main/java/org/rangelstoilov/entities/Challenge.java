package org.rangelstoilov.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.rangelstoilov.custom.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "challenges")
public class Challenge {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    @Min(1)
    private Integer wager;

    @ManyToOne
    @JoinColumn(name="challenger_id", nullable=false)
    @JsonBackReference
    private User challenger;

    @ManyToOne
    @JoinColumn(name="opponent_id", nullable=false)
    @JsonBackReference
    private User opponent;

    @ManyToOne
    @JoinColumn(name="judge_id", nullable=false)
    @JsonBackReference
    private User judge;

    @Column
    private Date createdOn;

    @Column(nullable = false)
    @Enumerated
    private Status status;

    public Challenge() {
        this.createdOn = new Date();
        this.status = Status.ACTIVE;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getChallenger() {
        return this.challenger;
    }

    public void setChallenger(User challenger) {
        this.challenger = challenger;
    }

    public User getOpponent() {
        return this.opponent;
    }

    public void setOpponent(User opponent) {
        this.opponent = opponent;
    }

    public User getJudge() {
        return this.judge;
    }

    public void setJudge(User judge) {
        this.judge = judge;
    }

    public Integer getWager() {
        return this.wager;
    }

    public void setWager(Integer wager) {
        this.wager = wager;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
