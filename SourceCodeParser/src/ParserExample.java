import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.visitor.VoidVisitorAdapter;


public class ParserExample {
	public static void main(String[] args){
		System.out.println("begin");
		String pathToFile="C:\\eclipse_workspace\\AlbertStub\\src\\install\\position.java";
		CompilationUnit unit=getCompilationUnit(pathToFile);
		new VoidVisitorAdapter<Object>() {
			public void visit(japa.parser.ast.body.MethodDeclaration methodDeclaration, Object arg) {
				System.out.println("Method name:"+methodDeclaration.getName()+"  arguments: "+arg);
				List<Parameter> list=methodDeclaration.getParameters();
				for(Parameter each:list){
					System.out.println("    Parameter type:"+each.getType()+"  data:"+each.getData());
				}
			};
			public void visit(japa.parser.ast.body.ConstructorDeclaration constructorDeclaration, Object arg) {
				System.out.println("Constructor declaration:"+constructorDeclaration.getName()+" arguments:"+arg);
			};
			/*public void visit(japa.parser.ast.body.ClassOrInterfaceDeclaration declaration, Object arg) {
				System.out.println("ClassOrInterface declaration:"+declaration.getName()+"   is Interface:"+declaration.isInterface());
			};*/
		}.visit(unit, null);
		System.out.println("end");
	}
	
	private static CompilationUnit getCompilationUnit(String pathToFile) {
		 InputStream in = null;
         CompilationUnit cu = null;
         try
         {
        	 	in=new FileInputStream(new File(pathToFile));
                 cu = JavaParser.parse(in);
         }
         catch(FileNotFoundException ex){
        	 
         }
         catch(ParseException x)
         {
              // handle parse exceptions here.
         }
         finally
         {
        	 try{
        		 in.close();
        	 }catch(Exception ex){};
        	 
         }
         return cu;
		
	}
}
