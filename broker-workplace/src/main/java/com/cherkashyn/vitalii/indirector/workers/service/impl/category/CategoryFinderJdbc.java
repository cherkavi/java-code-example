package com.cherkashyn.vitalii.indirector.workers.service.impl.category;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Category;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.category.CategoryFinder;

@Service
public class CategoryFinderJdbc extends FinderJdbc<Category> implements CategoryFinder{

	@Override
	protected Class<Category> getEntityClass() {
		return Category.class;
	}


}
