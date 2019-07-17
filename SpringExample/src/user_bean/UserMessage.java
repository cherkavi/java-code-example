package user_bean;

public class UserMessage {
	private String message;
	
	public UserMessage(){
		System.out.println("User create Message EMPTY");
		message="";
	};
	
	public UserMessage(String message){
		System.out.println("User create Message:"+message);
		this.message=message;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
