package br.com.diademaenforma.enformaplus.rabbitmq;

import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "${mensageria.queue}")
    public void receberMensagem(String mensagemJson) {
        try {
            AgendamentoResponseDTO dto = objectMapper.readValue(mensagemJson, AgendamentoResponseDTO.class);
            System.out.println("📩 Mensagem recebida do RabbitMQ:");
            System.out.println(dto);
        } catch (Exception e) {
            System.err.println("❌ Erro ao desserializar mensagem do RabbitMQ: " + e.getMessage());
        }
    }
}
