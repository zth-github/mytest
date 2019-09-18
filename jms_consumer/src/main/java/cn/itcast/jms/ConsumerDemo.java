package cn.itcast.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerDemo {
    public static void main(String[] args) throws Exception {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        connection.start();//启动连接
        //3.创建session 两个参数:1.是否启动事务 2.消息的确认机制
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地
        Queue destination = session.createQueue("queue_test");
        //5.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //6.给消费者设置监听 由监听帮助消费消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage= (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println("消费者接受的消息:"+text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();//想让监听器处于一个工作状态
        //关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
