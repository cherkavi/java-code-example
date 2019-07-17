import javax.imageio.ImageIO;
import javax.swing.table.AbstractTableModel;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageTableModel extends AbstractTableModel{
	private ArrayList<RowElement> field_data=new ArrayList<RowElement>();
	/** */
	private static final long serialVersionUID = 1L;
	
	public ImageTableModel(){
		
	}
	
	public void addElement(String ... path_to_image){
		String unique_identifier=this.getUniqueIdentifier();
		ImageIdentifier[] identifiers=new ImageIdentifier[path_to_image.length];
		for(int counter=0;counter<path_to_image.length;counter++){
			identifiers[counter]=new ImageIdentifier(path_to_image[counter]);
		}
		this.field_data.add(new RowElement(unique_identifier,identifiers));
		repaint_table();
	}
	
	public void deleteElement(String unique_identifier){
		int index_for_delete=this.getIndexByUniqueIdentifier(unique_identifier);
		if(index_for_delete>=0){
			this.field_data.remove(index_for_delete);
			repaint_table();
		}
	}

	/** repaint table for visible changing */
	private void repaint_table(){
		// TODO repaint Table
	}
	
	/**
	 * return index of RowElement into  ArrayList by unique_idetifier
	 * @param unique_identifier уникальный идентификатор 
	 * @return индекс иникального идентификатора
	 */
	private int getIndexByUniqueIdentifier(String unique_identifier){
		for(int counter=0;counter<field_data.size();counter++){
			if(field_data.get(counter).isUniqueIdentifierEquals(unique_identifier)){
				return counter;
			}
		}
		return -1;
	}
	
	/** генератор уникального идентификатора */
	private String getUniqueIdentifier(){
		// TODO create generator
		// check for repeat
		return "    ";
	}
	
	@Override
	public int getColumnCount() {
		return 0;
	}

	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return null;
	}

}

/** объект, который отвечает за получение изображени€ */
class ImageIdentifier{
	private String field_path_to_image;
	private Image field_image_full;
	private Image field_image_thumb;
	
	public ImageIdentifier(String path_to_image){
		this.field_path_to_image=path_to_image;
		field_image_full=this.getImageFromPath(path_to_image);
		field_image_thumb=this.getThumbFromImage(field_image_full);
	}
	
	public String getPathImage(){
		return this.field_path_to_image;
	}
	
	private Image getImageFromPath(String path_to_image){
		try{
			return ImageIO.read(new File(path_to_image));
		}catch(IOException ex){
			return null;
		}
	}
	
	private Image getThumbFromImage(Image full_image){
		//TODO create cut image
		return full_image;
	}
	
	
	public Image getImage(){
		return field_image_full;
	}
	
	public Image getThumbImage(){
		return field_image_thumb;
	}
}

/** элемент, который содержит изображение и уникальный идентификатор */
class RowElement{
	
	/** объект, содержащий уникальный идентификатор */
	private String field_unique_identifier;
	/** объект, содержащий изображени€ */
	private ImageIdentifier[] field_image_identifier;
	
	/** Ёлемент из строки таблицы
	 * @param unique_identifier - уникальный идентификатор
	 * @param identifier идентификаторы изображений 
	 * 
	 * */
	public RowElement(String unique_identifier, 
					  ImageIdentifier ... identifiers){
		this.field_unique_identifier=unique_identifier;
		this.field_image_identifier=identifiers;
	}
	
	public ImageIdentifier[] getIdentifier(){
		return this.field_image_identifier;
	}
	
	/** проверка на equals уникального идентификатора 
	 * @param value идентификатор дл€ проверки 
	 * @return true - если идентификаторы совпадают 
	 * */
	public boolean isUniqueIdentifierEquals(String value){
		return field_unique_identifier.equals(value);
	}
	
}