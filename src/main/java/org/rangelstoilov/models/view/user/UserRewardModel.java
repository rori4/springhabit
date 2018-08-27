package org.rangelstoilov.models.view.user;

public class UserRewardModel {

    private Integer level;

    private Integer experience;

    private  Integer health;

    private  Integer gold;

    private  Integer kills;

    public UserRewardModel() {
        this.kills = 0;
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

    public Integer getKills() {
        return this.kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }
}
