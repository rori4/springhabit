package org.rangelstoilov.models.view.user;

import org.rangelstoilov.entities.Role;
import org.rangelstoilov.entities.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UserAdminViewModel {

    @NotNull
    @NotEmpty(message = "Id can't be empty")
    private String id;

    @NotNull
    @NotEmpty(message = "Name can not be empty")
    private String name;

    private String email;

    @NotNull(message = "Level can not be empty")
    @Min(value = 1, message = "Minimum level must be 1")
    private Integer level;

    private Integer levelStartExp;

    private Integer nextLevelExp;

    @NotNull(message = "Experience can not be empty")
    @Min(value = 0, message = "Minimum XP is 0")
    private Integer experience;

    @NotNull(message = "Max health can not be empty")
    @Min(value = 100, message = "Minimum max health is 100")
    private Integer maxHealth;

    @NotNull(message = "Health can not be empty")
    @Min(value = 1, message = "Minimum current health is 1")
    private  Integer health;

    @NotNull(message = "Gold can not be empty")
    @Min(value = 0, message = "Minimum gold is 0")
    private Integer gold;

    private List<User> challengesPending;

    private List<User> challengesAccepted;

    @NotEmpty(message = "Roles can not be empty")
    private List<Role> roles;

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

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getChallengersMaxHealth() {
        return this.challengesAccepted.stream().mapToInt(User::getMaxHealth).sum();
    }

    public Integer getChallengersCurrentHealth() {
        return this.challengesAccepted.stream().mapToInt(User::getHealth).sum();
    }
}
