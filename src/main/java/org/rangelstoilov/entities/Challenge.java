//package org.rangelstoilov.entities;
//
//import org.hibernate.annotations.GenericGenerator;
//import org.rangelstoilov.custom.enums.ChallengeStatus;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "challenges")
//public class Challenge {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "id", updatable = false, nullable = false)
//    private String id;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name="challenger_id", nullable=false)
//    private User challenger;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name="opponent_id", nullable=false)
//    private User opponent;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name="judge_id")
//    private User judge;
//
//    @Column
//    private Date createdOn;
//
//    @Column(nullable = false)
//    @Enumerated
//    private ChallengeStatus challengeStatus;
//
//    public Challenge() {
//        this.createdOn = new Date();
//        this.challengeStatus = ChallengeStatus.PENDING;
//    }
//
//    public Challenge(User challenger, User opponent) {
//        this.challenger = challenger;
//        this.opponent = opponent;
//        this.createdOn = new Date();
//        this.challengeStatus = ChallengeStatus.PENDING;
//    }
//
//    public String getId() {
//        return this.id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public User getChallenger() {
//        return this.challenger;
//    }
//
//    public void setChallenger(User challenger) {
//        this.challenger = challenger;
//    }
//
//    public User getOpponent() {
//        return this.opponent;
//    }
//
//    public void setOpponent(User opponent) {
//        this.opponent = opponent;
//    }
//
//    public User getJudge() {
//        return this.judge;
//    }
//
//    public void setJudge(User judge) {
//        this.judge = judge;
//    }
//
//    public ChallengeStatus getTaskStatus() {
//        return this.challengeStatus;
//    }
//
//    public void setTaskStatus(ChallengeStatus challengeStatus) {
//        this.challengeStatus = challengeStatus;
//    }
//
//    public Date getCreatedOn() {
//        return this.createdOn;
//    }
//
//    public void setCreatedOn(Date createdOn) {
//        this.createdOn = createdOn;
//    }
//}
