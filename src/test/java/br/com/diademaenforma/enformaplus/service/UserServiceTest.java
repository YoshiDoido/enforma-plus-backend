package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.model.user.Papel;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.model.user.UserDTO;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ct01_usuario_deve_cadastrar_com_sucesso() {
        UserDTO inputDto = new UserDTO();
        inputDto.setUsuario("Paulo Ferreira");
        inputDto.setEmail("paulo.ferreira@gmail.com");
        inputDto.setSenha("paulo123");
        inputDto.setPapel("USUARIO_COMUM");

        User user = new User();
        user.setId(1L);
        user.setUsuario("Paulo Ferreira");
        user.setEmail("paulo.ferreira@gmail.com");
        user.setSenha("encodedSenha");
        user.setPapel(Papel.USUARIO_COMUM);

        when(passwordEncoder.encode("paulo123")).thenReturn("encodedSenha");
        when(userRepository.findByEmail("paulo.ferreira@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.saveUser(inputDto);

        assertNotNull(result);
        assertEquals("Paulo Ferreira", result.getUsuario());
        assertEquals("paulo.ferreira@gmail.com", result.getEmail());
        assertEquals("USUARIO_COMUM", result.getPapel());
    }

    @Test
    void ct02_erro_cadastro_somente_com_nome() {
        UserDTO inputDto = new UserDTO();
        inputDto.setUsuario("João");

        assertThrows(ResponseStatusException.class, () -> userService.saveUser(inputDto));
    }

    @Test
    void ct03_erro_cadastro_somente_com_email() {
        UserDTO inputDto = new UserDTO();
        inputDto.setEmail("joao@email.com");

        assertThrows(ResponseStatusException.class, () -> userService.saveUser(inputDto));
    }

    @Test
    void ct04_erro_cadastro_somente_com_senha() {
        UserDTO inputDto = new UserDTO();
        inputDto.setSenha("123456");

        assertThrows(ResponseStatusException.class, () -> userService.saveUser(inputDto));
    }

    @Test
    void ct05_erro_cadastro_somente_com_papel() {
        UserDTO inputDto = new UserDTO();
        inputDto.setPapel("USUARIO_COMUM");

        assertThrows(ResponseStatusException.class, () -> userService.saveUser(inputDto));
    }

    @Test
    void ct06_erro_cadastro_papel_profissional_sem_especialidade() {
        UserDTO inputDto = new UserDTO();
        inputDto.setUsuario("Maria");
        inputDto.setEmail("maria@email.com");
        inputDto.setSenha("123456");
        inputDto.setPapel("USUARIO_PROFISSIONAL");

        assertThrows(ResponseStatusException.class, () -> userService.saveUser(inputDto));
    }

    @Test
    void ct07_erro_cadastro_com_campos_obrigatorios_ausentes_profissional() {
        UserDTO inputDto = new UserDTO();
        inputDto.setPapel("USUARIO_PROFISSIONAL");
        inputDto.setEspecialidade("NUTRICIONISTA");

        assertThrows(ResponseStatusException.class,
                () -> userService.saveUser(inputDto),
                "Esperado erro de BAD_REQUEST quando faltam campos obrigatórios"
        );
    }

    @Test
    void ct08_erro_cadastro_com_email_ja_existente() {
        UserDTO inputDto = new UserDTO();
        inputDto.setUsuario("João");
        inputDto.setEmail("joao@email.com");
        inputDto.setSenha("123456");
        inputDto.setPapel("USUARIO_COMUM");

        // Simula que já existe um usuário com esse e-mail
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("joao@email.com");

        when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(existingUser));


        assertThrows(ResponseStatusException.class, () -> userService.saveUser(inputDto));
    }

}
