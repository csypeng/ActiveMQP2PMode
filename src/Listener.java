import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

    public class Listener implements MessageListener {  
      
        public void onMessage(Message message) {  
            try {  
                System.out.println("Receive: " + ((TextMessage) message).getText() +" topic: "+message.getJMSDestination());
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
      
    }  