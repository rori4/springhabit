package org.rangelstoilov.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    public static final int BASE_NEXT_LEVEL_EXP = 500;
    public static final int BASE_MAX_HEALTH = 100;
    public static final int BASE_START_LEVEL = 1;
    public static final int BASE_EXPERIENCE = 0;
    public static final int BASE_HEALTH = 100;
    public static final int BASE_GOLD = 0;

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
        this.maxHealth = BASE_MAX_HEALTH;
        this.level = BASE_START_LEVEL;
        this.experience = BASE_EXPERIENCE;
        this.health = BASE_HEALTH;
        this.gold = BASE_GOLD;
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

    public void levelUp() {
        this.setLevel(this.getLevel()+1);
        this.setHealth(this.getMaxHealth());
    }

    public int getNextLevelExp(){
        return (int) Math.pow(this.getLevel(),2)*User.BASE_NEXT_LEVEL_EXP;
    }
}
