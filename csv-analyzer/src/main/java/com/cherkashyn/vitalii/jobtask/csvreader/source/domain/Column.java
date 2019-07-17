package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.DataSourceException;

/**
 * desription of the column - metadata for column
 * @author technik
 *
 */
public abstract class Column<T> {
	protected String idName;
	
	public Column(String columnUniqueId){
		this.idName=columnUniqueId;
	}
	/**
	 * parse data from string
	 * @param rawData
	 * @return
	 * @throws DataSourceException - when data in string is not applicable with expected format
	 */
	public abstract Field<T> parseData(String rawData) throws DataSourceException;
	
	/**
	 * find column by unique id
	 * @param columns
	 * @param id
	 * @return
	 */
	public static Column<?> findColumnById(Column<?>[] columns, String id){
		if(columns==null || columns.length==0){
			return null;
		}
		for(Column<?> eachColumn:columns){
			if(eachColumn.idName.equals(id)){
				return eachColumn;
			}
		}
		// was not found
		return null;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idName == null) ? 0 : idName.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column<?> other = (Column<?>) obj;
		if (idName == null) {
			if (other.idName != null)
				return false;
		} else if (!idName.equals(other.idName))
			return false;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Field<T> findByColumn(Column<T> targetColumn, Row row) {
		if(targetColumn==null){
			return null;
		}
		if(row==null){
			return null;
		}
		for(Field<?> eachField:row.getData()){
			if(targetColumn.equals(eachField.getColumn())){
				return (Field<T>)eachField;
			}
		}
		return null;
	}
	
	
}
