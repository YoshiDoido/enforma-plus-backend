package br.com.diademaenforma.enformaplus.rabbitmq;

import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${mensageria.queue}")
    private String queue;

    public AgendamentoProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarMensagem(AgendamentoResponseDTO dto) {
        try {
            String mensagemJson = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(queue, mensagemJson);
        } catch (JsonProcessingException e) {
            System.err.println("❌ Erro ao converter DTO para JSON: " + e.getMessage());
        }
    }
}

