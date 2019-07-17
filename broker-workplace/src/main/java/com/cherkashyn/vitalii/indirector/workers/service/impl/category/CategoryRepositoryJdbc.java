package com.cherkashyn.vitalii.indirector.workers.service.impl.category;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Category;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.category.CategoryRepository;

@Service
public class CategoryRepositoryJdbc extends RepositoryJdbc<Category> implements CategoryRepository{


}
