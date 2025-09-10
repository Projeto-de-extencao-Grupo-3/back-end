package geo.track.service;

import geo.track.domain.Empresas;
import geo.track.repository.EmpresasRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresasService {
    private final EmpresasRepository repository;

    public EmpresasService(EmpresasRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Empresas> cadastrar(Empresas empresa){
        if (repository.findBycnpj(empresa.getCnpj()).isPresent()){
            return ResponseEntity.status(409).build();
        }
        Empresas emp = repository.save(empresa);
        return ResponseEntity.status(201).body(emp);
    }

    public ResponseEntity<List<Empresas>> listar(){
        List<Empresas> listaEmpresas = repository.findAll();
        if(listaEmpresas.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaEmpresas);
    }

    public ResponseEntity<Empresas> findEmpresaById(Integer id){
        return ResponseEntity.of(repository.findById(id));
    }

    public ResponseEntity<List<Empresas>> findEmpresaByRazaoSocial(String razaoSocial){
        List<Empresas> empresasPorRazaoSocial = repository.findByrazaoSocialContainingIgnoreCase(razaoSocial);
        if (empresasPorRazaoSocial.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(empresasPorRazaoSocial);
    }

    public ResponseEntity<Empresas> findEmpresaByCNPJ(String CNPJ){
        return ResponseEntity.of(repository.findBycnpj(CNPJ));
    }

    public ResponseEntity<Empresas> atualizar(Integer id,Empresas empresa){
        if (repository.existsById(id)){
            empresa.setIdEmpresa(id);
            Empresas empSalva = repository.save(empresa);
            return ResponseEntity.status(200).body(empSalva);
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Empresas> remover(Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
