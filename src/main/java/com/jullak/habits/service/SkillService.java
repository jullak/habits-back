package com.jullak.habits.service;

import com.jullak.habits.model.Skill;

public interface SkillService {
    boolean isDone(long skillId, long maxPoint, long currentPoint) throws Exception;
    Skill doneProcess(Skill skill) throws Exception;
    Skill addPoint(long skillId, long point) throws Exception;
}
