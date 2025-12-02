package geo.track.service;

import geo.track.domain.Produtos;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ProdutosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ProdutosService")
class ProdutosServiceTest {

    @Mock
    private ProdutosRepository repository;

    @InjectMocks
    private ProdutosService service;

    private Produtos produto;
    private RequestPatchQtdEstoque patchQtdEstoque;
    private RequestPatchPrecoCompra patchPrecoCompra;
    private RequestPatchPrecoVenda patchPrecoVenda;

    @BeforeEach
    void setUp() {
        produto = new Produtos();
        produto.setIdProduto(1);
        produto.setNome("Filtro de Óleo");
        produto.setQuantidadeEstoque(50);
        produto.setPrecoCompra(18.00);
        produto.setPrecoVenda(30.00);

        patchQtdEstoque = new RequestPatchQtdEstoque(1, 50);
        patchPrecoCompra = new RequestPatchPrecoCompra(1, 18.00);
        patchPrecoVenda = new RequestPatchPrecoVenda(1, 30.00);
    }

    // --- Testes para cadastrar ---
    @Test
    @DisplayName("cadastrar: Deve cadastrar um produto com sucesso")
    void deveCadastrarProdutoComSucesso() {
        when(repository.save(any(Produtos.class))).thenReturn(produto);
        Produtos resultado = service.cadastrar(new Produtos());
        assertNotNull(resultado);
        verify(repository).save(any(Produtos.class));
    }

    // --- Testes para listar ---
    @Test
    @DisplayName("listar: Deve retornar uma lista de produtos")
    void deveRetornarListaDeProdutos() {
        when(repository.findAll()).thenReturn(List.of(produto));
        List<Produtos> resultado = service.listar();
        assertFalse(resultado.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("listar: Deve retornar uma lista vazia quando não houver produtos")
    void deveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Produtos> resultado = service.listar();
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // --- Testes para findProdutoById ---
    @Test
    @DisplayName("findProdutoById: Deve encontrar produto por ID com sucesso")
    void deveEncontrarProdutoPorIdComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(produto));
        Produtos resultado = service.findProdutoById(1);
        assertNotNull(resultado);
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findProdutoById: Deve lançar DataNotFoundException para ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.findProdutoById(99));
        verify(repository).findById(99);
    }

    // --- Testes para putProdutos ---
    @Test
    @DisplayName("putProdutos: Deve atualizar um produto com sucesso")
    void deveAtualizarProdutoComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Produtos.class))).thenReturn(produto);
        Produtos resultado = service.putProdutos(1, produto);
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdProduto());
        verify(repository).existsById(1);
        verify(repository).save(produto);
    }

    @Test
    @DisplayName("putProdutos: Deve lançar DataNotFoundException para ID inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        when(repository.existsById(99)).thenReturn(false);
        assertThrows(DataNotFoundException.class, () -> service.putProdutos(99, new Produtos()));
        verify(repository).existsById(99);
        verify(repository, never()).save(any(Produtos.class));
    }

    // --- Testes para patchQtdEstoque ---
    @Test
    @DisplayName("patchQtdEstoque: Deve atualizar a quantidade em estoque com sucesso")
    void deveAtualizarQtdEstoqueComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produtos.class))).thenAnswer(inv -> inv.getArgument(0));
        Produtos resultado = service.patchQtdEstoque(patchQtdEstoque);
        assertEquals(patchQtdEstoque.getQuantidadeEstoque(), resultado.getQuantidadeEstoque());
        verify(repository).findById(1);
        verify(repository).save(any(Produtos.class));
    }

    // --- Testes para patchPrecoCompra ---
    @Test
    @DisplayName("patchPrecoCompra: Deve atualizar o preço de compra com sucesso")
    void deveAtualizarPrecoCompraComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produtos.class))).thenAnswer(inv -> inv.getArgument(0));
        Produtos resultado = service.patchPrecoCompra(patchPrecoCompra);
        assertEquals(patchPrecoCompra.getPrecoCompra(), resultado.getPrecoCompra());
        verify(repository).findById(1);
        verify(repository).save(any(Produtos.class));
    }

    // --- Testes para patchPrecoVenda ---
    @Test
    @DisplayName("patchPrecoVenda: Deve atualizar o preço de venda com sucesso")
    void deveAtualizarPrecoVendaComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produtos.class))).thenAnswer(inv -> inv.getArgument(0));
        Produtos resultado = service.patchPrecoVenda(patchPrecoVenda);
        assertEquals(patchPrecoVenda.getPrecoVenda(), resultado.getPrecoVenda());
        verify(repository).findById(1);
        verify(repository).save(any(Produtos.class));
    }

    // --- Testes para excluir ---
    @Test
    @DisplayName("excluir: Deve excluir um produto com sucesso")
    void deveExcluirProdutoComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);
        assertDoesNotThrow(() -> service.excluir(1));
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("excluir: Deve lançar DataNotFoundException ao tentar excluir produto inexistente")
    void deveLancarExcecaoAoExcluirProdutoInexistente() {
        when(repository.existsById(99)).thenReturn(false);
        assertThrows(DataNotFoundException.class, () -> service.excluir(99));
        verify(repository).existsById(99);
        verify(repository, never()).deleteById(anyInt());
    }
}