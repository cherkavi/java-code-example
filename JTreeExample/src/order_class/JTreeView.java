package order_class;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.sql.Connection;

import javax.swing.JTree;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class JTreeView extends JTree implements KeyListener{
	private final static long serialVersionUID=1L;
	private final static String rootElementName="Price";
	private Model model;
	
	/** View wich display data from Model */
	public JTreeView(Model model,Connection connection){
		this.model=model;
		try{
			this.model.load(connection);
		}catch(Exception ex){
			System.err.println("JTreeView  Exception: "+ex.getMessage());
		}
		this.addKeyListener(this);
	}
	
	public void updateModel(){
		DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode(rootElementName);
		int modelSize=this.model.getSize();
		int sectionKod=(-1);
		DefaultMutableTreeNode sectionNode=null;
		for(int counter=0;counter<modelSize;counter++){
			Element currentElement=this.model.getElement(counter);
			if(sectionKod!=currentElement.getKodSection().intValue()){
				// create new Section
				sectionNode=new DefaultMutableTreeNode(currentElement.getNameSection());
				rootNode.add(sectionNode);
				sectionKod=currentElement.getKodSection().intValue();
			}
			// add element to current section
			sectionNode.add(new DefaultMutableTreeNode(currentElement.getNameClass()));
		}
		DefaultTreeModel defaultTreeModel=new DefaultTreeModel(rootNode);
		this.setModel(defaultTreeModel);
		this.setRootVisible(false);
	}
	
	/** передвинуть выделенную €чейку вверх по дереву - вниз по List */
	public void selectionMoveUp(){
		TreePath treePath=this.getSelectionModel().getSelectionPath();
		if(treePath.getPathCount()==2){
			//System.out.println("SECTION");
			String sectionName=treePath.getPathComponent(1).toString();
			this.model.moveUpSection(new Element(null, sectionName));
			this.updateModel();
			this.expandTreeByCaption(sectionName);
		}
		if(treePath.getPathCount()==3){
			//System.out.println("MoveUp");
			String className=treePath.getPathComponent(2).toString();
			String sectionName=treePath.getPathComponent(1).toString();
			this.model.moveUpElement(this.model.getIndex(new Element(className, sectionName)));
			this.updateModel();
			this.expandTreeByCaption(sectionName, className);
					// Not work code below
			//System.out.println("Row from path: "+this.getRowForPath(new TreePath( ((TreeNode)this.getModel().getRoot()).getChildAt(2)  )));
			//System.out.println("Row from path: "+this.getRowForPath(new TreePath(this.getTreePathFromCaption(sectionName, className))));
			//this.expandPath(new TreePath(this.getTreePathFromCaption(sectionName, className)));
			//this.expandPath(new TreePath(new Object[]{rootElementName,sectionName, className}));
			//this.expandRow(3);
			//this.expandPath(new TreePath(this.getModel().getRoot()));
			//this.expandPath(new TreePath(new Object[]{new DefaultMutableTreeNode(rootElement),
			//  										  new DefaultMutableTreeNode(sectionName)}));
		}
	}
	
	/** полностью перечитать данные из таблицы  */
	public void resetDataIntoDataBase(Connection connection){
		this.model.resetDataIntoDataBase(connection);
	}
	
	/** передвинуть выделенную €чейку вниз по дереву - вверх по List */
	public void selectionMoveDown(){
		TreePath treePath=this.getSelectionModel().getSelectionPath();
		if(treePath.getPathCount()==2){
			//System.out.println("SECTION");
			String sectionName=treePath.getPathComponent(1).toString();
			this.model.moveDownSection(new Element(null, sectionName));
			this.updateModel();
			this.expandTreeByCaption(sectionName);
		}
		if(treePath.getPathCount()==3){
			//System.out.println("MoveUp");
			String className=treePath.getPathComponent(2).toString();
			String sectionName=treePath.getPathComponent(1).toString();
			this.model.moveDownElement(this.model.getIndex(new Element(className, sectionName)));
			this.updateModel();
			this.expandTreeByCaption(sectionName, className);
			//this.expandPath(new TreePath(new Object[]{new DefaultMutableTreeNode(rootElement),
			//											new DefaultMutableTreeNode(sectionName)}));
		}
	}
	
	private void expandTreeByCaption(String ... caption){
		try{
			TreePath path=null;
			try_again:
			for(int counter=0;counter<caption.length;counter++){
				int position=0;
				while( (path=this.getNextMatch(caption[counter],position,Position.Bias.Forward))!=null){
					//System.out.println("LastPathComponent:"+path.getLastPathComponent().toString());
					if(path.getLastPathComponent().toString().equals(caption[counter])){// дл€ точного, а не частичного совпадени€ 
						position=this.getRowForPath(path);
						//System.out.println("next Match:"+position);
						this.expandPath(path);
						this.setSelectionInterval(position, position);
						this.makeVisible(path);
						position++;
						continue try_again;
					}
					position++;
				}
			}
			//System.out.println("Find Match End");
		}catch(IllegalArgumentException ex){
			System.err.println("Find Exception: "+ex.getMessage());
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.isShiftDown()){
			if(event.getKeyCode()==KeyEvent.VK_UP){
				this.selectionMoveUp();
				event.consume();
			}
			if(event.getKeyCode()==KeyEvent.VK_DOWN){
				this.selectionMoveDown();
				event.consume();
			}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
/*	private void expandTreeByCaption(String ... caption){
		ArrayList<TreeNode> returnValue=new ArrayList<TreeNode>();
		// поиск заданных элементов, начина€ от корневого элемента
		
		returnValue.add((TreeNode)this.getModel().getRoot());
		boolean flagContinue=false;
		int position=(-1);
		for(int counter=0;counter<caption.length;counter++){
			flagContinue=false;
			for(int index=0;index<returnValue.get(counter).getChildCount();index++){
				position++;
				TreeNode currentNode=returnValue.get(counter);
				//System.out.println("CurrentNode: "+currentNode.toString());
				TreeNode currentChildNode=currentNode.getChildAt(index);
				//System.out.println("CurrentChildNode: "+currentChildNode.toString());
				String currentCaption=caption[counter];
				//System.out.println("CurrentCaption: "+currentCaption.toString());
				if(currentChildNode.toString().equals(currentCaption)){
					returnValue.add(returnValue.get(counter).getChildAt(index));
					flagContinue=true;
					//this.expandRow(position);
					break;
				}
			}
			if(flagContinue==false){
				break;
			}
		}
		//TreePath path=new TreePath(returnValue.get(returnValue.size()-1));
		//Collections.reverse(returnValue);
		//TreePath findPath=new TreePath(returnValue.toArray(new DefaultMutableTreeNode[]{}));
		// выделение заданного TreePath пути, состо€щего из TreeNode
		TreeNode[] findNodes=returnValue.toArray(new DefaultMutableTreeNode[]{});
		try_again:
		for(int row=0;row<this.getRowCount();row++){
			try{
				if(this.compareTreeNodes(findNodes, this.getPathForRow(row).getPath())){
					this.expandRow(row);
					System.out.println("Match:"+row);
					//this.printArray(this.getPathForRow(row).getPath());
					continue try_again;
				}
			}catch(MatchException me){
				System.out.println("Match finded: ");
				this.printArray(findNodes);
				this.printArray(this.getPathForRow(row).getPath());
				this.expandRow(row);
				this.setSelectionInterval(row,row);
				break;
			}
		}
		
		TreePath path=new TreePath(returnValue.toArray(new TreeNode[]{}));
		this.printArray(returnValue.toArray(new TreeNode[]{}));
		System.out.println("Row: "+this.getRowForPath(path));
		this.expandPath(path);
		this.setSelectionInterval(position, position);
		System.out.println("ReturnValue: "+returnValue.get(returnValue.size()-1));
		
	}
*/
	
	/* получить сравнение двух массивов элементов
	 * @param source - изначальный более длинный массив элементов 
	 * @param destination - изначально более короткий массив элементов 
	 * @return true - если destination полностью соответствует началу в source
	private boolean compareTreeNodes(TreeNode[] source, Object[] destination) throws MatchException{
		boolean returnValue=true;
		for(int counter=0;counter<destination.length;counter++){
			if(!destination[counter].equals(source[counter])){
				returnValue=false;
				break;
			}
		}
		if((returnValue==true)&&(source.length==destination.length)){
			throw new MatchException();
		}
		return returnValue;
	}*/

/*	private void printArray(Object[] array){
		if(array!=null){
			for(int counter=0;counter<array.length;counter++){
				System.out.println(counter+" : "+array[counter]+"   Class:"+array[counter].getClass().toString());
			}
		}else{
			System.out.println("Array is null ");
		}
	}
*/	
	/** получить цепочку элементов на основании пути */
/*	private TreeNode[] getTreePathFromCaption(String ... caption){
		ArrayList<TreeNode> returnValue=new ArrayList<TreeNode>();
		returnValue.add((TreeNode)this.getModel().getRoot());
		boolean flagContinue=false;
		for(int counter=0;counter<caption.length;counter++){
			flagContinue=false;
			for(int index=0;index<returnValue.get(0).getChildCount();index++){
				TreeNode currentNode=returnValue.get(counter);
				//System.out.println("CurrentNode: "+currentNode.toString());
				TreeNode currentChildNode=currentNode.getChildAt(index);
				//System.out.println("CurrentChildNode: "+currentChildNode.toString());
				String currentCaption=caption[counter];
				//System.out.println("CurrentCaption: "+currentCaption.toString());
				if(currentChildNode.toString().equals(currentCaption)){
					returnValue.add(returnValue.get(counter).getChildAt(index));
					flagContinue=true;
					break;
				}
			}
			if(flagContinue==false){
				break;
			}
		}
		System.out.println("ReturnValue: "+returnValue.get(returnValue.size()-1));
		return returnValue.toArray(new TreeNode[]{});
	}
*/	
}


class MatchException extends Exception{
	private final static long serialVersionUID=1L;
	public MatchException(){
		super("match");
	}
}