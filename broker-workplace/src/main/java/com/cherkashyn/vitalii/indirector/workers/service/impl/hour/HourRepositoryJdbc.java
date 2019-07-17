package com.cherkashyn.vitalii.indirector.workers.service.impl.hour;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Worker2hour;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.hour.HourRepository;

@Service
public class HourRepositoryJdbc extends RepositoryJdbc<Worker2hour> implements HourRepository{


}
