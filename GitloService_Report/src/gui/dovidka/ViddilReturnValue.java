package gui.dovidka;

public class ViddilReturnValue {
	private Integer value=null;
	private boolean isSelected=false;

	public ViddilReturnValue(Integer value){
		this.value=value;
	}
	public ViddilReturnValue(){
	}
	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}
	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
