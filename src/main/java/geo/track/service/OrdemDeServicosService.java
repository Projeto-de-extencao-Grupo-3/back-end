package geo.track.service;

import geo.track.domain.OrdemDeServicos;
import geo.track.dto.os.request.*;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ForbiddenException;
import geo.track.repository.OrdemDeServicosRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdemDeServicosService {
    private final OrdemDeServicosRepository ordemRepository;

    public OrdemDeServicos postOrdem(@Valid @RequestBody PostEntradaVeiculo ordemDTO){
        OrdemDeServicos ordem = new OrdemDeServicos();

        ordem.setStatus(ordemDTO.getStatus());
        ordem.setValorTotal(ordemDTO.getValorTotal());
//        ordem.setFk_entrada(ordemDTO.getFkEntrada());

        return ordemRepository.save(ordem);
    }

    public List<OrdemDeServicos> findOrdem(){
        return ordemRepository.findAll();
    }

    public OrdemDeServicos findOrdemById(Integer idOrdem){
        Optional<OrdemDeServicos> ordem = ordemRepository.findById(idOrdem);

        if (ordem.isEmpty()){
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(idOrdem), "Ordem de Serviço");
        }
        return ordem.get();
    }

    public OrdemDeServicos putValorESaida(RequestPutValorESaida ordemDTO){
        Optional<OrdemDeServicos> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServicos ordem = ordemOPT.get();

        ordem.setValorTotal(ordemDTO.getValorTotal());
        ordem.setDtSaidaPrevista(ordemDTO.getSaidaPrevista());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServicos patchSaidaEfetiva(RequestPatchSaidaEfetiva ordemDTO){
        Optional<OrdemDeServicos> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }
        
        OrdemDeServicos ordem = ordemOPT.get();
        
        ordem.setDtSaidaEfetiva(ordemDTO.getDtSaidaEfeiva());
        
        return ordemRepository.save(ordem);
    }

    public OrdemDeServicos patchStatus(RequestPatchStatus ordemDTO){
        Optional<OrdemDeServicos> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServicos ordem = ordemOPT.get();

        ordem.setStatus(ordemDTO.getStatus());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServicos patchSeguradora(RequestPatchSeguradora ordemDTO){
        Optional<OrdemDeServicos> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServicos ordem = ordemOPT.get();

        ordem.setSeguradora(ordemDTO.getSeguradora());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServicos patchNfRealizada(RequestPatchNfRealizada ordemDTO){
        Optional<OrdemDeServicos> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServicos ordem = ordemOPT.get();

        ordem.setSeguradora(ordemDTO.getNfRealizada());

        return ordemRepository.save(ordem);
    }

    public OrdemDeServicos patchPagtoRealizado(RequestPatchPagtoRealizado ordemDTO){
        Optional<OrdemDeServicos> ordemOPT = ordemRepository.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServicos ordem = ordemOPT.get();

        ordem.setPagtRealizado(ordemDTO.getPagtoRealizado());
        return ordemRepository.save(ordem);
    }

    public void deleteOrdem(Integer idOrdem){
        Optional<OrdemDeServicos> ordemOPT = ordemRepository.findById(idOrdem);

        if (ordemOPT.isEmpty()) {
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServicos ordem = ordemOPT.get();

        if (ordem.getFk_entrada() == null){
            throw new ForbiddenException("Solicitação recusada", "Ordem de Serviço");
        }

    }

}

