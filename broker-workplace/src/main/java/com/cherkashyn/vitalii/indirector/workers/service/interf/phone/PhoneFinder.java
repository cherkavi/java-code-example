package com.cherkashyn.vitalii.indirector.workers.service.interf.phone;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.Phone;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface PhoneFinder extends Finder<Phone>{

	List<Phone> findLike(String replaceAll) throws ServiceException;
}
