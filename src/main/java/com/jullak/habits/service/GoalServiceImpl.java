package com.jullak.habits.service;

import com.jullak.habits.model.Goal;
import com.jullak.habits.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    GoalRepository goalRepository;

    @Override
    public Optional<List<Goal>> getRealToday() throws Exception {
        Date today = Calendar.getInstance().getTime();
        Optional<List<Goal>> goals = goalRepository.findByToday(true);
        if (goals.isPresent()) {
            Iterator<Goal> itGoal = goals.get().iterator();

            while (itGoal.hasNext()) {
                Goal current = itGoal.next();
                if (current.getStartedToday().getDate() != today.getDate()
                    || current.getStartedToday().getMonth() != today.getMonth()
                    || current.getStartedToday().getYear() != today.getYear()) {
                    current.setToday(false);
                    goalRepository.save(current);
                }
            }

            Optional<List<Goal>> goalsAfter = goalRepository.findByToday(true);
        }

        return goals;
    }
}
