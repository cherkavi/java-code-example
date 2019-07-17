package com.cherkashyn.vitalii.indirector.workers.service.impl.hour;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Worker2hour;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.hour.HourFinder;

@Service
public class HourFinderJdbc extends FinderJdbc<Worker2hour> implements HourFinder{

	@Override
	protected Class<Worker2hour> getEntityClass() {
		return Worker2hour.class;
	}


}
