package com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattempt;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttempt;
import com.cherkashyn.vitalii.indirector.workers.domain.PhoneDirty;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface PhoneAttemptFinder extends Finder<PhoneAttempt>{

	List<PhoneAttempt> find(PhoneDirty phoneDirty) throws ServiceException;
}
