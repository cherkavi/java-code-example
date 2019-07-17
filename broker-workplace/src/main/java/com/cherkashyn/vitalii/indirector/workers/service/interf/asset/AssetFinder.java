package com.cherkashyn.vitalii.indirector.workers.service.interf.asset;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.Asset;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface AssetFinder extends Finder<Asset>{

	List<Asset> findAll() throws ServiceException;

	Asset findById(Integer id) throws ServiceException;
}
