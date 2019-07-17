package com.cherkashyn.vitalii.indirector.workers.service.impl.asset;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Asset;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.asset.AssetFinder;

@Service
public class AssetFinderJdbc extends FinderJdbc<Asset> implements AssetFinder{

	@Override
	protected Class<Asset> getEntityClass() {
		return Asset.class;
	}


}
