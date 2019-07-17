package com.cherkashyn.vitalii.indirector.workers.service.impl.phonedirty;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneDirty;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phonedirty.PhoneDirtyRepository;

@Service
public class PhoneDirtyRepositoryJdbc extends RepositoryJdbc<PhoneDirty> implements PhoneDirtyRepository{


}
