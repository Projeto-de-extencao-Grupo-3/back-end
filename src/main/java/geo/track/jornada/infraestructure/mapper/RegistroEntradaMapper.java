package geo.track.jornada.infraestructure.mapper;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.jornada.infraestructure.persistence.entity.ItemEntrada;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.response.entrada.RegistroEntradaCriacaoResponse;
import geo.track.jornada.infraestructure.request.entrada.RequestEntrada;
import geo.track.jornada.infraestructure.response.entrada.RegistroEntradaResponse;

import java.time.LocalDate;
import java.util.ArrayList;
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


        response.setFkOrdemServico(entity.getFkOrdemServico().getIdOrdemServico());

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
    
    public static RegistroEntrada toEntityUpdate(RegistroEntrada entity, RequestEntrada request) {
        if (request == null || entity == null) {
            return entity;
        }

        entity.setDataEntradaEfetiva(LocalDate.now());
        entity.setResponsavel(request.responsavel());
        entity.setCpf(request.cpf());

        entity.setItensEntrada(toItemEntrada(request, entity));

        return entity;
    }

    public static RegistroEntrada toEntity(RequestEntrada request, Veiculo veiculo, OrdemDeServico ordemDeServico) {
        RegistroEntrada entrada = new RegistroEntrada();

        entrada.setDataEntradaEfetiva(LocalDate.now());
        entrada.setDataEntradaPrevista(LocalDate.now());
        entrada.setResponsavel(request.responsavel());
        entrada.setCpf(request.cpf());

        entrada.setItensEntrada(toItemEntrada(request, entrada));

        entrada.setFkVeiculo(veiculo);
        entrada.setFkOrdemServico(ordemDeServico);

        return entrada;
    }

    private static List<ItemEntrada> toItemEntrada(RequestEntrada request, RegistroEntrada registro) {
        List<ItemEntrada> itensEntrada = new ArrayList<>();

        request.itensEntrada().forEach(item -> {
            ItemEntrada itemEntrada = new ItemEntrada();
            itemEntrada.setNomeItem(item.nomeItem());
            itemEntrada.setQuantidade(item.quantidade());
            itemEntrada.setFkRegistroEntrada(registro);
            itensEntrada.add(itemEntrada);
        });

        return itensEntrada;
    }
}
