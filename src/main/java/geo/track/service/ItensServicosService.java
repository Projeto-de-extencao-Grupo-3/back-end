package geo.track.service;

import geo.track.domain.ItensServicos;
import geo.track.domain.OrdemDeServicos;
import geo.track.repository.ItensServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensServicosService {
    private final ItensServicoRepository itensServicoRepository;

    public List<ItensServicos> listarPelaOrdemServico(OrdemDeServicos ordemDeServicos) {
        List<ItensServicos> servicos = itensServicoRepository.findAllByFkOrdemServicoIdOrdemServico(ordemDeServicos.getIdOrdemServico());

        return servicos;
    }

    public ItensServicosService(ItensServicoRepository itensServicoRepository) {
        this.itensServicoRepository = itensServicoRepository;
    }
}
