package com.test.dao;

import com.test.database.db_beans.Place;

public interface IPlaceDAO {
	
	public Place createPlace(String name);
	
	public void removePlace(Place place);
	
	public void renamePlace(Place place, String newPlaceName);
}
