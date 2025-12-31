package com.lucasteixeira.infrastructure.client;

import com.lucasteixeira.business.dto.ViaCepDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViaCepClientTest {

    @Mock
    private ViaCepClient viaCepClient;


    @Test
    @DisplayName("Should valide the cep and return if cep exists ou not")
    void buscaDadosDeEndereco() {

        String cep = "01001-000";

        ViaCepDTO viaCepDTO = new ViaCepDTO();
        viaCepDTO.setCep(cep);
        viaCepDTO.setLogradouro("Rio de Janeiro");

        when(viaCepClient.buscaDadosDeEndereco(cep)).thenReturn(viaCepDTO);

        ViaCepDTO result = viaCepClient.buscaDadosDeEndereco(cep);

        assertThat(result.getCep()).isEqualTo(cep);

    }
}