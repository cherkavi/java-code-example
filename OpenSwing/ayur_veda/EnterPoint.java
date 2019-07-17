package ayur_veda;

import java.util.*;
import org.openswing.swing.mdi.client.*;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.internationalization.java.EnglishOnlyResourceFactory;
import org.openswing.swing.util.client.*;
import org.openswing.swing.permissions.client.*;
import javax.swing.*;
import org.openswing.swing.internationalization.java.Language;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import org.openswing.swing.mdi.java.ApplicationFunction;
import org.openswing.swing.client.SplashScreen;
import org.openswing.swing.tree.java.OpenSwingTreeNode;

public class EnterPoint  implements MDIController,LoginController {

	  private UserClientFacade clientFacade = new UserClientFacade();

	  public EnterPoint(String[] argv) {
	    new ClientSettings(new RussianOnlyResourceFactory(),null);

//	    Enumeration k = System.getProperties().keys();
//	    while(k.hasMoreElements()) {
//	      String kk = k.nextElement().toString();
//	      System.out.println(kk+"="+System.getProperty(kk));
//	    }
//	    System.out.println(ClientSettings.LOOK_AND_FEEL_CLASS_NAME);


	    //ClientSettings.BACKGROUND = "background3.jpg";
	    //ClientSettings.TREE_BACK = "treeback2.jpg";
	    ClientSettings.AUTO_EXPAND_TREE_MENU = true;
	    ClientSettings.MIN_MENU_WIDTH = 300;
	    ClientSettings.DIVIDER_WIDTH = 0;
	    ClientSettings.SHOW_FIND_FUNCTION_LABEL=true;
	    ClientSettings.SHOW_FUNCTIONS_LABEL=true;
	    ClientSettings.SHOW_TREE_MENU_ROOT=false;
	    ClientSettings.SHOW_SCROLLBARS_IN_MDI=true;
	    
//	    ClientSettings.AUTO_EXPAND_SUBTREE_MENU = "Folder3";

	    if(argv.length==1)
	      ClientSettings.LOOK_AND_FEEL_CLASS_NAME = argv[0];

//	    ClientSettings.MAX_MENU_WIDTH = 300;
//	    ClientSettings.MENU_WIDTH = 300;

	    MDIFrame mdi = new MDIFrame(this);
	    mdi.setLocked(false);
	  }


	  /**
	   * Method called after MDI creation.
	   */
	  public void afterMDIcreation(MDIFrame frame) {
/*	    frame.addButtonToToolBar("new.gif","New Record");
	    frame.addButtonToToolBar("edit.gif","Edit Record");
	    frame.addButtonToToolBar("reload.gif","Undo/Refresh Record");
	    frame.addButtonToToolBar("save.gif","Save Record");
	    frame.addButtonToToolBar("del.gif","Delete Record");*/
	    frame.setBorderPainterOnToolBar(false);
	    frame.setFloatableOnToolBar(false);
	    frame.setRolloverOnToolBar(false);
	    //new SplashScreen(frame,"about.jpg",getMDIFrameTitle(),5);
	    // frame.getMenuItem("F2").setMnemonic('2');
	  }


	  /**
	   * @see JFrame getExtendedState method
	   */
	  public int getExtendedState() {
	    return JFrame.NORMAL;// MAXIMIZED_BOTH;
	  }


	  /**
	   * @return client facade, invoked by the MDI Frame tree/menu
	   */
	  public ClientFacade getClientFacade() {
	    return clientFacade;
	  }


	  /**
	   * Method used to destroy application.
	   */
	  public void stopApplication() {
	    System.exit(0);
	  }


	  /**
	   * Defines if application functions must be viewed inside a tree panel of MDI Frame.
	   * @return <code>true</code> if application functions must be viewed inside a tree panel of MDI Frame, <code>false</code> no tree is viewed
	   */
	  public boolean viewFunctionsInTreePanel() {
	    return true;
	  }


	  /**
	   * Defines if application functions must be viewed in the menubar of MDI Frame.
	   * @return <code>true</code> if application functions must be viewed in the menubar of MDI Frame, <code>false</code> otherwise
	   */
	  public boolean viewFunctionsInMenuBar() {
	    return true;
	  }


	  /**
	   * @return <code>true</code> if the MDI frame must show a login menu in the menubar, <code>false</code> no login menu item will be added
	   */
	  public boolean viewLoginInMenuBar() {
	    return false;
	  }


	  /**
	   * @return application title
	   */
	  public String getMDIFrameTitle() {
	    return "Учёт пациентов";
	  }


	  /**
	   * @return text to view in the about dialog window
	   */
	  public String getAboutText() {
	    return
	    	"<html> <center>Developer:</center><br><center>Черкашин Виталий </center><br> +38 097 920 46 71</html>";
	  }


	  /**
	   * @return image name to view in the about dialog window
	   */
	  public String getAboutImage() {
	    return null;
	  }

	  /**
	   * @param parentFrame parent frame
	   * @return a dialog window to logon the application; the method can return null if viewLoginInMenuBar returns false
	   */
	  public JDialog viewLoginDialog(JFrame parentFrame) {
	    return new LoginDialog(null, false, this);
	  }



	  /**
	   * @return maximum number of failed login
	   */
	  public int getMaxAttempts() {
	    return 5;
	  }


	  /**
	   * Method called by MDI Frame to authenticate the user.
	   * @param loginInfo login information, like username, password, ...
	   * @return <code>true</code> if user is correcly authenticated, <code>false</code> otherwise
	   */
	  public boolean authenticateUser(Map loginInfo) throws Exception {
	    return true;
	  }



	  public static void main(String[] argv) {
	    new EnterPoint(argv);
	  }


	  /**
	   * Method called by LoginDialog to notify the sucessful login.
	   * @param loginInfo login information, like username, password, ...
	   */
	  public void loginSuccessful(Map loginInfo) {
		  //MDIFrame mdi=new MDIFrame(this);
	  }


	  /**
	   * @return <code>true</code> if the MDI frame must show a change language menu in the menubar, <code>false</code> no change language menu item will be added
	   */
	  public boolean viewChangeLanguageInMenuBar() {
	    return false;
	  }


	  /**
	   * @return list of languages supported by the application
	   */
	  public ArrayList<Language> getLanguages() {
	    ArrayList<Language> list = new ArrayList<Language>();
	    list.add(new Language("RU", "Russian"));
	    list.add(new Language("EN","English"));
	    return list;
	  }


	  /**
	   * @return application functions (ApplicationFunction objects), organized as a tree
	   */
	  public DefaultTreeModel getApplicationFunctions() {
	    //ApplicationFunction root = new ApplicationFunction("Function0","F0",null,"getF0");
		  ApplicationFunction root = new ApplicationFunction("Function",null);
//	    DefaultMutableTreeNode root = new OpenSwingTreeNode();
	    DefaultTreeModel model = new DefaultTreeModel(root);
	    ApplicationFunction n1 = new ApplicationFunction("Анкеты",null);

	    ApplicationFunction n11 = new ApplicationFunction("Просмотр/Редактирование всех анкет","view_questionary",null,"getViewQuestionary");
	    ApplicationFunction n12 = new ApplicationFunction("Создать анкету","add_questionary",null,"getAddQuestionary");
	    n1.add(n11);
	    n1.add(n12);
	    root.add(n1);
	    return model;
	  }


	  /**
	   * @return <code>true</code> if the MDI frame must show a panel in the bottom, containing last opened window icons, <code>false</code> no panel is showed
	   */
	  public boolean viewOpenedWindowIcons() {
	    return true;
	  }

}
