package geo.track.gestao.util;

import geo.track.dto.veiculos.response.VeiculoHistoricoResponse;
import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.Veiculo;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.dto.veiculos.request.RequestPutVeiculo;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.jornada.entity.OrdemDeServico;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoMapper {
    public static VeiculoResponse toResponse(Veiculo entity) {
        if (entity == null) {
            return null;
        }

        VeiculoResponse response = new VeiculoResponse();
        response.setIdVeiculo(entity.getIdVeiculo());
        response.setPlaca(entity.getPlaca());
        response.setModelo(entity.getModelo());
        response.setAnoModelo(entity.getAnoModelo());
        response.setMarca(entity.getMarca());
        response.setPrefixo(entity.getPrefixo());
        response.setIdCliente(entity.getFkCliente().getIdCliente());
        response.setNomeCliente(entity.getFkCliente().getNome());
        // Assuming fkCliente is not directly mapped to response, or handled elsewhere if needed.

        return response;
    }

    public static List<VeiculoResponse> toResponse(List<Veiculo> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(VeiculoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static Veiculo toEntity(RequestPostVeiculo dto) {
        if (dto == null) {
            return null;
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setPrefixo(dto.getPrefixo());
        veiculo.setAnoModelo(dto.getAnoModelo());

        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());

        veiculo.setFkCliente(cliente);

        return veiculo;
    }

    public static Veiculo toEntityUpdate(Veiculo existente, RequestPutVeiculo dto) {
        if (dto == null) {
            return existente;
        }
        if (dto.getMarca() != null) existente.setMarca(dto.getMarca());
        if (dto.getModelo() != null) existente.setModelo(dto.getModelo());
        if (dto.getPrefixo() != null) existente.setPrefixo(dto.getPrefixo());
        if (dto.getAnoModelo() != null) existente.setAnoModelo(dto.getAnoModelo());
        return existente;
    }

    public static VeiculoHistoricoResponse toResponseHistorico(Veiculo veiculo, OrdemDeServico ordem) {
        String status = switch (ordem.getStatus()) {
            case AGUARDANDO_ENTRADA -> "Com agendamento";
            case AGUARDANDO_ORCAMENTO, AGUARDANDO_AUTORIZACAO, AGUARDANDO_VAGA -> "Presente na oficina";
            case EM_PRODUCAO -> "Em produção";
            case FINALIZADO, CANCELADO -> "Sem agendamento";
        };

        return new VeiculoHistoricoResponse(
                veiculo.getIdVeiculo(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getAnoModelo(),
                veiculo.getMarca(),
                veiculo.getPrefixo(),
                veiculo.getFkCliente().getNome(),
                veiculo.getFkCliente().getIdCliente(),
                status
                );
    }
}
