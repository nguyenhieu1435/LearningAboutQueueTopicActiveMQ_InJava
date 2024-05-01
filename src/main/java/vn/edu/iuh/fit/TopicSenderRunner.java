package vn.edu.iuh.fit;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class TopicSenderRunner {
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

        MessageProducer producer = session.createProducer(topic);
        TextMessage message = session.createTextMessage("Hello subscribers!");
        producer.send(message);
        connection.close();
    }
}
