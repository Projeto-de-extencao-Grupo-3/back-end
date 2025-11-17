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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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

    public Oficinas cadastrar(Oficinas Oficinas){
        if (repository.findByCnpj(Oficinas.getCnpj()).isPresent()){
            throw new ConflictException("O CNPJ %s já está cadastrado!".formatted(Oficinas.getCnpj()), "Oficinas");
        }
        String senhaCriptografada = passwordEncoder.encode(Oficinas.getSenha());
        Oficinas.setSenha(senhaCriptografada);
        return repository.save(Oficinas);
    }

    public UsuarioTokenDto autenticar(Oficinas Oficinas) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                Oficinas.getCnpj(), Oficinas.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Oficinas usuarioAutenticado =
                repository.findByCnpj(Oficinas.getCnpj())
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

    public Oficinas findOficinasById(Integer id){
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("Oficina com ID %d não encontrado".formatted(id), "Oficinas"));
    }

    public List<Oficinas> findOficinasByRazaoSocial(String razaoSocial){
        return repository.findByrazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public Oficinas findOficinasByCnpj(String cnpj){
        return repository.findByCnpj(cnpj).orElseThrow(() -> new DataNotFoundException("Oficina com CNPJ %s não foi encontrado".formatted(cnpj), "Oficinas"));
    }

    public Oficinas atualizar(Integer id, Oficinas oficinas){
        if (repository.existsById(id)){
            oficinas.setIdOficina(id);
            Oficinas empSalva = repository.save(oficinas);
            return empSalva;
        }
         throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Oficinas");
    }

    public Oficinas patchEmail(OficinaPatchEmailDTO dto){
        Optional<Oficinas> Oficinas = repository.findById(dto.getId());

        if(Oficinas.isEmpty()){
            throw new DataNotFoundException("Não existe uma oficina com esse ID", "Oficinas");
        }
        Oficinas emp = Oficinas.get();

        emp.setIdOficina(dto.getId());
        emp.setEmail(dto.getEmail());
        return repository.save(emp);
    }


    public Oficinas patchStatus(OficinaPatchStatusDTO dto){
        Optional<Oficinas> Oficinas = repository.findById(dto.getId());

        if(Oficinas.isEmpty()){
            throw new DataNotFoundException("Não existe uma oficina com esse ID", "Oficinas");
        }
        Oficinas emp = Oficinas.get();

        emp.setIdOficina(dto.getId());
        emp.setStatus(dto.getStatus());
        return repository.save(emp);
    }

    public void remover(Integer id){
        if (!repository.existsById(id)){
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Oficinas");
        }
        repository.deleteById(id);
    }
}
