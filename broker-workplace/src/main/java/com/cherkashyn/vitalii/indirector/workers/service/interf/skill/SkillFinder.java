package com.cherkashyn.vitalii.indirector.workers.service.interf.skill;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.Category;
import com.cherkashyn.vitalii.indirector.workers.domain.Skill;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface SkillFinder extends Finder<Skill>{
	/**
	 * find all by certain category
	 * @param category
	 * @return
	 * @throws ServiceException 
	 */
	List<Skill> findAll(Category category) throws ServiceException;
}
