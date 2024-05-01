package vn.edu.iuh.fit;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class TopicReceiverRunner {
    public static void main(String[] args) throws NamingException, JMSException {
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        InitialContext jndi = new InitialContext(properties);
        ConnectionFactory factory = (ConnectionFactory) jndi.lookup("TopicConnectionFactory");
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = (Topic) jndi.lookup("dynamicTopics/teoTopic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage){
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println(textMessage.getText());
                    }
                } catch (JMSException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
