package example;

import java.io.IOException;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

/** автоматически пытается присоединиться при отсоединении */
public class SmackExample {
	// Notice the username is NOT smack.test@gmail.com
	private static String username = "technik7jobrobot@gmail.com";
	private static String password = "robottechnik7";
	
	public static void main( String[] args ) {
		System.out.println("Starting IM client");
        
        // gtalk requires this or your messages bounce back as errors
        ConnectionConfiguration connConfig = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
        XMPPConnection connection = new XMPPConnection(connConfig);
        try {
            connection.connect();
            System.out.println("Connected to " + connection.getHost());
        } catch (XMPPException ex) {
            //ex.printStackTrace();
            System.out.println("Failed to connect to " + connection.getHost());
            System.exit(1);
        }

        connection.addConnectionListener(new ConnectionListener(){
		@Override
		public void connectionClosed() {
			System.out.println("connectionClosed:");
		}

		@Override
		public void connectionClosedOnError(Exception arg0) {
			System.out.println("connectionClosedOnError:");
		}

		@Override
		public void reconnectingIn(int arg0) {
			System.out.println("reconnectingsIn:"+arg0);
		}

		@Override
		public void reconnectionFailed(Exception arg0) {
			System.out.println("reconnectionFailed:"+arg0.getMessage());
		}

		@Override
		public void reconnectionSuccessful() {
			System.out.println("reconnectionSuccessful:");
		}
    });
        
        try {
            connection.login(username, password);
            System.out.println("Logged in as " + connection.getUser());
            
            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);
            
        } catch (XMPPException ex) {
            //ex.printStackTrace();
            // XMPPConnection only remember the username if login is succesful
            // so we can''t use connection.getUser() unless we log in correctly
            System.out.println("Failed to log in as " + username);
            System.exit(1);
        }
        
        
        // ------------------------------------ this is direct connection 
        PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
        connection.addPacketListener(new MessageParrot(connection), filter);
/*        
        if(args.length > 0) {
            // google bounces back the default message types, you must use chat
            Message msg = new Message(args[0], Message.Type.chat);
            msg.setBody("Test");
            connection.sendPacket(msg);
        }else{
            // google bounces back the default message types, you must use chat
            Message msg = new Message("technik7job@gmail.com", Message.Type.chat);
            msg.setBody("Test");
            connection.sendPacket(msg);
        }
        
        System.out.println("Press enter to disconnect\n");
*/
        // --------------------------------------------
/*        ChatManager chatmanager = connection.getChatManager();
        Chat newChat = chatmanager.createChat("technik7job@gmail.com", new MessageListener() {
            public void processMessage(Chat chat, Message message) {
                System.out.println("Received message: " + message);
                System.out.println("Message Body: "+message.getBody());
                System.out.println("Message Subject: "+message.getSubject());
                System.out.println("Message From:"+message.getFrom());
                System.out.println("Message Thread: "+message.getThread());
                System.out.println("Message type:"+message.getType().toString());
            	try{
            		chat.sendMessage("server online");
            	}catch(Exception ex){};
            }
        });

        try {
            // ------------
        	newChat.sendMessage("Online!");
        	// ------------
        	//Message newMessage=new Message();
        	//newMessage.setBody("Howdy!");
        	//newMessage.setProperty("favoriteColor", "red");
        	//newChat.sendMessage(newMessage);
        }
        catch (XMPPException e) {
            System.out.println("Error Delivering block");
        }        
*/        
        try {
            System.in.read();
        } catch (IOException ex) {
            //ex.printStackTrace();
        	System.out.println("Exception ex:"+ex.getMessage());
        }
        Presence presence = new Presence(Presence.Type.unavailable);
        connection.sendPacket(presence);
        
        connection.disconnect();
        System.out.println("END.");
    }
}

/** listener for all chat's - listener for connection */
class MessageParrot implements PacketListener {
    private XMPPConnection xmppConnection;
    
    public MessageParrot(XMPPConnection conn) {
        xmppConnection = conn;
    }
    
    public void processPacket(Packet packet) {
        Message message = (Message)packet;
        if(message.getBody() != null) {
            String fromName = StringUtils.parseBareAddress(message.getFrom());
            System.out.println("Message from " + fromName + "\n" + message.getBody() + "\n");
            Message reply = new Message();
            reply.setTo(fromName);
            reply.setBody("I am a Java bot. You said: " + message.getBody());
            xmppConnection.sendPacket(reply);
        }
    }
};
