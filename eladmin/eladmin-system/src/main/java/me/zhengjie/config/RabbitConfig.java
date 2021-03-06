package me.zhengjie.config;

import me.zhengjie.utils.MessageConverter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new MessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new MessageConverter());
        return factory;
    }
    /**
     * @Author : JCccc
     * @CreateTime : 2019/9/3
     * @Description :
     **/
    @Configuration
    public class DirectRabbitConfig {
        //队列 起名：MailDirectQueue
        @Bean
        public Queue MailDirectQueue() {
            // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
            // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
            // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
            //   return new Queue("TestDirectQueue",true,true,false);

            //一般设置一下队列的持久化就好,其余两个就是默认false
            return new Queue("MailDirectQueue", true);
        }

        //队列 起名：MailDirectQueue2
        @Bean
        public Queue MailDirectQueue2() {
            // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
            // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
            // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
            //   return new Queue("TestDirectQueue",true,true,false);

            //一般设置一下队列的持久化就好,其余两个就是默认false
            return new Queue("MailDirectQueue2", true);
        }

        //队列 起名：DingTipQueue
        @Bean
        public Queue DingTipQueue() {
            // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
            // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
            // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
            //   return new Queue("TestDirectQueue",true,true,false);

            //一般设置一下队列的持久化就好,其余两个就是默认false
            return new Queue("DingTipQueue", true);
        }

        //队列 起名：DingTipQueue
        @Bean
        public Queue DingTipQueue2() {
            // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
            // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
            // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
            //   return new Queue("TestDirectQueue",true,true,false);

            //一般设置一下队列的持久化就好,其余两个就是默认false
            return new Queue("DingTipQueue2", true);
        }

        //Direct交换机 起名：TestDirectExchange
        @Bean
        DirectExchange MailDirectExchange() {
            //  return new DirectExchange("TestDirectExchange",true,true);
            return new DirectExchange("MailDirectExchange", true, false);
        }

        //Direct交换机 起名：DingTipDirectExchange
        @Bean
        DirectExchange DingTipDirectExchange() {
            //  return new DirectExchange("TestDirectExchange",true,true);
            return new DirectExchange("DingTipDirectExchange", true, false);
        }


        //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
        @Bean
        Binding bindingDirect() {
            return BindingBuilder.bind(MailDirectQueue()).to(MailDirectExchange()).with("MailDirectRouting");
        }


        //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting2
        @Bean
        Binding bindingDirect2() {
            return BindingBuilder.bind(MailDirectQueue2()).to(MailDirectExchange()).with("MailDirectRouting2");
        }

        //绑定  将队列和交换机绑定, 并设置用于匹配键：DingTipDirectRouting
        @Bean
        Binding bindingDingTipDirect() {
            return BindingBuilder.bind(DingTipQueue()).to(DingTipDirectExchange()).with("DingTipDirectRouting");
        }

        //绑定  将队列和交换机绑定, 并设置用于匹配键：DingTipDirectRouting
        @Bean
        Binding bindingDingTipDirect2() {
            return BindingBuilder.bind(DingTipQueue2()).to(DingTipDirectExchange()).with("DingTipDirectRouting2");
        }

        @Bean
        DirectExchange lonelyDirectExchange() {
            return new DirectExchange("lonelyDirectExchange");
        }
    }
}
