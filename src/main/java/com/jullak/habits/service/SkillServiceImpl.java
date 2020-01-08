package com.jullak.habits.service;

import com.jullak.habits.model.Skill;
import com.jullak.habits.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    SkillRepository skillRepository;

    @Override
    public boolean isDone(long skillId, long maxPoint, long currentPoint) throws Exception {
        return currentPoint >= maxPoint;
    }

    @Override
    public Skill doneProcess(Skill skill) throws Exception {
        skill.setDone(true);
        skill.setFinished(Calendar.getInstance().getTime());
        return skillRepository.save(skill);
    }

    @Override
    public Skill addPoint(long skillId, long point) throws Exception {
        Skill updated = new Skill();
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (skill.isPresent()) {
            skill.get().setCurrentPoint(skill.get().getCurrentPoint() + point);
            updated = skillRepository.save(skill.get());
        }
        return updated;
    }
}
