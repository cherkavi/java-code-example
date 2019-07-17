package com.test.database.dao;

import java.util.List;

import com.test.database.model.Actions;

public interface ActionsDao {
	public List<Actions> getAllActions();
	public Actions getAction(Integer id);
}
