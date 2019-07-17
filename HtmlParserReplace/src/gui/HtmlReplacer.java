package gui;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

/** заменяет в странице HTML указанные тэги на переденные значения */
public class HtmlReplacer {
	private Page page;
	private Lexer lexer;
	private Parser parser;
	private StringBuffer result;
	
	public HtmlReplacer(String pathToHtml, String charset, String tagForReplace, ArrayList<String> values) throws UnsupportedEncodingException, FileNotFoundException, ParserException{
		page=new Page(new FileInputStream(pathToHtml),charset);
		lexer=new Lexer(page);
		parser=new Parser(lexer);
		
		Visitor visitor=new Visitor(tagForReplace);
		visitor.clearList();
		parser.visitAllNodesWith(visitor);
		
		ArrayList<Position> listOfBuffer=visitor.getListOfPosition();
		result=new StringBuffer();
		int lastPosition=0;
		for(int counter=0;counter<listOfBuffer.size();counter++){
			page.getText(result,lastPosition,listOfBuffer.get(counter).getBegin());
			if(counter<values.size()){
				result.append(values.get(counter));
			}
			lastPosition=listOfBuffer.get(counter).getEnd();
		}
		page.getText(result,lastPosition,page.getText().length());
	}
	
	public void printResultToWriter(PrintStream writer){
		writer.print(result);
	}
	
	public void printResultToOutputStream(OutputStream out) throws IOException{
		(new BufferedWriter(new OutputStreamWriter(out))).write(result.toString());
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