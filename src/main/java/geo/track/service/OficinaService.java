package geo.track.service;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.domain.Oficinas;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.OficinaRepository;
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
public class OficinaService {
    private final OficinaRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public OficinaService(OficinaRepository repository) {
        this.repository = repository;
    }

    public Oficinas cadastrar(Oficinas empresa){
        if (repository.findByCnpj(empresa.getCnpj()).isPresent()){
            throw new ConflictException("O CNPJ %s já está cadastrado!".formatted(empresa.getCnpj()), "Empresas");
        }
        String senhaCriptografada = passwordEncoder.encode(empresa.getSenha());
        empresa.setSenha(senhaCriptografada);
        return repository.save(empresa);
    }

    public UsuarioTokenDto autenticar(Oficinas empresa) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                empresa.getCnpj(), empresa.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Oficinas usuarioAutenticado =
                oficinaRepository.findByCnpj(empresa.getCnpj())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "CNPJ do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public List<Oficinas> listar(){
        return repository.findAll();
    }

    public Oficinas findEmpresaById(Integer id){
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("Empresa com ID %d não encontrado".formatted(id), "Empresas"));
    }

    public List<Oficinas> findEmpresaByRazaoSocial(String razaoSocial){
        return repository.findByrazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public Oficinas findEmpresaByCnpj(String cnpj){
        return repository.findByCnpj(cnpj).orElseThrow(() -> new DataNotFoundException("Empresa com CNPJ %s não foi encontrado".formatted(cnpj), "Empresas"));
    }

    public Oficinas atualizar(Integer id, Oficinas empresa){
        if (repository.existsById(id)){
            empresa.setIdEmpresa(id);
            Oficinas empSalva = repository.save(empresa);
            return empSalva;
        }
         throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Empresas");
    }

    public Oficinas patchEmail(OficinaPatchEmailDTO dto){
        Optional<Oficinas> empresa = repository.findById(dto.getId());

        if(empresa.isEmpty()){
            throw new DataNotFoundException("Não existe uma empresa com esse ID", "Empresas");
        }
        Oficinas emp = empresa.get();

        emp.setIdEmpresa(dto.getId());
        emp.setEmail(dto.getEmail());
        return repository.save(emp);
    }


    public Oficinas patchStatus(OficinaPatchStatusDTO dto){
        Optional<Oficinas> empresa = repository.findById(dto.getId());

        if(empresa.isEmpty()){
            throw new DataNotFoundException("Não existe uma empresa com esse ID", "Empresas");
        }
        Oficinas emp = empresa.get();

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
