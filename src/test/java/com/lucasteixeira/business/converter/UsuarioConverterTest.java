package com.lucasteixeira.business.converter;

import com.lucasteixeira.business.dto.EnderecoDTO;
import com.lucasteixeira.business.dto.TelefoneDTO;
import com.lucasteixeira.business.dto.UsuarioDTO;
import com.lucasteixeira.infrastructure.entity.Endereco;
import com.lucasteixeira.infrastructure.entity.Telefone;
import com.lucasteixeira.infrastructure.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


    class UsuarioConverterTest {

        private final UsuarioConverter converter = new UsuarioConverter();

        @Test
        @DisplayName("Should convert UsuarioDTO to Usuario Entity including lists")
        void paraUsuario() {
            EnderecoDTO enderecoDTO = EnderecoDTO.builder().rua("Main St").build();
            TelefoneDTO telefoneDTO = TelefoneDTO.builder().numero("99999999").build();
            UsuarioDTO dto = UsuarioDTO.builder()
                    .nome("Lucas")
                    .endereco(List.of(enderecoDTO))
                    .telefone(List.of(telefoneDTO))
                    .build();

            Usuario result = converter.paraUsuario(dto);

            assertThat(result.getNome()).isEqualTo(dto.getNome());
            assertThat(result.getEnderecos()).hasSize(1);
            assertThat(result.getTelefones()).hasSize(1);
        }

        @Test
        @DisplayName("Should convert list of EnderecoDTO to list of Endereco Entity")
        void paraListaEndereco() {
            List<EnderecoDTO> dtos = List.of(EnderecoDTO.builder().rua("R1").build(), EnderecoDTO.builder().rua("R2").build());

            List<Endereco> result = converter.paraListaEndereco(dtos);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getRua()).isEqualTo("R1");
        }

        @Test
        @DisplayName("Should convert EnderecoDTO to Endereco Entity")
        void paraEndereco() {
            EnderecoDTO dto = EnderecoDTO.builder().rua("Street").cidade("City").build();

            Endereco result = converter.paraEndereco(dto);

            assertThat(result.getRua()).isEqualTo(dto.getRua());
            assertThat(result.getCidade()).isEqualTo(dto.getCidade());
        }

        @Test
        @DisplayName("Should convert list of TelefoneDTO to list of Telefone Entity")
        void paraListaTelefones() {
            List<TelefoneDTO> dtos = List.of(TelefoneDTO.builder().numero("123").build());

            List<Telefone> result = converter.paraListaTelefones(dtos);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getNumero()).isEqualTo("123");
        }

        @Test
        @DisplayName("Should convert TelefoneDTO to Telefone Entity")
        void paraTelefone() {
            TelefoneDTO dto = TelefoneDTO.builder().numero("123").ddd("11").build();

            Telefone result = converter.paraTelefone(dto);

            assertThat(result.getNumero()).isEqualTo(dto.getNumero());
            assertThat(result.getDdd()).isEqualTo(dto.getDdd());
        }

        @Test
        @DisplayName("Should convert Usuario Entity to UsuarioDTO including lists")
        void paraUsuarioDTO() {
            Usuario entity = Usuario.builder()
                    .nome("Lucas")
                    .enderecos(List.of(Endereco.builder().rua("Rua").build()))
                    .build();

            UsuarioDTO result = converter.paraUsuarioDTO(entity);

            assertThat(result.getNome()).isEqualTo(entity.getNome());
            assertThat(result.getEndereco()).isNotEmpty();
        }

        @Test
        @DisplayName("Should update Usuario entity fields only if DTO fields are not null")
        void updateUsuario() {
            Usuario entity = Usuario.builder().id(1L).nome("Old").email("old@mail.com").build();
            UsuarioDTO dto = UsuarioDTO.builder().nome("New").build(); // Email is null

            Usuario result = converter.updateUsuario(dto, entity);

            assertThat(result.getNome()).isEqualTo("New");
            assertThat(result.getEmail()).isEqualTo("old@mail.com");
            assertThat(result.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should update Endereco entity fields and maintain user relation")
        void updateEndereco() {
            Endereco entity = Endereco.builder().id(1L).rua("Old Rua").usuario_Id(10L).build();
            EnderecoDTO dto = EnderecoDTO.builder().rua("New Rua").build();

            Endereco result = converter.updateEndereco(dto, entity);

            assertThat(result.getRua()).isEqualTo("New Rua");
            assertThat(result.getUsuario_Id()).isEqualTo(10L);
        }

        @Test
        @DisplayName("Should update Telefone entity fields")
        void updateTelefone() {
            Telefone entity = Telefone.builder().id(1L).numero("111").usuario_Id(5L).build();
            TelefoneDTO dto = TelefoneDTO.builder().numero("222").build();

            Telefone result = converter.updateTelefone(dto, entity);

            assertThat(result.getNumero()).isEqualTo("222");
            assertThat(result.getUsuario_Id()).isEqualTo(5L);
        }

        @Test
        @DisplayName("Should convert EnderecoDTO to Entity with explicit User ID")
        void paraEnderecoEntity() {
            EnderecoDTO dto = EnderecoDTO.builder().rua("Rua X").build();

            Endereco result = converter.paraEnderecoEntity(dto, 100L);

            assertThat(result.getRua()).isEqualTo("Rua X");
            assertThat(result.getUsuario_Id()).isEqualTo(100L);
        }

        @Test
        @DisplayName("Should convert TelefoneDTO to Entity with explicit User ID")
        void paraTelefoneEntity() {
            TelefoneDTO dto = TelefoneDTO.builder().numero("123").build();

            Telefone result = converter.paraTelefoneEntity(dto, 200L);

            assertThat(result.getNumero()).isEqualTo("123");
            assertThat(result.getUsuario_Id()).isEqualTo(200L);
        }
}