package org.rangelstoilov.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User  implements UserDetails {
    private static final int BASE_NEXT_LEVEL_EXP = 500;
    private static final int BASE_MAX_HEALTH = 100;
    private static final int BASE_START_LEVEL = 1;
    private static final int BASE_EXPERIENCE = 0;
    private static final int BASE_HEALTH = 100;
    private static final int BASE_GOLD = 0;

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

    @Transient
    private Integer levelStartExp;

    @Transient
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "challenges_send",
            joinColumns = { @JoinColumn(name = "challenger_id") },
            inverseJoinColumns = { @JoinColumn(name = "opponent_id") })
    private List<User> challengesSend;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "challenges_pending",
            joinColumns = { @JoinColumn(name = "challenged_id") },
            inverseJoinColumns = { @JoinColumn(name = "opponent_id") })
    private List<User> challengesPending;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "challenges_accepted",
            joinColumns = { @JoinColumn(name = "challenger_id") },
            inverseJoinColumns = { @JoinColumn(name = "opponent_id") })
    private List<User> challengesAccepted;

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
        this.challengesSend = new ArrayList<>();
        this.challengesPending = new ArrayList<>();
        this.challengesAccepted = new ArrayList<>();
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

    public List<User> getChallengesSend() {
        return this.challengesSend;
    }

    public void setChallengesSend(List<User> challengesSend) {
        this.challengesSend = challengesSend;
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

    public Integer getLevelStartExp() {
        return (int) Math.pow(Math.max(0,this.getLevel()-1),2)*User.BASE_NEXT_LEVEL_EXP;
    }

    public Integer getNextLevelExp() {
        return (int) Math.pow(this.getLevel(),2)*User.BASE_NEXT_LEVEL_EXP;
    }

    public void levelUp() {
        this.setLevel(this.getLevel()+1);
        this.setMaxHealth(this.getMaxHealth()*2);
        this.setHealth(this.getMaxHealth());
    }

    public void dead() {
        this.setLevel(Math.max(1,this.getLevel()-1));
        this.setExperience(this.getLevelStartExp());
        this.setMaxHealth(Math.max(BASE_HEALTH,this.getMaxHealth()/2));
        this.setHealth(this.getMaxHealth());
        this.setGold(this.getGold()/2);
    }

    public Integer getChallengersMaxHealth() {
        int sum = this.challengesAccepted.stream().mapToInt(User::getMaxHealth).sum();
        return sum;
    }

    public Integer getChallengersCurrentHealth() {
        return this.challengesAccepted.stream().mapToInt(User::getHealth).sum();
    }

    public Integer getChallengersHealthPercentage() {
        if (getChallengersMaxHealth() != 0) {
            return (this.getChallengersCurrentHealth()/this.getChallengersMaxHealth())*100;
        } else {
            return 0;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
