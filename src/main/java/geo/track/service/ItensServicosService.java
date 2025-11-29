package geo.track.service;

import geo.track.domain.ItensServicos;
import geo.track.domain.OrdemDeServicos;
import geo.track.domain.RegistroEntrada;
import geo.track.dto.os.request.PostEntradaVeiculo;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ItensServicoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItensServicosService {
    private final ItensServicoRepository itensServicoRepository;

    public List<ItensServicos> listar(){
        return itensServicoRepository.findAll();
    }

    public ItensServicos listarPorId(Integer id){
        Optional<ItensServicos> ordem = itensServicoRepository.findById(id);

        if (ordem.isEmpty()){
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Itens de Serviço");
        }
        return ordem.get();
    }

    public List<ItensServicos> listarPelaOrdemServico(OrdemDeServicos ordemDeServicos) {
        List<ItensServicos> servicos = itensServicoRepository.findAllByFkOrdemServicoIdOrdemServico(ordemDeServicos.getIdOrdemServico());

        return servicos;
    }
}
