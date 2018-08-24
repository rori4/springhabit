package org.rangelstoilov.models.view.user;

import org.rangelstoilov.entities.Challenge;

import java.util.List;

public class UserDashboardViewModel {

    private String name;

    private String email;

    private Integer level;

    private Integer levelStartExp;

    private Integer nextLevelExp;

    private Integer experience;

    private Integer maxHealth;

    private  Integer health;

    private Integer gold;

    private List<Challenge> challengesCreated;

    private List<Challenge> challengesAccepted;

    private List<Challenge> challengesJudging;

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

    public List<Challenge> getChallengesCreated() {
        return this.challengesCreated;
    }

    public void setChallengesCreated(List<Challenge> challengesCreated) {
        this.challengesCreated = challengesCreated;
    }

    public List<Challenge> getChallengesAccepted() {
        return this.challengesAccepted;
    }

    public void setChallengesAccepted(List<Challenge> challengesAccepted) {
        this.challengesAccepted = challengesAccepted;
    }

    public List<Challenge> getChallengesJudging() {
        return this.challengesJudging;
    }

    public void setChallengesJudging(List<Challenge> challengesJudging) {
        this.challengesJudging = challengesJudging;
    }
}
