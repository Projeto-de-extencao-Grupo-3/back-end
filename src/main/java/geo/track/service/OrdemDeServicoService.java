package geo.track.service;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.RegistroEntrada;
import geo.track.dto.os.request.*;
import geo.track.exception.BadRequestException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ForbiddenException;
import geo.track.repository.OrdemDeServicoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdemDeServicoService {
    private final OrdemDeServicoRepository ordemRepository;
    private final ItemServicoService itemServicoService;
    private final RegistroEntradaService registroEntradaService;

    public OrdemDeServico postOrdem(@Valid @RequestBody PostEntradaVeiculo ordemDTO) {
        RegistroEntrada entrada = registroEntradaService.findRegistroById(ordemDTO.getFkEntrada());

//      Data prevista ser da seguinte forma : Data atual(HOJE) + 1 Mês para frente
        LocalDate dataPrevista = LocalDate.now().plusMonths(1);

        OrdemDeServico ordem = OrdemDeServico.builder()
                .valorTotal(ordemDTO.getValorTotal())
                .status(ordemDTO.getStatus())
                .dtSaidaPrevista(dataPrevista)
                .dtSaidaEfetiva(null)
                .seguradora(false)
                .nfRealizada(false)
                .pagtRealizado(false)
                .ativo(true)
                .fk_entrada(entrada)
                .build();

        return ordemRepository.save(ordem);
    }

    public List<OrdemDeServico> findOrdem(){
        return ordemRepository.findAll();
    }

    public OrdemDeServico findOrdemById(Integer idOrdem){
        Optional<OrdemDeServico> ordem = ordemRepository.findById(idOrdem);

        if (ordem.isEmpty()){
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(idOrdem), "Ordem de Serviço");
        }
        return ordem.get();
    }

    public OrdemDeServico putValorESaida(RequestPutValorESaida ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setValorTotal(ordemDTO.getValorTotal());
        ordem.setDtSaidaPrevista(ordemDTO.getSaidaPrevista());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServico patchSaidaEfetiva(RequestPatchSaidaEfetiva ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }
        
        OrdemDeServico ordem = ordemOPT.get();
        
        ordem.setDtSaidaEfetiva(ordemDTO.getDtSaidaEfeiva());
        
        return ordemRepository.save(ordem);
    }

    public OrdemDeServico patchStatus(RequestPatchStatus ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setStatus(ordemDTO.getStatus());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServico patchSeguradora(RequestPatchSeguradora ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(ordemDTO.getSeguradora());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServico patchNfRealizada(RequestPatchNfRealizada ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(ordemDTO.getNfRealizada());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServico patchPagtoRealizado(RequestPatchPagtoRealizado ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setPagtRealizado(ordemDTO.getPagtoRealizado());
        return ordemRepository.save(ordem);
    }

    public void deleteOrdem(Integer idOrdem){
        Optional<OrdemDeServico> ordemOPT = ordemRepository.findById(idOrdem);

        if (ordemOPT.isEmpty()) {
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();
        RegistroEntrada entrada = ordem.getFk_entrada();

        List<ItemServico> servicos = itemServicoService.listarPelaOrdemServico(ordem);

        if (!servicos.isEmpty()) {
            throw new BadRequestException("Não é possível deletar ordem de serviço que possui serviços anexados", "Ordem de Serviço");
        }

        if (entrada == null){
            throw new ForbiddenException("Solicitação recusada", "Ordem de Serviço");
        }

        // verificar se tem serviços atrelado
        ordemRepository.delete(ordem);
    }
}

