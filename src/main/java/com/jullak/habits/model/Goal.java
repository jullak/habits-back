package com.jullak.habits.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long givenPoint = 10;

    @Column(nullable = false)
    private boolean done = false;

    @Column(name = "skill_id", nullable = false)
    private long skill;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date started = Calendar.getInstance().getTime();

    @Column
    @Temporal(TemporalType.DATE)
    private java.util.Date finished;

    @Column(nullable = false)
    private boolean today = false;

    @Column(nullable = false)
    private boolean repeatedToday = false;

    @Column
    @Temporal(TemporalType.DATE)
    private java.util.Date startedToday = Calendar.getInstance().getTime();

    public Goal() {}

    public Goal(String name, long skill) {
        this.name = name;
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGivenPoint() {
        return givenPoint;
    }

    public void setGivenPoint(long givenPoint) {
        this.givenPoint = givenPoint;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getSkill() {
        return skill;
    }

    public void setSkill(long skill) {
        this.skill = skill;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

    public boolean isRepeatedToday() {
        return repeatedToday;
    }

    public void setRepeatedToday(boolean repeatedToday) {
        this.repeatedToday = repeatedToday;
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

    public Date getStartedToday() {
        return startedToday;
    }

    public void setStartedToday(Date startedToday) {
        this.startedToday = startedToday;
    }
}
