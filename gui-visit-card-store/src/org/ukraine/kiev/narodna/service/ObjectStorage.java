package org.ukraine.kiev.narodna.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.ukraine.kiev.narodna.domain.ListItemModel;

public class ObjectStorage extends Storage {

	private List<ListItemModel>	data	= null;
	private File file;

	public ObjectStorage(String pathToFile) {
		file = new File(pathToFile);
		if (file.exists()) {
			data = readData(file);
		} else {
			data = new ArrayList<ListItemModel>();
		}
	}

	@SuppressWarnings("unchecked")
	private List<ListItemModel> readData(File file) {
		ObjectInputStream inputStream=null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(file));
			return (List<ListItemModel>)inputStream .readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("can't read data from file: "
					+ file.getAbsolutePath());
			return new ArrayList<ListItemModel>();
		} catch (IOException ex) {
			System.err.println("can't read data from file: "
					+ file.getAbsolutePath());
			return new ArrayList<ListItemModel>();
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public List<ListItemModel> getAll() {
		return new ArrayList<ListItemModel>(this.data);
	}

	@Override
	public ListItemModel save(ListItemModel model) throws ServiceException {
		if(this.data.contains(model)){
			return this.data.get(this.data.indexOf(model));
		}else{
			model.setId(getMaxId()+1);
			this.data.add(model);
			this.saveToFile();
			return model;
		}
		
	}

	private void saveToFile() throws ServiceException{
		ObjectOutputStream output=null;
		try{
			output=new ObjectOutputStream(new FileOutputStream(this.file));
			output.writeObject(this.data);
		}catch(IOException ex){
			throw new ServiceException("can't save file: "+ex.getMessage());
		}finally{
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private int getMaxId() {
		int max=0;
		for(ListItemModel eachModel:this.data){
			if(eachModel.getId()>max){
				max=eachModel.getId();
			}
		}
		return max;
	}

}
