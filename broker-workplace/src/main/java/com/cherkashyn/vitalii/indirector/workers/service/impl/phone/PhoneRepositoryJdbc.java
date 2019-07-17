package com.cherkashyn.vitalii.indirector.workers.service.impl.phone;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Phone;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phone.PhoneRepository;

@Service
public class PhoneRepositoryJdbc extends RepositoryJdbc<Phone> implements PhoneRepository{


}
