package geo.track.service;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.domain.Funcionario;
import geo.track.domain.Oficinas;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.OficinaExceptionMessages; // Corrected import
import geo.track.repository.FuncionarioRepository;
import geo.track.repository.OficinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException; // Keep this import if ResponseStatusException is used elsewhere

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OficinaService {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final GerenciadorTokenJwt GERENCIADOR_TOKEN_JWT;
    private final AuthenticationManager AUTHENTICATION_MANAGER;
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;

    public Oficinas cadastrar(Oficinas Oficinas){
        if (OFICINA_REPOSITORY.findByCnpj(Oficinas.getCnpj()).isPresent()){
            throw new ConflictException(String.format(OficinaExceptionMessages.CNPJ_JA_CADASTRADO, Oficinas.getCnpj()), EnumDomains.OFICINA);
        }
        return OFICINA_REPOSITORY.save(Oficinas);
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto body) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                body.getEmail(), body.getSenha());

        final Authentication authentication = this.AUTHENTICATION_MANAGER.authenticate(credentials);

        Funcionario funcionarioAutenticado =
                FUNCIONARIO_REPOSITORY.findByEmail(body.getEmail())
                        .orElseThrow(
                                () -> new DataNotFoundException(OficinaExceptionMessages.EMAIL_NAO_CADASTRADO, EnumDomains.FUNCIONARIO) // Changed to DataNotFoundException
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = GERENCIADOR_TOKEN_JWT.generateToken(authentication, funcionarioAutenticado);

        return UsuarioMapper.of(funcionarioAutenticado, token);
    }

    public List<Oficinas> listar(){
        return OFICINA_REPOSITORY.findAll();
    }

    public Oficinas findOficinasById(Integer id){
        return OFICINA_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_ID, id), EnumDomains.OFICINA));
    }

    public List<Oficinas> findOficinasByRazaoSocial(String razaoSocial){
        return OFICINA_REPOSITORY.findByrazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public Oficinas findOficinasByCnpj(String cnpj){
        return OFICINA_REPOSITORY.findByCnpj(cnpj).orElseThrow(() -> new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_CNPJ, cnpj), EnumDomains.OFICINA));
    }

    public Oficinas atualizar(Integer id, Oficinas oficinas){
        if (OFICINA_REPOSITORY.existsById(id)){
            oficinas.setIdOficina(id);
            Oficinas empSalva = OFICINA_REPOSITORY.save(oficinas);
            return empSalva;
        }
         throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_ID, id), EnumDomains.OFICINA);
    }

    public Oficinas patchEmail(OficinaPatchEmailDTO dto){
        Optional<Oficinas> Oficinas = OFICINA_REPOSITORY.findById(dto.getId());

        if(Oficinas.isEmpty()){
            throw new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, EnumDomains.OFICINA);
        }
        Oficinas emp = Oficinas.get();

        emp.setIdOficina(dto.getId());
        emp.setEmail(dto.getEmail());
        return OFICINA_REPOSITORY.save(emp);
    }


    public Oficinas patchStatus(OficinaPatchStatusDTO dto){
        Optional<Oficinas> Oficinas = OFICINA_REPOSITORY.findById(dto.getId());

        if(Oficinas.isEmpty()){
            throw new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, EnumDomains.OFICINA);
        }
        Oficinas emp = Oficinas.get();

        emp.setIdOficina(dto.getId());
        emp.setStatus(dto.getStatus());
        return OFICINA_REPOSITORY.save(emp);
    }

    public void remover(Integer id){
        if (!OFICINA_REPOSITORY.existsById(id)){
            throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_ID, id), EnumDomains.OFICINA);
        }
        OFICINA_REPOSITORY.deleteById(id);
    }
}
