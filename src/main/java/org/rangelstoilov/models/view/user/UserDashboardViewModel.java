package org.rangelstoilov.models.view.user;

import org.rangelstoilov.entities.User;

import java.util.List;

public class UserDashboardViewModel {

    private String id;

    private String name;

    private String email;

    private Integer level;

    private Integer levelStartExp;

    private Integer nextLevelExp;

    private Integer experience;

    private Integer maxHealth;

    private  Integer health;

    private Integer gold;

    private List<User> challengesPending;

    private List<User> challengesAccepted;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return this.experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getHealth() {
        return this.health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getGold() {
        return this.gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getLevelStartExp() {
        return this.levelStartExp;
    }

    public void setLevelStartExp(Integer levelStartExp) {
        this.levelStartExp = levelStartExp;
    }

    public Integer getNextLevelExp() {
        return this.nextLevelExp;
    }

    public void setNextLevelExp(Integer nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public Integer getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
    }

    public List<User> getChallengesPending() {
        return this.challengesPending;
    }

    public void setChallengesPending(List<User> challengesPending) {
        this.challengesPending = challengesPending;
    }

    public List<User> getChallengesAccepted() {
        return this.challengesAccepted;
    }

    public void setChallengesAccepted(List<User> challengesAccepted) {
        this.challengesAccepted = challengesAccepted;
    }
}
