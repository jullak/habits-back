package com.jullak.habits.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jullak.habits.model.Goal;
import com.jullak.habits.model.Skill;
import com.jullak.habits.repository.GoalRepository;
import com.jullak.habits.service.GoalService;
import com.jullak.habits.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.swing.text.html.Option;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping(value = "goal")
public class GoalController {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    SkillService skillService;

    @Autowired
    GoalService goalService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createGoal (@RequestParam String name, @RequestParam long skillId,
                                              @RequestParam(required = false) Optional<Long> givenPoint) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        Goal goal = new Goal(name, skillId);
        if (givenPoint.isPresent()) {
            goal.setGivenPoint(givenPoint.get());
        }

        try {
            Goal createdGoal = goalRepository.save(goal);
            result = gson.toJsonTree(createdGoal).getAsJsonObject();
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @PostMapping(value = "/edit/{goalId}")
    public ResponseEntity<String> editGoal(@PathVariable("goalId") long goalId,
                                           @RequestParam(required = false) Optional<String> name,
                                           @RequestParam(required = false) Optional<Long> givenPoint,
                                           @RequestParam(required = false) Optional<Long> skillId) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<Goal> updatedGoal = goalRepository.findById(goalId);
            if (updatedGoal.isPresent()) {

                if (name.isPresent()) {
                    updatedGoal.get().setName(name.get());
                }
                if (givenPoint.isPresent()) {
                    updatedGoal.get().setGivenPoint(givenPoint.get());
                }
                if (skillId.isPresent()) {
                    updatedGoal.get().setSkill(skillId.get());
                }

                Goal goal = goalRepository.save(updatedGoal.get());

                result = gson.toJsonTree(goal).getAsJsonObject();
                result.addProperty("gettingResult", "exists");
            } else {
                result.addProperty("gettingResult", "none");
            }
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<String> getAllGoals(@RequestParam long skillId,
                                              @RequestParam(required = false) Optional<Boolean> done) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<List<Goal>> updated = goalService.getRealToday(); //strange way to update today...

            if (done.isPresent()) {
                List<Goal> goals = goalRepository.findBySkillAndDone(skillId, done.get());
                result.add("goals", gson.toJsonTree(goals));
            } else {
                List<Goal> goals = goalRepository.findBySkill(skillId);
                result.add("goals", gson.toJsonTree(goals));
            }
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @GetMapping(value = "/get/{goalId}")
    public ResponseEntity<String> getGoalById(@PathVariable("goalId") long goalId, @RequestParam(required = false) Optional<Boolean> done) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<Goal> goal = goalRepository.findById(goalId);
            if (goal.isPresent()) {
                result = gson.toJsonTree(goal.get()).getAsJsonObject();
                result.addProperty("gettingResult", "exists");
            } else {
                result.addProperty("gettingResult", "none");
            }
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @GetMapping(value = "/get/daily")
    public ResponseEntity<String> getTodayGoals() {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<List<Goal>> goals = goalService.getRealToday();
            if (goals.isPresent()) {
                result.add("goals", gson.toJsonTree(goals.get()));
                result.addProperty("value", "exists");
            } else {
                result.addProperty("value", "none");
            }
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @PostMapping(value = "/edit/today")
    public ResponseEntity<String> setToday (@RequestParam long goalId) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<Goal> goal = goalRepository.findById(goalId);
            if (goal.isPresent()) {
                goal.get().setToday(true);
                goal.get().setStartedToday(Calendar.getInstance().getTime());
                Goal updated = goalRepository.save(goal.get());

                result = gson.toJsonTree(goal.get()).getAsJsonObject();
            }
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @PostMapping(value = "/edit/done")
    public ResponseEntity<String> setDone (@RequestParam long goalId) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<Goal> goal = goalRepository.findById(goalId);
            if (goal.isPresent()) {
                skillService.addPoint(goal.get().getSkill(), goal.get().getGivenPoint());
                goal.get().setFinished(Calendar.getInstance().getTime());

                goal.get().setDone(true);
                Goal updated = goalRepository.save(goal.get());

                result = gson.toJsonTree(updated).getAsJsonObject();
            }
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

}
