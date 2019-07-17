package com.cherkashyn.vitalii.indirector.workers.service.impl.phoneattempt;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttempt;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattempt.PhoneAttemptRepository;

@Service
public class PhoneAttemptRepositoryJdbc extends RepositoryJdbc<PhoneAttempt> implements PhoneAttemptRepository{


}
