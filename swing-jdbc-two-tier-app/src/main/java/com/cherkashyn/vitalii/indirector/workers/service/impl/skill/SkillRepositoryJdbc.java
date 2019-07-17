package com.cherkashyn.vitalii.indirector.workers.service.impl.skill;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Skill;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.skill.SkillRepository;

@Service
public class SkillRepositoryJdbc extends RepositoryJdbc<Skill> implements SkillRepository{

}
