package geo.track.dto.oficinas.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OficinaResponse {
    private Integer idOficina;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private LocalDateTime dtCriacao;
    private Boolean status;
}
