package com.cherkashyn.vitalii.indirector.workers.service.interf.worker;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.Worker;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface WorkerFinder extends Finder<Worker>{
	/**
	 * find last 10 workers 
	 * @param visibleWorkersCount
	 * @return
	 */
	List<Worker> findLast(int visibleWorkersCount) throws ServiceException;

	/**
	 * just refresh data from DB
	 * @param worker
	 * @throws ServiceException 
	 */
	void refresh(Worker worker) throws ServiceException;

	/**
	 * global search ( used different fields )
	 * @param searchValue
	 * @param visibleWorkersCount
	 * @return
	 * @throws ServiceException 
	 */
	List<Worker> find(String searchValue, int visibleWorkersCount) throws ServiceException;
}
