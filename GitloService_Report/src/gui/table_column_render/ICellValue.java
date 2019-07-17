package gui.table_column_render;
import java.sql.ResultSet;

/** интерфейс, который предоставляет возможность получения данных на основании ResultSet */
public interface ICellValue {
	/** получить очередное значение из ResultSet */
	public String getCellValue(ResultSet rs);
}
