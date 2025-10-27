package com.bnp.movimentos.application.service;

import com.bnp.movimentos.domain.entity.MovimentoManual;
import com.bnp.movimentos.domain.repository.MovimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - MovimentoService")
class MovimentoServiceTest {

    @Mock
    private MovimentoRepository movimentoRepository;

    @InjectMocks
    private MovimentoService movimentoService;

    @Captor
    private ArgumentCaptor<MovimentoManual> movimentoCaptor;

    private MovimentoManual movimento1;
    private MovimentoManual movimento2;
    private MovimentoManual movimentoParaCriar;

    @BeforeEach
    void setUp() {
        movimento1 = MovimentoManual.builder()
                .id(1L)
                .mes(10)
                .ano(2025)
                .numLancamento(1L)
                .codProduto("PROD001")
                .codCosif("COSIF001")
                .descricao("Movimento Teste 1")
                .valor(BigDecimal.valueOf(1000.00))
                .codUsuario("USER001")
                .dataMovimento(LocalDateTime.of(2025, 10, 20, 10, 30))
                .build();

        movimento2 = MovimentoManual.builder()
                .id(2L)
                .mes(10)
                .ano(2025)
                .numLancamento(2L)
                .codProduto("PROD002")
                .codCosif("COSIF002")
                .descricao("Movimento Teste 2")
                .valor(BigDecimal.valueOf(2000.00))
                .codUsuario("USER002")
                .dataMovimento(LocalDateTime.of(2025, 10, 21, 14, 45))
                .build();

        movimentoParaCriar = MovimentoManual.builder()
                .mes(10)
                .ano(2025)
                .codProduto("PROD003")
                .codCosif("COSIF003")
                .descricao("Novo Movimento")
                .valor(BigDecimal.valueOf(500.00))
                .build();
    }

