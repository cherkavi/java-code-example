package com.cherkashyn.vitaliy.thrift.server;

import org.apache.thrift.TException;

import com.cherkashyn.vitaliy.thrift.exchange.Address;
import com.cherkashyn.vitaliy.thrift.exchange.EmployeeInfo;
import com.cherkashyn.vitaliy.thrift.exchange.Worker;

public class FunctionImpl implements EmployeeInfo.Iface{

	@Override
	public Worker getWorkerById(int id) throws TException {
		Worker worker=new Worker();
		worker.id=1;
		worker.name_first="Pit";
		worker.name_second="Bronson";
		worker.phone="555 12 34";
			Address address=new Address();
			address.post_index=3212;
			address.city="L'vov";
			address.street="Mitskevicha";
			address.b_number=5;
			address.flat_number=12;
		worker.home_address=address;
		return worker;
	}

	
}
