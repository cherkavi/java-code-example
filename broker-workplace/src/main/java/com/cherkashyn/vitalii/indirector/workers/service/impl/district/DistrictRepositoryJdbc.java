package com.cherkashyn.vitalii.indirector.workers.service.impl.district;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.District;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.district.DistrictRepository;

@Service
public class DistrictRepositoryJdbc extends RepositoryJdbc<District> implements DistrictRepository{


}
