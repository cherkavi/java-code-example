package com.cherkashyn.vitalii.indirector.workers.service.impl.asset;

import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Asset;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.asset.AssetRepository;

@Service
public class AssetRepositoryJdbc extends RepositoryJdbc<Asset> implements AssetRepository{


}
