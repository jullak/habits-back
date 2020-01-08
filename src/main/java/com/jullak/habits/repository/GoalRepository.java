package com.jullak.habits.repository;

import com.jullak.habits.model.Goal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends CrudRepository<Goal, Long> {

    List<Goal> findBySkillAndDone(long skill, boolean done);

    List<Goal> findBySkill(long skill);

    Optional<List<Goal>> findByToday(boolean today);
}
