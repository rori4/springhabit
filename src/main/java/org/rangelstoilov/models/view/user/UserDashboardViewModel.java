package org.rangelstoilov.models.view.user;

import org.rangelstoilov.entities.*;

import java.util.List;

public class UserDashboardViewModel {

    private String name;

    private String email;

    private String password;

    private Integer level;

    private Integer nextLevelExp;

    private Integer experience;

    private Integer maxHealth;

    private  Integer health;

    private Integer gold;

    private List<ToDo> todos;

    private List<RecurringTask> recurringTasks;

    private List<Habit> habits;

    private List<Challenge> challengesCreated;

    private List<Challenge> challengesAccepted;

    private List<Challenge> challengesJudging;

    private List<Role> roles;

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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<ToDo> getTodos() {
        return this.todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    public List<RecurringTask> getRecurringTasks() {
        return this.recurringTasks;
    }

    public void setRecurringTasks(List<RecurringTask> recurringTasks) {
        this.recurringTasks = recurringTasks;
    }

    public List<Habit> getHabits() {
        return this.habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
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

    public List<Role> getRoles() {
        return this.roles;
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
}
