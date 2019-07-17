package com.cherkashyn.vitalii.indirector.workers.service.interf.district;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.District;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface DistrictFinder extends Finder<District>{

	List<District> findAll(District parent) throws ServiceException;

	District findById(Integer id) throws ServiceException;

	/** find lead only, which don't have child 
	 * @throws ServiceException */
	List<District> findAllLeaf() throws ServiceException;
}
