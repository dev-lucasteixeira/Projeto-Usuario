package com.lucasteixeira.infrastructure.repository;

import com.lucasteixeira.infrastructure.entity.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Mock
    EntityManager entityManager;

    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Should get User from DB when user exists")
    void existsByEmail() {

        String email = "teste@teste.com";
        createUser(email);

        boolean result = usuarioRepository.existsByEmail(email);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when user not exists")
    void notExistsByEmail() {

        String email = "teste@teste.com";

        boolean result = usuarioRepository.existsByEmail(email);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should get User successfully from DB")
    @WithMockUser(username = "teste@teste.com")
    void findByEmail() {
        String email = "teste@teste.com";
        createUser(email);

        var result = usuarioRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Should delete User successfully from DB")
    void deleteByEmail() {

        String email = "teste@teste.com";
        createUser(email);

        usuarioRepository.deleteByEmail(email);

        var result = usuarioRepository.findByEmail(email);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should delete User Unsuccessful from DB")
    void deleteByEmailUnsuccessful() {

        String email = "teste@teste.com";
        createUser(email);

        usuarioRepository.deleteByEmail("teste1@teste.com");

        var result = usuarioRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
    }

    private Usuario createUser(String email) {
        Usuario newUser = new Usuario();
        newUser.setNome("Lucas");
        newUser.setEmail(email);
        newUser.setSenha("123456");

        this.entityManager.persist(newUser);
        return newUser;
    }
}