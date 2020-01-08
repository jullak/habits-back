package com.jullak.habits.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jullak.habits.model.Skill;
import com.jullak.habits.repository.SkillRepository;
import com.jullak.habits.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "skill")
public class SkillController {
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    SkillService skillService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createSkill(@RequestParam String name, @RequestParam(value = "maxPoint", required = false) Optional<Long> maxPoint,
                                              @RequestParam long userId) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        Skill skill = new Skill(name, userId);
        if (maxPoint.isPresent()) {
            skill.setMaxPoint(maxPoint.get());
        }

        try {
            Skill createdSkill = skillRepository.save(skill);
            result = gson.toJsonTree(createdSkill).getAsJsonObject();
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<String> getAllSkills(@RequestParam long userId, @RequestParam(required = false) Optional<Boolean> done) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            if (done.isPresent()) {
                List<Skill> skills = skillRepository.findByUserAndDone(userId, done.get());
                result.addProperty("skills", gson.toJson(skills));
            } else {
                List<Skill> skills = skillRepository.findByUser(userId);
                result.addProperty("skills", gson.toJson(skills));
            }
        } catch (Exception e) {
            result.addProperty("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }

        return ResponseEntity.ok().body(result.toString());
    }

    @GetMapping(value = "/get/{skillId}")
    public ResponseEntity<String> getSkillsById(@PathVariable("skillId") long skillId) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<Skill> skill = skillRepository.findById(skillId);
            if (skill.isPresent()) {
                result = gson.toJsonTree(skill.get()).getAsJsonObject();
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

    @PostMapping(value = "/edit/{skillId}")
    public ResponseEntity<String> editSkill(@PathVariable("skillId") long skillId,
                                            @RequestParam(required = false) Optional<String> name,
                                            @RequestParam(required = false) Optional<Long> maxPoint) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        boolean isDone = false;

        try {
            Optional<Skill> updatedSkill = skillRepository.findById(skillId);
            if (updatedSkill.isPresent()) {

                if (name.isPresent()) {
                    updatedSkill.get().setName(name.get());
                }
                if (maxPoint.isPresent()) {
                    isDone = skillService.isDone(skillId, maxPoint.get(), updatedSkill.get().getCurrentPoint());
                    updatedSkill.get().setMaxPoint(maxPoint.get());
                }

                Skill skill = skillRepository.save(updatedSkill.get());
                if (isDone)  {
                    skill = skillService.doneProcess(skill);
                }

                result = gson.toJsonTree(skill).getAsJsonObject();
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

}
