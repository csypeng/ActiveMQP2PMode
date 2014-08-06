import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Publisher {

    private String user = ActiveMQConnection.DEFAULT_USER;  
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;  
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;  
    private String subject = "mytopic";  
    private Destination[] destinations = null;  
    private Connection connection = null;  
    private Session session = null;  
    private MessageProducer producer = null;
    
    public Publisher() throws JMSException {  
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);  
        connection = factory.createConnection();  
        connection.start();  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        producer = session.createProducer(null);  
    }  
    
    public void sendMessage() throws JMSException {  
    	String[] jobs = new String[2];
    	jobs[0] = "s1";
    	jobs[1] = "s2";
    	
    	String[] msgs = new String[2];
    	msgs[0] = "oh1";
    	msgs[1] = "oh2";
    	
    	for(int i = 0; i < jobs.length; i++)  
        {  
            String job = jobs[i];  
            Destination destination = session.createQueue("JOBS." + job);  
            Message message = session.createTextMessage(msgs[i]);  
            System.out.println("Sending: id: " + ((TextMessage)message).getText() + " on queue: " + destination);  
            producer.send(destination, message);  
        }  
    }  
    
    public void close() throws JMSException {  
        if (connection != null) {  
            connection.close();  
         }  
    }  
    
    public static void main(String[] args) throws JMSException {  
        Publisher publisher = new Publisher();  
        for(int i = 0; i < 10; i++) {  
            publisher.sendMessage();  
            System.out.println("Published " + i + " job messages");  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
            	e.printStackTrace();  
            }  
        }  
        publisher.close();  
    }  
}
