package com.jullak.habits.service;

import com.jullak.habits.model.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    Optional<List<Goal>> getRealToday() throws Exception;
}
