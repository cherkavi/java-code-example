package com.mock;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.easymock.EasyMock;

import com.example.IStringIntegerSetter;
import com.utility.BeanManager;

/** 
 * пример реализации модели для интерфейса <br />
 * то есть это просто реализация интерфейса средствами easymock <br />
 * createMock - все методы интерфейса будут реализованы <br />
 * createStrictMock - будут реализованы только ожидаемые методы интерфейса ( для кого было введено правило EasyMock.expect ) <br />
 */
public class ExampleInterfaceRealisation {

	public static void main(String[] args){
		System.out.println("begin");System.out.println();
		
		System.out.println("------------");

		exampleReturnValue();
		System.out.println("------------");
		
		// ------------------------------------------------------
		exampleStub();
		System.out.println("------------");

		// ------------------------------------------------------
		try{
			exampleException();
		}catch(Exception ex){
			System.out.println("Throw Exception: "+ex.getMessage());
		}
		System.out.println("------------");
		
		
		System.out.println();
		System.out.println("-end-");
	}
	
	/**
	 * пример Exception
	 */
	private static void exampleException(){
		IStringIntegerSetter mock=EasyMock.createMock(IStringIntegerSetter.class);
		EasyMock.expect(mock.getString()).andThrow(new RuntimeException("this is added RuntimeException"));
		EasyMock.replay(mock);
		System.out.println("This method throws the RuntimeException: "+ mock.getString());
	}
	
	
	/** 
	 * пример перенаправления вызовов
	 */
	private static void exampleStub(){
		// создать модель
		IStringIntegerSetter stubMock=EasyMock.createMock(IStringIntegerSetter.class);
		// для ожидаемого вызова метода
//		org.easymock.EasyMock.expect(stubMock.getParameter("param1"))
//			// установить делегат для перенаправления
//			.andStubDelegateTo(new SimpleStub());
		
		// для ожидаемого вызова параметра установить особый вид параметров в виде Regular expression
		EasyMock.expect(stubMock.getParameter(EasyMock.matches("[A-Z]*[a-z]*[0-9]*")))
		.andStubDelegateTo(new SimpleStub());
		// активизировать объект
		EasyMock.replay(stubMock);
		EasyMock.verify(stubMock);
		System.out.println(stubMock.getParameter("param1"));
		System.out.println(stubMock.getParameter("P1"));
		System.out.println(stubMock.getParameter("Parameter2"));
	}
	
	
	
	/**
	 * пример добавления возвращаемого значения для модели 
	 */
	private static void exampleReturnValue(){
		// получить объект-модель
		IStringIntegerSetter mock=org.easymock.EasyMock.createMock(IStringIntegerSetter.class);
		
		System.out.println("Object:"+mock);
		BeanManager.printParentObject(mock);
		BeanManager.printObjectInterfaces(mock);
		
		// для указанного объекта модели установить для возвращаемого метода
		org.easymock.EasyMock.expect(mock.getString())
			// определенное значение 
			.andReturn("this is temp value for ")
			.times(1);
			
		org.easymock.EasyMock.expect(mock.getString())
		// определенное значение 
		.andReturn("this is another value")
		.times(2);
		
		org.easymock.EasyMock.expect(mock.getString())
		// определенное значение 
		.andReturn("this is LAST value")
		.anyTimes();
		
		// or
//		EasyMock.expect(mock.getString())
//			.andReturn("this is temp value").times(1)
//			.andReturn("this is another value").times(2)
//			.andReturn("this is LAST value").anyTimes();
		
		// activate the mock
		org.easymock.EasyMock.replay(mock);
		
		// get value from mock object
		System.out.println("Return: "+mock.getString());
		System.out.println("Return: "+mock.getString());
		System.out.println("Return: "+mock.getString());
		System.out.println("Return: "+mock.getString());
	}
	
}

class SimpleStub implements IStringIntegerSetter{

	private int intValue;
	private String stringValue;
	
	@Override
	public int getInteger() {
		return intValue;
	}

	@Override
	public String getParameter(String parameter) {
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date())+" "+parameter;
	}

	@Override
	public String getString() {
		return stringValue;
	}

	@Override
	public void setInteger(int value) {
		this.intValue=value;
	}

	@Override
	public void setString(String value) {
		this.stringValue=value;
	}
	
}

