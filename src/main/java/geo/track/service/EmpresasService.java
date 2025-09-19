package geo.track.service;

import geo.track.domain.Empresas;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.EmpresasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresasService {
    private final EmpresasRepository repository;

    public EmpresasService(EmpresasRepository repository) {
        this.repository = repository;
    }

    public Empresas cadastrar(Empresas empresa){
        if (repository.findByCnpj(empresa.getCnpj()).isPresent()){
            throw new ConflictException("O CNPJ %s já está cadastrado!".formatted(empresa.getCnpj()), "Empresas");
        }
        return repository.save(empresa);
    }

    public List<Empresas> listar(){
        List<Empresas> listaEmpresas = repository.findAll();
        if(listaEmpresas.isEmpty()){
            throw new DataNotFoundException("A lista de empresas está vazia!", "Empresas");
        }
        return listaEmpresas;
    }

    public Empresas findEmpresaById(Integer id){
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("Empresa com ID %d não encontrado".formatted(id), "Empresas"));
    }

    public List<Empresas> findEmpresaByRazaoSocial(String razaoSocial){
        List<Empresas> empresasPorRazaoSocial = repository.findByrazaoSocialContainingIgnoreCase(razaoSocial);
        if (empresasPorRazaoSocial.isEmpty()){
            throw new DataNotFoundException("A Empresa %s não existe".formatted(razaoSocial), "Empresas");
        }
        return empresasPorRazaoSocial;
    }

    public Empresas findEmpresaByCnpj(String cnpj){
        return repository.findByCnpj(cnpj).orElseThrow(() -> new DataNotFoundException("Empresa com CNPJ %s não foi encontrado".formatted(cnpj), "Empresas"));
    }

    public Empresas atualizar(Integer id,Empresas empresa){
        if (repository.existsById(id)){
            empresa.setIdEmpresa(id);
            Empresas empSalva = repository.save(empresa);
            return empSalva;
        }
         throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Empresas");
    }

    public void remover(Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
        throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Empresas");
    }
}
