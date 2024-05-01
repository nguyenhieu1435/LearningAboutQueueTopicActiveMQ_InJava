package vn.edu.iuh.fit;


import org.apache.activemq.ActiveMQConnection;
import org.apache.log4j.BasicConfigurator;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class QueueReceiverRunner {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static void main(String[] args) throws NamingException, JMSException {
        BasicConfigurator.configure();
        Properties settings = new Properties();
        settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        InitialContext jndi = new InitialContext(settings);


        ConnectionFactory factory = (ConnectionFactory) jndi.lookup("ConnectionFactory");
        Connection con = factory.createConnection("admin", "admin");
        con.start();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue destination = (Queue) jndi.lookup("dynamicQueues/thanthidet");
        MessageConsumer consumer = session.createConsumer(destination);

        System.out.println("Tý was listened on queue...");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage){
                        TextMessage tm = (TextMessage) message;
                        String txt = tm.getText();
                        System.out.println("Nhận được " + txt);
                    } else if (message instanceof  ObjectMessage){
                        ObjectMessage om = (ObjectMessage) message;
                        System.out.println(om);
                    }
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
