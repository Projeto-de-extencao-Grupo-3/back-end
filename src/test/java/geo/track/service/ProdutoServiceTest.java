package geo.track.service;

import geo.track.domain.Produto;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ProdutoRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ProdutoService")
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto;
    private RequestPatchQtdEstoque patchQtdEstoque;
    private RequestPatchPrecoCompra patchPrecoCompra;
    private RequestPatchPrecoVenda patchPrecoVenda;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidade
        produto = new Produto();
        produto.setIdProduto(1);
        produto.setNome("Filtro de Óleo");
        produto.setQuantidadeEstoque(50);
        produto.setPrecoCompra(18.00);
        produto.setPrecoVenda(30.00);

        patchQtdEstoque = new RequestPatchQtdEstoque(1, 50);
        patchPrecoCompra = new RequestPatchPrecoCompra(1, 20.00); // Ajuste: Alinhado com o valor esperado no teste (20.0)
        patchPrecoVenda = new RequestPatchPrecoVenda(1, 35.00); // Ajuste: Alinhado com o valor esperado no teste (35.0)
    }

    // ===== cadastrar =====
    @Test
    @DisplayName("cadastrar: Deve cadastrar novo produto com sucesso")
    void testCadastrarProduto() {
        // Arrange
        Produto novoProduto = produto;
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Produto resultado = service.cadastrar(novoProduto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Filtro de Óleo", resultado.getNome());
        verify(repository).save(any(Produto.class));
    }

    // ===== listar =====
    @Test
    @DisplayName("listar: Deve retornar lista de produtos quando existem")
    void testListarProdutos() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(produto));

        // Act
        List<Produto> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("listar: Deve retornar lista vazia quando não existem produtos")
    void testListarProdutos_Vazio() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<Produto> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // ===== findProdutoById =====
    @Test
    @DisplayName("findProdutoById: Deve encontrar produto por ID com sucesso")
    void testFindProdutoById() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(produto));

        // Act
        Produto resultado = service.findProdutoById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdProduto());
        assertEquals("Filtro de Óleo", resultado.getNome());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findProdutoById: Deve lançar DataNotFoundException quando ID não existe")
    void testFindProdutoById_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.findProdutoById(999));

        verify(repository).findById(999);
    }

    // ===== putProdutos =====
    @Test
    @DisplayName("putProdutos: Deve atualizar produto com sucesso quando existe")
    void testPutProdutos() {
        // Arrange
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setIdProduto(1);
        produtoAtualizado.setNome("Filtro de Óleo Sintético");
        produtoAtualizado.setPrecoVenda(35.00);
        
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Produto resultado = service.putProdutos(1, produtoAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Filtro de Óleo Sintético", resultado.getNome());
        assertEquals(35.00, resultado.getPrecoVenda());
        verify(repository).existsById(1);
        verify(repository).save(any(Produto.class));
    }

    @Test
    @DisplayName("putProdutos: Deve lançar DataNotFoundException quando produto não existe")
    void testPutProdutos_NaoEncontrado() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.putProdutos(999, produto));

        verify(repository).existsById(999);
        verify(repository, never()).save(any(Produto.class));
    }

    // ===== patchQtdEstoque =====
    @Test
    @DisplayName("patchQtdEstoque: Deve atualizar quantidade em estoque com sucesso")
    void testPatchQtdEstoque() {
        // Arrange
        // Mock precisa retornar o produto original antes da alteração
        when(repository.findById(patchQtdEstoque.getId())).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Produto resultado = service.patchQtdEstoque(patchQtdEstoque);

        // Assert
        assertNotNull(resultado);
        assertEquals(50, resultado.getQuantidadeEstoque());
        verify(repository).findById(patchQtdEstoque.getId());
        verify(repository).save(any(Produto.class));
    }

    @Test
    @DisplayName("patchQtdEstoque: Deve lançar DataNotFoundException quando produto não existe")
    void testPatchQtdEstoque_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.patchQtdEstoque(new RequestPatchQtdEstoque(999, 100)));

        verify(repository).findById(999);
        verify(repository, never()).save(any(Produto.class));
    }

    // ===== patchPrecoCompra =====
    @Test
    @DisplayName("patchPrecoCompra: Deve atualizar preço de compra com sucesso")
    void testPatchPrecoCompra() {
        // Arrange
        // Importante: No teste, 'produto' tem preço 18.0. 'patchPrecoCompra' tem 20.0 (corrigido no setUp).
        // O service recupera o 'produto', atualiza com o valor do 'patch', e salva.
        when(repository.findById(patchPrecoCompra.getId())).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Produto resultado = service.patchPrecoCompra(patchPrecoCompra);

        // Assert
        assertNotNull(resultado);
        assertEquals(20.00, resultado.getPrecoCompra());
        verify(repository).findById(patchPrecoCompra.getId());
        verify(repository).save(any(Produto.class));
    }

    @Test
    @DisplayName("patchPrecoCompra: Deve lançar DataNotFoundException quando produto não existe")
    void testPatchPrecoCompra_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.patchPrecoCompra(new RequestPatchPrecoCompra(999, 25.00)));

        verify(repository).findById(999);
        verify(repository, never()).save(any(Produto.class));
    }

    // ===== patchPrecoVenda =====
    @Test
    @DisplayName("patchPrecoVenda: Deve atualizar preço de venda com sucesso")
    void testPatchPrecoVenda() {
        // Arrange
        // Importante: No teste, 'produto' tem preço 30.0. 'patchPrecoVenda' tem 35.0 (corrigido no setUp).
        when(repository.findById(patchPrecoVenda.getId())).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Produto resultado = service.patchPrecoVenda(patchPrecoVenda);

        // Assert
        assertNotNull(resultado);
        assertEquals(35.00, resultado.getPrecoVenda());
        verify(repository).findById(patchPrecoVenda.getId());
        verify(repository).save(any(Produto.class));
    }

    @Test
    @DisplayName("patchPrecoVenda: Deve lançar DataNotFoundException quando produto não existe")
    void testPatchPrecoVenda_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.patchPrecoVenda(new RequestPatchPrecoVenda(999, 40.00)));

        verify(repository).findById(999);
        verify(repository, never()).save(any(Produto.class));
    }

    // ===== excluir =====
    @Test
    @DisplayName("excluir: Deve excluir produto com sucesso quando existe")
    void testExcluirProduto() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        // Act
        assertDoesNotThrow(() -> service.excluir(1));

        // Assert
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("excluir: Deve lançar DataNotFoundException quando produto não existe")
    void testExcluirProduto_NaoEncontrado() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.excluir(999));

        verify(repository).existsById(999);
        verify(repository, never()).deleteById(anyInt());
    }
}
