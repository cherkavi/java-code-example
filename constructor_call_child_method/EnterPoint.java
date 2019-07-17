package constructor_call_child_method;

public class EnterPoint {
	/*
	// вызывается анонимный блок родителя 
	Parent#anonym block
	// вызывается тело конструктора родителя
	Parent#constructor
	// в теле конструктора вызывается метод ( метод, определенный в данном объекте )
	call method main ( то есть из конструктора вызывается метод не локального объекта, а самый последний переопределенный )
	// переадресация вызова метода потомку, который создается - !!! Нарушение!!!
	Child#methodMain: 
	// анонимный блок создаваемого объекта 
	Child#anonym block
	// тело конструктора создаваемого объекта 
	Child#constructor
	*/
	public static void main(String[] args){
		new Child();
	}
}
