package br.com.diademaenforma.enformaplus.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${mensageria.queue}")
    private String queue;

    @Bean
    public Queue agendamentoQueue() {
        return new Queue(queue, false);
    }
}

