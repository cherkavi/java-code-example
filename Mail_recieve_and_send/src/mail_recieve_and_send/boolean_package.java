/*
 * boolean_package.java
 *
 * Created on 29 лютого 2008, 14:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package mail_recieve_and_send;

/**
 *
 * @author Technik
 */
/*
 *package for boolean value
 */
class boolean_package{
	private boolean value;
	boolean_package(boolean value){
		this.value=value;
	}
	boolean getValue(){
		return this.value;
	}
	void setValue(boolean value){
		this.value=value;
	}
}
