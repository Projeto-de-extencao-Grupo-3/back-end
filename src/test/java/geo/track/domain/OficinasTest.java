package geo.track.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

@DisplayName("Classe Oficinas Testes")
class OficinasTest {

    @Test
    void deveCriarOficinaComConstrutorVazio() {
        LocalDateTime agora = LocalDateTime.now();
        Oficinas oficina = new Oficinas();

        oficina.setIdOficina(1);
        oficina.setRazaoSocial("Auto Center São Lucas LTDA");
        oficina.setCnpj("12.345.678/0001-99");
        oficina.setEmail("contato@autocenter.com");
        oficina.setDtCriacao(agora);
        oficina.setStatus(true);
        oficina.setSenha("123456");

        Assertions.assertEquals(1, oficina.getIdOficina());
        Assertions.assertEquals("Auto Center São Lucas LTDA", oficina.getRazaoSocial());
        Assertions.assertEquals("12.345.678/0001-99", oficina.getCnpj());
        Assertions.assertEquals("contato@autocenter.com", oficina.getEmail());
        Assertions.assertEquals(agora, oficina.getDtCriacao());
        Assertions.assertEquals(true, oficina.getStatus());
        Assertions.assertEquals("123456", oficina.getSenha());
    }

    @Test
    void deveCriarOficinaComConstrutorCheio() {
        LocalDateTime dataCriacao = LocalDateTime.of(2025, 10, 25, 14, 30);

        Oficinas oficina = new Oficinas(
                2,
                "Oficina Teste LTDA",
                "98.765.432/0001-11",
                "teste@oficina.com",
                dataCriacao,
                false,
                "senhaSegura",
                List.of(),
                List.of()
        );

        Assertions.assertEquals(2, oficina.getIdOficina());
        Assertions.assertEquals("Oficina Teste LTDA", oficina.getRazaoSocial());
        Assertions.assertEquals("98.765.432/0001-11", oficina.getCnpj());
        Assertions.assertEquals("teste@oficina.com", oficina.getEmail());
        Assertions.assertEquals(dataCriacao, oficina.getDtCriacao());
        Assertions.assertEquals(false, oficina.getStatus());
        Assertions.assertEquals("senhaSegura", oficina.getSenha());
        Assertions.assertEquals(List.of(), oficina.getFuncionarios());
        Assertions.assertEquals(List.of(), oficina.getClientes());
    }
}
