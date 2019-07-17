package user_bean;

public class UserMessageDouble {
	private String message1;
	private String message2;
	
	public UserMessageDouble(){
		System.out.println("User create Message EMPTY");
		message1="";
		message2="";
	};
	
	public UserMessageDouble(String messageFirst, String messageSecond){
		System.out.println("User create DoubleMessage:"+messageFirst);
		System.out.println("User create DoubleMessage:"+messageSecond);
		this.message1=messageFirst;
		this.message2=messageSecond;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	
}
