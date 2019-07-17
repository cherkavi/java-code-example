package com.cherkashyn.vitalii.indirector.workers.service.interf.phonedirty;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneDirty;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface PhoneDirtyFinder extends Finder<PhoneDirty>{

	/**
	 * find phone ( SQL: where phone like '%5500101' 
	 * @param phone
	 * @return
	 * @throws ServiceException
	 */
	List<PhoneDirty> findLike(String phone) throws ServiceException;
}
