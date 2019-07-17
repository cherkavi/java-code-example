package com.cherkashyn.vitalii.accounting.service.finder;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.accounting.domain.AssortmentType;

@Service
public class TypeFinder extends FinderJdbc<AssortmentType>{
	
	@Override
	protected Class<AssortmentType> getEntityClass() {
		return AssortmentType.class;
	}

}
