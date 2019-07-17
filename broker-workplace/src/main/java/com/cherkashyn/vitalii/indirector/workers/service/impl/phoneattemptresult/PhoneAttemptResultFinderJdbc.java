package com.cherkashyn.vitalii.indirector.workers.service.impl.phoneattemptresult;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttemptResult;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattemptresult.PhoneAttemptResultFinder;

@Service
public class PhoneAttemptResultFinderJdbc extends FinderJdbc<PhoneAttemptResult> implements PhoneAttemptResultFinder{

	@Override
	protected Class<PhoneAttemptResult> getEntityClass() {
		return PhoneAttemptResult.class;
	}


}
