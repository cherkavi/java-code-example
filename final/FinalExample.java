package com.vitaliy.test;

import java.text.MessageFormat;


public class FinalExample {
	
	public static void main(String[] args){
		System.out.println(MessageFormat.format("return from try >>> {0} <<< " , getResult(20)));
		// >>> 3.5 <<<
		System.out.println(MessageFormat.format("return after try-finally block >>> {0} <<< " , getResultAndChangeIt(20)));
		// >>> 3.5 <<<
		System.out.println(MessageFormat.format("direct return from finally <<< {0} >>>", returnResult()));
	}
	
	private static float returnResult(){
		try{
			return 0;
		}finally{
			return -5;
		}
	}

	private static float getResult(int argument){
		float returnValue=0;
		float magicNumber=70;
		try{
			returnValue=magicNumber/argument;
			//    V - place of execution finally block
			return change(returnValue);
		}finally{
			returnValue=-5;
		}
	}

	private static float getResultAndChangeIt(float argument){
		float returnValue=0;
		float magicNumber=70;
		try{
			if(argument<0){
				throw new RuntimeException();
			}
			returnValue=magicNumber/argument;
			//    V - place of execution finally block
			returnValue=change(returnValue);
		}finally{
			returnValue=-5;
		}
		return returnValue;
	}
	
	private static float change(float returnValue) {
		System.out.println("update, before return");
		return returnValue/1;
	}
	
}
