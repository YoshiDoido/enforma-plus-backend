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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.saveUser(inputDto);

        assertNotNull(result);
        assertEquals("Paulo Ferreira", result.getUsuario());
        assertEquals("paulo.ferreira@gmail.com", result.getEmail());
        assertEquals("USUARIO_COMUM", result.getPapel());
    }

}
