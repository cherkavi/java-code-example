package ayur_veda;

import org.openswing.swing.mdi.client.*;

import ayur_veda.questionary.AddQuestionary;

public class UserClientFacade implements ClientFacade {

		public void getViewQuestionary() {
		    InternalFrame f = new InternalFrame();
		    f.setResizable(false);
		    f.setIconifiable(false);
		    f.setMaximizable(false);
		    f.setSize(300,200);
		    f.setTitle("Просмотр/Редактирование анкет");
		    MDIFrame.add(f,false);
		  }


		  public void getAddQuestionary() {
		    MDIFrame.add(new AddQuestionary(),false);
		  }



}
