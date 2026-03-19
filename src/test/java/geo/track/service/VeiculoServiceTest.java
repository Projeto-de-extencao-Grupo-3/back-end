package geo.track.service;

import geo.track.domain.Cliente;
import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.dto.veiculos.request.RequestPutVeiculo;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.log.Log;
import geo.track.repository.ClienteRepository;
import geo.track.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do VeiculoService")
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private Log log;

    @InjectMocks
    private VeiculoService veiculoService;

    private Veiculo veiculo;
    private RequestPostVeiculo requestPostVeiculo;
    private RequestPutVeiculo requestPutVeiculo;
    private RequestPatchPlaca requestPatchPlaca;
    private RequestPatchCor requestPatchCor;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNome("Cliente Teste");

        veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);
        veiculo.setPlaca("ABC1234");
        veiculo.setMarca("Toyota");
        veiculo.setModelo("Corolla");
        veiculo.setPrefixo("TRK");
        veiculo.setAnoModelo(2023);
        veiculo.setFkCliente(cliente);

        requestPostVeiculo = new RequestPostVeiculo();
        requestPostVeiculo.setPlaca("ABC1234");
        requestPostVeiculo.setMarca("Toyota");
        requestPostVeiculo.setModelo("Corolla");
        requestPostVeiculo.setPrefixo("TRK");
        requestPostVeiculo.setAnoModelo(2023);
        requestPostVeiculo.setIdCliente(1);

        requestPutVeiculo = new RequestPutVeiculo();
        requestPutVeiculo.setMarca("Honda");
        requestPutVeiculo.setModelo("Civic");
        requestPutVeiculo.setPrefixo("TRK2");
        requestPutVeiculo.setAnoModelo(2024);

        requestPatchPlaca = new RequestPatchPlaca();
        requestPatchPlaca.setIdVeiculo(1);
        requestPatchPlaca.setPlaca("XYZ9876");

        requestPatchCor = new RequestPatchCor();
        requestPatchCor.setIdVeiculo(1);
        // Assuming RequestPatchCor would have a cor field, though not present in Veiculo currently.
        // Adjusting test to simply pass through the existing method logic.
    }

    @Test
    @DisplayName("Deve cadastrar veículo com sucesso")
    void cadastrar_ComSucesso() {
        when(veiculoRepository.existsByPlacaIgnoreCase(requestPostVeiculo.getPlaca())).thenReturn(false);
        when(clienteRepository.findById(requestPostVeiculo.getIdCliente())).thenReturn(Optional.of(cliente));
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo salvo = veiculoService.cadastrar(requestPostVeiculo);

        assertNotNull(salvo);
        assertEquals(veiculo.getPlaca(), salvo.getPlaca());
        verify(veiculoRepository).existsByPlacaIgnoreCase(anyString());
        verify(clienteRepository).findById(anyInt());
        verify(veiculoRepository).save(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar veículo com placa já existente")
    void cadastrar_PlacaExistente() {
        when(veiculoRepository.existsByPlacaIgnoreCase(requestPostVeiculo.getPlaca())).thenReturn(true);

        assertThrows(ConflictException.class, () -> veiculoService.cadastrar(requestPostVeiculo));
        verify(veiculoRepository).existsByPlacaIgnoreCase(anyString());
        verify(clienteRepository, never()).findById(anyInt());
        verify(veiculoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar veículo com cliente não encontrado")
    void cadastrar_ClienteNaoEncontrado() {
        when(veiculoRepository.existsByPlacaIgnoreCase(requestPostVeiculo.getPlaca())).thenReturn(false);
        when(clienteRepository.findById(requestPostVeiculo.getIdCliente())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> veiculoService.cadastrar(requestPostVeiculo));
        verify(veiculoRepository).existsByPlacaIgnoreCase(anyString());
        verify(clienteRepository).findById(anyInt());
        verify(veiculoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os veículos")
    void listar_ComSucesso() {
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo));

        List<Veiculo> veiculos = veiculoService.listar();

        assertFalse(veiculos.isEmpty());
        assertEquals(1, veiculos.size());
        verify(veiculoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar veículo por ID com sucesso")
    void findVeiculoById_ComSucesso() {
        when(veiculoRepository.findById(1)).thenReturn(Optional.of(veiculo));

        Veiculo encontrado = veiculoService.findVeiculoById(1);

        assertNotNull(encontrado);
        assertEquals(1, encontrado.getIdVeiculo());
        verify(veiculoRepository).findById(anyInt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar veículo por ID não existente")
    void findVeiculoById_NaoEncontrado() {
        when(veiculoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> veiculoService.findVeiculoById(1));
        verify(veiculoRepository).findById(anyInt());
    }

    @Test
    @DisplayName("Deve buscar veículo por placa com sucesso")
    void findVeiculoByPlaca_ComSucesso() {
        when(veiculoRepository.findAllByPlacaStartsWithIgnoreCase("ABC")).thenReturn(List.of(veiculo));

        List<Veiculo> encontrados = veiculoService.findVeiculoByPlaca("ABC");

        assertFalse(encontrados.isEmpty());
        assertEquals(1, encontrados.size());
        verify(veiculoRepository).findAllByPlacaStartsWithIgnoreCase(anyString());
    }

    @Test
    @DisplayName("Deve atualizar veículo com sucesso")
    void atualizarVeiculo_ComSucesso() {
        when(veiculoRepository.findById(1)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo atualizado = veiculoService.atualizarVeiculo(1, requestPutVeiculo);

        assertNotNull(atualizado);
        verify(veiculoRepository).findById(1);
        verify(veiculoRepository).save(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar veículo não existente")
    void atualizarVeiculo_NaoEncontrado() {
        when(veiculoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> veiculoService.atualizarVeiculo(1, requestPutVeiculo));
        verify(veiculoRepository).findById(1);
        verify(veiculoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar a placa do veículo com sucesso")
    void patchPlaca_ComSucesso() {
        when(veiculoRepository.findById(1)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.existsByPlacaIgnoreCase(requestPatchPlaca.getPlaca())).thenReturn(false);
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo atualizado = veiculoService.patchPlaca(requestPatchPlaca);

        assertNotNull(atualizado);
        verify(veiculoRepository).findById(1);
        verify(veiculoRepository).existsByPlacaIgnoreCase(anyString());
        verify(veiculoRepository).save(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar a placa para uma já existente")
    void patchPlaca_PlacaExistente() {
        when(veiculoRepository.findById(1)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.existsByPlacaIgnoreCase(requestPatchPlaca.getPlaca())).thenReturn(true);

        assertThrows(ConflictException.class, () -> veiculoService.patchPlaca(requestPatchPlaca));
        verify(veiculoRepository).findById(1);
        verify(veiculoRepository).existsByPlacaIgnoreCase(anyString());
        verify(veiculoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar a cor do veículo com sucesso")
    void patchCor_ComSucesso() {
        when(veiculoRepository.findById(1)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo atualizado = veiculoService.patchCor(requestPatchCor);

        assertNotNull(atualizado);
        verify(veiculoRepository).findById(1);
        verify(veiculoRepository).save(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve deletar veículo por ID com sucesso")
    void deleteVeiculoById_ComSucesso() {
        when(veiculoRepository.existsById(1)).thenReturn(true);
        doNothing().when(veiculoRepository).deleteById(1);

        veiculoService.deleteVeiculoById(1);

        verify(veiculoRepository).existsById(1);
        verify(veiculoRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar veículo não encontrado por ID")
    void deleteVeiculoById_NaoEncontrado() {
        when(veiculoRepository.existsById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> veiculoService.deleteVeiculoById(1));
        verify(veiculoRepository).existsById(1);
        verify(veiculoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve deletar veículo por placa com sucesso")
    void deleteVeiculoByPlaca_ComSucesso() {
        when(veiculoRepository.existsByPlacaIgnoreCase("ABC1234")).thenReturn(true);
        when(veiculoRepository.findAllByPlacaStartsWithIgnoreCase("ABC1234")).thenReturn(List.of(veiculo));
        doNothing().when(veiculoRepository).deleteById(1);

        veiculoService.deleteVeiculoByPlaca("ABC1234");

        verify(veiculoRepository).existsByPlacaIgnoreCase(anyString());
        verify(veiculoRepository).findAllByPlacaStartsWithIgnoreCase(anyString());
        verify(veiculoRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve retornar lista de veículos por ID do cliente")
    void findVeiculoByCliente_ComSucesso() {
        when(veiculoRepository.findAllByFkCliente_IdCliente(1)).thenReturn(List.of(veiculo));

        List<Veiculo> encontrados = veiculoService.findVeiculoByCliente(1);

        assertFalse(encontrados.isEmpty());
        assertEquals(1, encontrados.size());
        verify(veiculoRepository).findAllByFkCliente_IdCliente(1);
    }
}
