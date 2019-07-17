package gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** Cell renderer for JTable*/
public class ImageTableRenderer implements TableCellRenderer{
	private JPanel field_empty_result=new JPanel();
	private Logger field_logger;
	/** Selected color */
	private Color field_color_selected=Color.RED;
	/** Unselected color*/
	private Color field_color_unselected=Color.DARK_GRAY;
	/** Border for selection component */
	private LineBorder field_border_selected;
	/** Border for unselected component*/
	private LineBorder field_border_unselected;
	
	public ImageTableRenderer(){
		this.field_logger=Logger.getLogger(this.getClass());
		this.field_logger.setLevel(Level.DEBUG);
		this.field_border_selected=new javax.swing.border.LineBorder(field_color_selected);
		this.field_border_unselected=new javax.swing.border.LineBorder(field_color_unselected);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, 
												   Object value,
												   boolean isSelected, 
												   boolean hasFocus, 
												   int row, 
												   int column) {
		if(value instanceof RowElement){
			this.field_logger.debug("is RowElement");
			try{
				RowElement field_current_row_element=((RowElement)value);
				if(hasFocus){
					JPanel field_view=field_current_row_element.getPanel();
					field_view.setBorder(this.field_border_selected);
					return field_view;
				}else{
					JPanel field_view=field_current_row_element.getPanelThumb();
					field_view.setBorder(this.field_border_unselected);
					return field_view;
				}
			}catch(Exception ex){
				field_logger.error("Renderer is not RowElement");
				return field_empty_result;
			}
		}else{
			if(value instanceof String){
				this.field_logger.debug("is String");
				return field_empty_result;
			}else{
				return field_empty_result;
			}
		}
	}
	
}