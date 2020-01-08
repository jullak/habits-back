package com.jullak.habits.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long maxPoint = 100;

    @Column(nullable = false)
    private long currentPoint = 0;

    @Column(nullable = false)
    private boolean done = false;

    @OneToMany(mappedBy = "skill", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Goal> skillGoals;

    @Column(name = "user_id", nullable = false)
    private long user;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date started = Calendar.getInstance().getTime();

    @Column
    @Temporal(TemporalType.DATE)
    private java.util.Date finished;

    public Skill() {}

    public Skill(String name, long user) {
        this.name = name;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(long maxPoint) {
        this.maxPoint = maxPoint;
    }

    public long getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(long currentPoint) {
        this.currentPoint = currentPoint;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<Goal> getSkillGoals() {
        return skillGoals;
    }

    public void setSkillGoals(Set<Goal> skillGoals) {
        this.skillGoals = skillGoals;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }
}
