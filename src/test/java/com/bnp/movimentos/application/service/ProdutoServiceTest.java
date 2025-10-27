package com.bnp.movimentos.application.service;

import com.bnp.movimentos.domain.entity.Produto;
import com.bnp.movimentos.domain.entity.ProdutoCosif;
import com.bnp.movimentos.domain.repository.ProdutoCosifRepository;
import com.bnp.movimentos.domain.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - ProdutoService")
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoCosifRepository produtoCosifRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto1;
    private Produto produto2;
    private ProdutoCosif produtoCosif1;
    private ProdutoCosif produtoCosif2;

    @BeforeEach
    void setUp() {
        produto1 = Produto.builder()
                .codProduto("PROD001")
                .desProduto("Produto Teste 1")
                .status("A")
                .build();

        produto2 = Produto.builder()
                .codProduto("PROD002")
                .desProduto("Produto Teste 2")
                .status("A")
                .build();

        produtoCosif1 = ProdutoCosif.builder()
                .id(1L)
                .codProduto("PROD001")
                .codCosif("COSIF001")
                .codClassificacao("CLASS001")
                .status("A")
                .build();

        produtoCosif2 = ProdutoCosif.builder()
                .id(2L)
                .codProduto("PROD001")
                .codCosif("COSIF002")
                .codClassificacao("CLASS002")
                .status("A")
                .build();
    }

    @Test
    @DisplayName("Deve retornar lista de produtos ativos com sucesso")
    void deveRetornarListaDeProdutosAtivos_quandoChamadoListarAtivos() {
        List<Produto> produtosEsperados = Arrays.asList(produto1, produto2);
        when(produtoRepository.listarAtivos()).thenReturn(produtosEsperados);
        List<Produto> resultado = produtoService.listarAtivos();
        assertNotNull(resultado, "A lista de produtos não deve ser nula");
        assertEquals(2, resultado.size(), "A lista deve conter 2 produtos");
        assertEquals("PROD001", resultado.get(0).getCodProduto(), "O código do primeiro produto deve ser PROD001");
        assertEquals("PROD002", resultado.get(1).getCodProduto(), "O código do segundo produto deve ser PROD002");
        assertEquals("A", resultado.get(0).getStatus(), "O status do primeiro produto deve ser A");

        verify(produtoRepository, times(1)).listarAtivos();
        verifyNoMoreInteractions(produtoRepository);
        verifyNoInteractions(produtoCosifRepository);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver produtos ativos")
    void deveRetornarListaVazia_quandoNaoHouverProdutosAtivos() {
        when(produtoRepository.listarAtivos()).thenReturn(Collections.emptyList());
        List<Produto> resultado = produtoService.listarAtivos();

        assertNotNull(resultado, "A lista não deve ser nula");
        assertTrue(resultado.isEmpty(), "A lista deve estar vazia");
        assertEquals(0, resultado.size(), "O tamanho da lista deve ser 0");

        verify(produtoRepository, times(1)).listarAtivos();
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    @DisplayName("Deve retornar lista de produtos com único elemento")
    void deveRetornarListaComUnicoElemento_quandoHouverApenasProdutoAtivo() {

        List<Produto> produtosEsperados = Collections.singletonList(produto1);
        when(produtoRepository.listarAtivos()).thenReturn(produtosEsperados);


        List<Produto> resultado = produtoService.listarAtivos();


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("PROD001", resultado.get(0).getCodProduto());
        assertEquals("Produto Teste 1", resultado.get(0).getDesProduto());

        verify(produtoRepository, times(1)).listarAtivos();
    }

    @Test
    @DisplayName("Deve retornar lista de COSIFs para um produto específico")
    void deveRetornarListaDeCosifs_quandoChamadoListarCosifsComCodigoProdutoValido() {

        String codProduto = "PROD001";
        List<ProdutoCosif> cosifsEsperados = Arrays.asList(produtoCosif1, produtoCosif2);
        when(produtoCosifRepository.buscarPorProduto(codProduto)).thenReturn(cosifsEsperados);


        List<ProdutoCosif> resultado = produtoService.listarCosifs(codProduto);


        assertNotNull(resultado, "A lista de COSIFs não deve ser nula");
        assertEquals(2, resultado.size(), "A lista deve conter 2 COSIFs");
        assertEquals("COSIF001", resultado.get(0).getCodCosif(), "O código do primeiro COSIF deve ser COSIF001");
        assertEquals("COSIF002", resultado.get(1).getCodCosif(), "O código do segundo COSIF deve ser COSIF002");
        assertEquals(codProduto, resultado.get(0).getCodProduto(), "O código do produto deve corresponder");

        verify(produtoCosifRepository, times(1)).buscarPorProduto(codProduto);
        verifyNoMoreInteractions(produtoCosifRepository);
        verifyNoInteractions(produtoRepository);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando produto não possui COSIFs")
    void deveRetornarListaVazia_quandoProdutoNaoPossuiCosifs() {

        String codProduto = "PROD999";
        when(produtoCosifRepository.buscarPorProduto(codProduto)).thenReturn(Collections.emptyList());

        List<ProdutoCosif> resultado = produtoService.listarCosifs(codProduto);


        assertNotNull(resultado, "A lista não deve ser nula");
        assertTrue(resultado.isEmpty(), "A lista deve estar vazia");
        assertEquals(0, resultado.size(), "O tamanho da lista deve ser 0");

        verify(produtoCosifRepository, times(1)).buscarPorProduto(codProduto);
    }

    @Test
    @DisplayName("Deve retornar lista com único COSIF quando produto possui apenas um")
    void deveRetornarListaComUnicoCosif_quandoProdutoPossuiApenasUm() {

        String codProduto = "PROD001";
        List<ProdutoCosif> cosifsEsperados = Collections.singletonList(produtoCosif1);
        when(produtoCosifRepository.buscarPorProduto(codProduto)).thenReturn(cosifsEsperados);


        List<ProdutoCosif> resultado = produtoService.listarCosifs(codProduto);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("COSIF001", resultado.get(0).getCodCosif());
        assertEquals("CLASS001", resultado.get(0).getCodClassificacao());

        verify(produtoCosifRepository, times(1)).buscarPorProduto(codProduto);
    }

    @Test
    @DisplayName("Deve validar que os COSIFs retornados pertencem ao produto solicitado")
    void deveValidarQueCosifsPertencemAoProdutoSolicitado() {
        String codProduto = "PROD001";
        List<ProdutoCosif> cosifsEsperados = Arrays.asList(produtoCosif1, produtoCosif2);
        when(produtoCosifRepository.buscarPorProduto(codProduto)).thenReturn(cosifsEsperados);

        List<ProdutoCosif> resultado = produtoService.listarCosifs(codProduto);

        assertThat(resultado)
                .isNotNull()
                .hasSize(2)
                .allMatch(cosif -> cosif.getCodProduto().equals(codProduto),
                        "Todos os COSIFs devem pertencer ao produto " + codProduto)
                .allMatch(cosif -> cosif.getStatus().equals("A"),
                        "Todos os COSIFs devem estar ativos");

        verify(produtoCosifRepository, times(1)).buscarPorProduto(codProduto);
    }

    @Test
    @DisplayName("Deve verificar que produtos ativos têm status correto")
    void deveVerificarQueProdutosAtivosTemStatusCorreto() {
        List<Produto> produtosEsperados = Arrays.asList(produto1, produto2);
        when(produtoRepository.listarAtivos()).thenReturn(produtosEsperados);

        List<Produto> resultado = produtoService.listarAtivos();

        assertThat(resultado)
                .isNotNull()
                .hasSize(2)
                .allMatch(produto -> produto.getStatus().equals("A"),
                        "Todos os produtos devem ter status Ativo")
                .extracting(Produto::getCodProduto)
                .containsExactly("PROD001", "PROD002");

        verify(produtoRepository, times(1)).listarAtivos();
    }
}