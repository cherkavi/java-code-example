package com.cherkashyn.vitalii.indirector.workers.service.impl.phoneattemptresult;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttemptResult;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattemptresult.PhoneAttemptResultRepository;

@Service
public class PhoneAttemptResultRepositoryJdbc extends RepositoryJdbc<PhoneAttemptResult> implements PhoneAttemptResultRepository{


}
