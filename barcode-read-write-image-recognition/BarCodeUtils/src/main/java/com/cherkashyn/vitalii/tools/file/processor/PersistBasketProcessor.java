package com.cherkashyn.vitalii.tools.file.processor;

import java.io.File;

import com.cherkashyn.vitalii.tools.persist.PersistException;
import com.cherkashyn.vitalii.tools.persist.PersistService;

public class PersistBasketProcessor extends FolderProcessor{
	private final PersistService persistService;
	
	public PersistBasketProcessor(PersistService service){
		this.persistService=service;
	}
	@Override
	public void process(File directory, Exchange exchange)
			throws ProcessException {
		Long parentId=createParentRecord(exchange.getParentId());
		for(Basket eachBasket:exchange.getBaskets()){
			createBasketRecord(eachBasket, parentId);
		}
	}

	private void createBasketRecord(Basket eachBasket, Long parentId) throws ProcessException{
		try {
			this.persistService.createBasketRecord(eachBasket, parentId);
		} catch (PersistException e) {
			throw new ProcessException(e);
		}
	}

	private Long createParentRecord(String parentId) throws ProcessException {
		try {
			return this.persistService.createParentRecord(parentId);
		} catch (PersistException e) {
			throw new ProcessException(e);
		}
	}

}
