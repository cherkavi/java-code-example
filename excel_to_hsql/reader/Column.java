package excel_to_hsql.reader;

public class Column {
	private String columnName;
	private int columnIndex;
	
	public Column(String columnName, int columnIndex) {
		super();
		this.columnName = columnName;
		this.columnIndex = columnIndex;
	}

	public String getColumnName() {
		return columnName;
	}
	public int getColumnIndex() {
		return columnIndex;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columnIndex;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (columnIndex != other.columnIndex)
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}

	
}
