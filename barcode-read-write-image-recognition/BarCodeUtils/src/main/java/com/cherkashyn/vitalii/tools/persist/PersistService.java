package com.cherkashyn.vitalii.tools.persist;

import com.cherkashyn.vitalii.tools.file.processor.FolderProcessor.Basket;

public interface PersistService {

	void createBasketRecord(Basket eachBasket, Long parentId) throws PersistException;

	Long createParentRecord(String parentId) throws PersistException;

}
