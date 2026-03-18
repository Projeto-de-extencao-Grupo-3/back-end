package geo.track.mapper;

import geo.track.domain.Veiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.dto.registroEntrada.response.RegistroEntradaCriacaoResponse;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class RegistroEntradaMapper {
    public static RegistroEntradaResponse toResponse(RegistroEntrada entity) {
        if (entity == null) {
            return null;
        }

        RegistroEntradaResponse response = new RegistroEntradaResponse();
        response.setIdRegistroEntrada(entity.getIdRegistroEntrada());
        response.setDataEntradaPrevista(entity.getDataEntradaPrevista());
        response.setDataEntradaEfetiva(entity.getDataEntradaEfetiva());
        response.setResponsavel(entity.getResponsavel());
        response.setCpf(entity.getCpf());
        response.setExtintor(entity.getExtintor());
        response.setMacaco(entity.getMacaco());
        response.setChaveRoda(entity.getChaveRoda());
        response.setGeladeira(entity.getGeladeira());
        response.setMonitor(entity.getMonitor());
        response.setEstepe(entity.getEstepe());
        response.setSomDvd(entity.getSomDvd());
        response.setCaixaFerramenta(entity.getCaixaFerramentas());

        if (entity.getFkVeiculo() != null) {
            response.setIdVeiculo(entity.getFkVeiculo().getIdVeiculo());
        }

        return response;
    }

    public static List<RegistroEntradaResponse> toResponse(List<RegistroEntrada> entities) {
        return entities.stream()
                .map(RegistroEntradaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static RegistroEntradaCriacaoResponse toResponsePost(RegistroEntrada registro) {
        RegistroEntradaCriacaoResponse response = new RegistroEntradaCriacaoResponse();

        response.setEntrada(toResponse(registro));
        response.setOrdemServico(OrdemDeServicoMapper.toCard(registro.getFkOrdemServico()));

        return response;
    }
    
    public static RegistroEntrada toEntityUpdate(RegistroEntrada entity, RequestConfirmacao request) {
        if (request == null || entity == null) {
            return entity;
        }

        entity.setDataEntradaEfetiva(LocalDate.now());
        entity.setResponsavel(request.responsavel());
        entity.setCpf(request.cpf());
        entity.setExtintor(request.extintor());
        entity.setMacaco(request.macaco());
        entity.setChaveRoda(request.chaveRoda());
        entity.setGeladeira(request.geladeira());
        entity.setMonitor(request.monitor());
        entity.setEstepe(request.estepe());
        entity.setSomDvd(request.somDvd());
        entity.setCaixaFerramentas(request.caixaFerramentas());

        if (request.observacoes() != null) entity.setObservacoes(request.observacoes());

        return entity;
    }

    public static RegistroEntrada toEntity(RequestEntradaEfetiva request, Veiculo veiculo, OrdemDeServico ordemDeServico) {
        RegistroEntrada entrada = new RegistroEntrada();

        entrada.setDataEntradaEfetiva(LocalDate.now());
        entrada.setDataEntradaPrevista(LocalDate.now());
        entrada.setResponsavel(request.responsavel());
        entrada.setCpf(request.cpf());
        entrada.setExtintor(request.extintor());
        entrada.setMacaco(request.macaco());
        entrada.setChaveRoda(request.chaveRoda());
        entrada.setGeladeira(request.geladeira());
        entrada.setMonitor(request.monitor());
        entrada.setEstepe(request.estepe());
        entrada.setSomDvd(request.somDvd());
        entrada.setCaixaFerramentas(request.caixaFerramentas());
        if (request.observacoes() != null) entrada.setObservacoes(request.observacoes());

        entrada.setFkVeiculo(veiculo);
        entrada.setFkOrdemServico(ordemDeServico);

        return entrada;
    }
}
