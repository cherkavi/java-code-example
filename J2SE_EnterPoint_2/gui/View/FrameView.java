package gui.View;

import gui.Utility.JInternalFrameParent;

public class FrameView extends JInternalFrameParent{
	private static final long serialVersionUID=1L;
	
	public FrameView(JInternalFrameParent parent){
		super(parent,
		      parent.getCommonObject(),
		      "Viewer",
		      0,
		      0);
	}

	@Override
	protected void initComponents() {
		
	}
}
