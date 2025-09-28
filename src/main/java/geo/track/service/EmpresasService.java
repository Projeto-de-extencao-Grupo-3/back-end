package geo.track.service;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.domain.Empresas;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.empresas.request.EmpresaPatchEmailDTO;
import geo.track.dto.empresas.request.EmpresaPatchStatusDTO;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.EmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresasService {
    private final EmpresasRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public EmpresasService(EmpresasRepository repository) {
        this.repository = repository;
    }

    public Empresas cadastrar(Empresas empresa){
        if (repository.findByCnpj(empresa.getCnpj()).isPresent()){
            throw new ConflictException("O CNPJ %s já está cadastrado!".formatted(empresa.getCnpj()), "Empresas");
        }
        String senhaCriptografada = passwordEncoder.encode(empresa.getSenha());
        empresa.setSenha(senhaCriptografada);
        return repository.save(empresa);
    }

    public UsuarioTokenDto autenticar(Empresas empresa) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                empresa.getCnpj(), empresa.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Empresas usuarioAutenticado =
                empresasRepository.findByCnpj(empresa.getCnpj())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "CNPJ do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public List<Empresas> listar(){
        return repository.findAll();
    }

    public Empresas findEmpresaById(Integer id){
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("Empresa com ID %d não encontrado".formatted(id), "Empresas"));
    }

    public List<Empresas> findEmpresaByRazaoSocial(String razaoSocial){
        return repository.findByrazaoSocialContainingIgnoreCase(razaoSocial);
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

    public Empresas patchEmail(EmpresaPatchEmailDTO dto){
        Optional<Empresas> empresa = repository.findById(dto.getId());

        if(empresa.isEmpty()){
            throw new DataNotFoundException("Não existe uma empresa com esse ID", "Empresas");
        }
        Empresas emp = empresa.get();

        emp.setIdEmpresa(dto.getId());
        emp.setEmail(dto.getEmail());
        return repository.save(emp);
    }


    public Empresas patchStatus(EmpresaPatchStatusDTO dto){
        Optional<Empresas> empresa = repository.findById(dto.getId());

        if(empresa.isEmpty()){
            throw new DataNotFoundException("Não existe uma empresa com esse ID", "Empresas");
        }
        Empresas emp = empresa.get();

        emp.setIdEmpresa(dto.getId());
        emp.setStatus(dto.getStatus());
        return repository.save(emp);
    }

    public void remover(Integer id){
        if (!repository.existsById(id)){
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Empresas");
        }
        repository.deleteById(id);
    }
}