    @Test
    @DisplayName("Deve retornar lista de todos os movimentos com sucesso")
    void deveRetornarListaDeMovimentos_quandoChamadoListar() {
        List<MovimentoManual> movimentosEsperados = Arrays.asList(movimento1, movimento2);
        when(movimentoRepository.listarTodos()).thenReturn(movimentosEsperados);

        List<MovimentoManual> resultado = movimentoService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());
        assertEquals("PROD001", resultado.get(0).getCodProduto());
        assertEquals("PROD002", resultado.get(1).getCodProduto());
        verify(movimentoRepository, times(1)).listarTodos();
        verifyNoMoreInteractions(movimentoRepository);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver movimentos")
    void deveRetornarListaVazia_quandoNaoHouverMovimentos() {
        when(movimentoRepository.listarTodos()).thenReturn(Collections.emptyList());

        List<MovimentoManual> resultado = movimentoService.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.size());
        verify(movimentoRepository, times(1)).listarTodos();
    }

    @Test
    @DisplayName("Deve retornar lista com único movimento")
    void deveRetornarListaComUnicoMovimento_quandoHouverApenasUm() {
        List<MovimentoManual> movimentosEsperados = Collections.singletonList(movimento1);
        when(movimentoRepository.listarTodos()).thenReturn(movimentosEsperados);

        List<MovimentoManual> resultado = movimentoService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals("Movimento Teste 1", resultado.get(0).getDescricao());
        verify(movimentoRepository, times(1)).listarTodos();
    }

    @Test
    @DisplayName("Deve criar movimento com número de lançamento gerado automaticamente")
    void deveCriarMovimento_comNumeroLancamentoGerado() {
        Long proximoNumero = 5L;
        when(movimentoRepository.proximoNumeroLancamento(10, 2025)).thenReturn(proximoNumero);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoParaCriar);

        MovimentoManual resultado = movimentoService.criar(movimentoParaCriar);

        assertNotNull(resultado);
        verify(movimentoRepository, times(1)).proximoNumeroLancamento(10, 2025);
        verify(movimentoRepository, times(1)).salvar(movimentoCaptor.capture());

        MovimentoManual movimentoCapturado = movimentoCaptor.getValue();
        assertEquals(proximoNumero, movimentoCapturado.getNumLancamento());
        assertEquals("TESTE", movimentoCapturado.getCodUsuario());
        assertNotNull(movimentoCapturado.getDataMovimento());
    }

    @Test
    @DisplayName("Deve definir código de usuário como TESTE ao criar movimento")
    void deveDefinirCodigoUsuarioTeste_aoCriarMovimento() {
        when(movimentoRepository.proximoNumeroLancamento(anyInt(), anyInt())).thenReturn(1L);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoParaCriar);

        movimentoService.criar(movimentoParaCriar);

        verify(movimentoRepository).salvar(movimentoCaptor.capture());
        MovimentoManual movimentoCapturado = movimentoCaptor.getValue();
        assertEquals("TESTE", movimentoCapturado.getCodUsuario());
    }

    @Test
    @DisplayName("Deve definir data de movimento ao criar")
    void deveDefinirDataMovimento_aoCriar() {
        LocalDateTime antes = LocalDateTime.now();
        when(movimentoRepository.proximoNumeroLancamento(anyInt(), anyInt())).thenReturn(1L);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoParaCriar);

        movimentoService.criar(movimentoParaCriar);

        LocalDateTime depois = LocalDateTime.now();
        verify(movimentoRepository).salvar(movimentoCaptor.capture());
        MovimentoManual movimentoCapturado = movimentoCaptor.getValue();
        assertNotNull(movimentoCapturado.getDataMovimento());
        assertTrue(movimentoCapturado.getDataMovimento().isAfter(antes.minusSeconds(1)));
        assertTrue(movimentoCapturado.getDataMovimento().isBefore(depois.plusSeconds(1)));
    }

    @Test
    @DisplayName("Deve preservar dados originais do movimento ao criar")
    void devePreservarDadosOriginais_aoCriarMovimento() {
        when(movimentoRepository.proximoNumeroLancamento(10, 2025)).thenReturn(1L);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoParaCriar);

        movimentoService.criar(movimentoParaCriar);

        verify(movimentoRepository).salvar(movimentoCaptor.capture());
        MovimentoManual movimentoCapturado = movimentoCaptor.getValue();
        assertEquals(10, movimentoCapturado.getMes());
        assertEquals(2025, movimentoCapturado.getAno());
        assertEquals("PROD003", movimentoCapturado.getCodProduto());
        assertEquals("COSIF003", movimentoCapturado.getCodCosif());
        assertEquals("Novo Movimento", movimentoCapturado.getDescricao());
        assertEquals(BigDecimal.valueOf(500.00), movimentoCapturado.getValor());
    }

    @Test
    @DisplayName("Deve chamar proximoNumeroLancamento com mês e ano corretos")
    void deveChamarProximoNumeroLancamento_comMesAnoCorretos() {
        when(movimentoRepository.proximoNumeroLancamento(10, 2025)).thenReturn(1L);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoParaCriar);

        movimentoService.criar(movimentoParaCriar);

        verify(movimentoRepository, times(1)).proximoNumeroLancamento(10, 2025);
    }

    @Test
    @DisplayName("Deve retornar movimento salvo após criação")
    void deveRetornarMovimentoSalvo_aposCriacao() {
        MovimentoManual movimentoSalvo = MovimentoManual.builder()
                .id(10L)
                .mes(10)
                .ano(2025)
                .numLancamento(5L)
                .codProduto("PROD003")
                .codCosif("COSIF003")
                .descricao("Novo Movimento")
                .valor(BigDecimal.valueOf(500.00))
                .codUsuario("TESTE")
                .dataMovimento(LocalDateTime.now())
                .build();

        when(movimentoRepository.proximoNumeroLancamento(10, 2025)).thenReturn(5L);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoSalvo);

        MovimentoManual resultado = movimentoService.criar(movimentoParaCriar);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(5L, resultado.getNumLancamento());
        assertEquals("TESTE", resultado.getCodUsuario());
    }

    @Test
    @DisplayName("Deve criar movimento com número de lançamento 1 quando for o primeiro do mês")
    void deveCriarMovimento_comNumeroLancamento1_quandoForPrimeiro() {
        when(movimentoRepository.proximoNumeroLancamento(10, 2025)).thenReturn(1L);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoParaCriar);

        movimentoService.criar(movimentoParaCriar);

        verify(movimentoRepository).salvar(movimentoCaptor.capture());
        MovimentoManual movimentoCapturado = movimentoCaptor.getValue();
        assertEquals(1L, movimentoCapturado.getNumLancamento());
    }

    @Test
    @DisplayName("Deve criar movimentos com números sequenciais para mesmo mês e ano")
    void deveCriarMovimentos_comNumerosSequenciais_mesmoMesAno() {
        MovimentoManual movimento1 = MovimentoManual.builder()
                .mes(10)
                .ano(2025)
                .codProduto("PROD001")
                .codCosif("COSIF001")
                .descricao("Movimento 1")
                .valor(BigDecimal.valueOf(100.00))
                .build();

        MovimentoManual movimento2 = MovimentoManual.builder()
                .mes(10)
                .ano(2025)
                .codProduto("PROD002")
                .codCosif("COSIF002")
                .descricao("Movimento 2")
                .valor(BigDecimal.valueOf(200.00))
                .build();

        when(movimentoRepository.proximoNumeroLancamento(10, 2025))
                .thenReturn(1L)
                .thenReturn(2L);
        when(movimentoRepository.salvar(any(MovimentoManual.class)))
                .thenReturn(movimento1)
                .thenReturn(movimento2);

        movimentoService.criar(movimento1);
        movimentoService.criar(movimento2);

        verify(movimentoRepository, times(2)).proximoNumeroLancamento(10, 2025);
        verify(movimentoRepository, times(2)).salvar(any(MovimentoManual.class));
    }

    @Test
    @DisplayName("Deve validar que todos os campos obrigatórios são mantidos ao criar")
    void deveValidarCamposObrigatorios_aoCriar() {
        when(movimentoRepository.proximoNumeroLancamento(10, 2025)).thenReturn(3L);
        when(movimentoRepository.salvar(any(MovimentoManual.class))).thenReturn(movimentoParaCriar);

        movimentoService.criar(movimentoParaCriar);

        verify(movimentoRepository).salvar(movimentoCaptor.capture());
        MovimentoManual movimentoCapturado = movimentoCaptor.getValue();

        assertThat(movimentoCapturado)
                .isNotNull()
                .satisfies(m -> {
                    assertNotNull(m.getMes());
                    assertNotNull(m.getAno());
                    assertNotNull(m.getNumLancamento());
                    assertNotNull(m.getCodProduto());
                    assertNotNull(m.getCodCosif());
                    assertNotNull(m.getDescricao());
                    assertNotNull(m.getValor());
                    assertNotNull(m.getCodUsuario());
                    assertNotNull(m.getDataMovimento());
                });
    }
}