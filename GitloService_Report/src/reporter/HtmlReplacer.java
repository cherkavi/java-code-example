package reporter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import reporter.replacer.ReplaceValue;

/** заменяет в странице HTML указанные тэги на переденные значения */
public class HtmlReplacer {
	private Page page;
	private Lexer lexer;
	private Parser parser;
	private StringBuffer result;
	
	/** заменяет в HTML странице указанные тэги на передаваемые значения 
	 * @param pathToHtml - путь к файлу HTML
	 * @param charset - кодировка, в которой находится HTML файл
	 * @param tagForReplace - тэги, которые должны быть заменены 
	 * @param values - значения, которые должны быть по порядку вставлены в предоставленные тэги
	 * */
	public HtmlReplacer(String pathToHtml, 
						String charset, 
						String tagForReplace, 
						ArrayList<ReplaceValue> values) throws UnsupportedEncodingException, FileNotFoundException, ParserException{
		page=new Page(new FileInputStream(pathToHtml),charset);
		lexer=new Lexer(page);
		parser=new Parser(lexer);
		
		// получить Visitor на основании тэгов для просмотра
		Visitor visitor=new Visitor(tagForReplace);
		visitor.clearList();
		// пройтись по всех тэгам с помощью Visitor
		parser.visitAllNodesWith(visitor);
		// получить координаты всех точек входа тэгов для замены в программу 
		ArrayList<Position> listOfBuffer=visitor.getListOfPosition();
		result=new StringBuffer();
		int lastPosition=0;
		// пройтись по всем точкам в HTML коде для замены переменных в них
		for(int counter=0;counter<listOfBuffer.size();counter++){
			page.getText(result,lastPosition,listOfBuffer.get(counter).getBegin());
			if(counter<values.size()){
				result.append(values.get(counter).getReplaceValue());
			}
			lastPosition=listOfBuffer.get(counter).getEnd();
		}
		page.getText(result,lastPosition,page.getText().length());
	}
	
	public void printResultToWriter(PrintStream writer){
		writer.print(result);
	}
	
	private String getHtmlHeader(String title){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		returnValue.append("<html>");
		returnValue.append("<head>");
		returnValue.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=Windows-1251\">");
		returnValue.append("<title>"+title+"</title>");
		returnValue.append("</head>");
		returnValue.append("<body>");
		returnValue.append("<TT>");
		return returnValue.toString();
	}
	
	private String getHtmlFooter(){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("</TT>");
		returnValue.append("</body>");
		returnValue.append("</html>");
		return returnValue.toString();
	}
	
	public void printResultToOutputStream(BufferedWriter writer,
										  boolean printHtmlHeader,
										  String headerTitle,
										  boolean printHtmlFooter) throws IOException{
		if(printHtmlHeader==true){
			writer.write(this.getHtmlHeader(headerTitle));
		}
		writer.write(result.toString());
		if(printHtmlFooter==true){
			writer.write(this.getHtmlFooter());
		}
	}

}

class Visitor extends NodeVisitor{
	private String markTag;
	private ArrayList<Position> listOfPosition=new ArrayList<Position>();
	/** найти все тэги с указанным именем */
	public Visitor(String markTag){
		this.markTag=markTag;
		
	}
	
	public void clearList(){
		this.listOfPosition.clear();
	}
	
	public ArrayList<Position> getListOfPosition(){
		return this.listOfPosition;
	}
	
	@Override
	public void visitTag(Tag tag) {
		if(tag.getTagName().equalsIgnoreCase(markTag)){
			this.listOfPosition.add(new Position(tag.getStartPosition(),tag.getEndPosition()));
		}
	}
}

class Position{
	private int begin;
	private int end;
	public Position(int begin, int end){
		this.begin=begin;
		this.end=end;
	}
	/**
	 * @return the begin
	 */
	public int getBegin() {
		return begin;
	}
	/**
	 * @param begin the begin to set
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}
	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}
	
}