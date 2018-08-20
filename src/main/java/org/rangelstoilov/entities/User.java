package org.rangelstoilov.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private Integer level;

    @Column
    private Integer nextLevelExp;

    @Column
    private Integer experience;

    @Column
    private  Integer health;

    @Column
    private Integer maxHealth;

    @Column
    private Integer gold;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToDo> todos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecurringTask> recurringTasks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Habit> habits;

    @OneToMany(mappedBy = "challenger", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Challenge> challengesCreated;

    @OneToMany(mappedBy = "opponent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Challenge> challengesAccepted;

    @OneToMany(mappedBy = "judge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Challenge> challengesJudging;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;


    public User() {
        this.nextLevelExp = 500;
        this.maxHealth = 100;
        this.level = 1;
        this.experience = 0;
        this.health = 100;
        this.gold = 0;
        this.todos = new ArrayList<>();
        this.recurringTasks = new ArrayList<>();
        this.habits = new ArrayList<>();
        this.challengesCreated = new ArrayList<>();
        this.challengesAccepted = new ArrayList<>();
        this.challengesJudging = new ArrayList<>();
        this.roles = new ArrayList<>();
    }

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

    public Integer getGold() {
        return this.gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Integer getNextLevelExp() {
        return this.nextLevelExp;
    }

    public void setNextLevelExp(Integer nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }
}
