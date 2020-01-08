package com.jullak.habits.repository;

import com.jullak.habits.model.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill, Long> {
    List<Skill> findByUserAndDone(long user, boolean done);
    List<Skill> findByUser(long user);
}
