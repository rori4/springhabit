package org.rangelstoilov.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
    private Integer wager;

    @ManyToOne
    @JoinColumn(name="challenger_id", nullable=false)
    private User challenger;

    @ManyToOne
    @JoinColumn(name="opponent_id", nullable=false)
    private User opponent;

    @ManyToOne
    @JoinColumn(name="judge_id", nullable=false)
    private User judge;

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
}
