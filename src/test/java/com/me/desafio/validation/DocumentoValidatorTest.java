package com.me.desafio.validation;

import com.me.desafio.model.Vendedor;
import com.me.desafio.service.VendedorService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;


import org.junit.jupiter.api.BeforeEach;

public class DocumentoValidatorTest {

    @Mock
    private VendedorService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Vendedor createVendedor(String documento, String tipoContratacao) {
        Vendedor vendedor = new Vendedor();
        vendedor.setDocumento(documento);
        vendedor.setTipoContratacao(tipoContratacao);
        return vendedor;
    }

    @Test
    public void testCPFValido() {
        Vendedor vendedor = createVendedor("12345678901", "CLT");
        assertDoesNotThrow(() -> service.save(vendedor));
    }

    @Test
    public void testCPFInvalido() {
        Vendedor vendedor = createVendedor("1234567890", "CLT");
        doThrow(new IllegalArgumentException("Documento inválido para Pessoa Física")).when(service).save(vendedor);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(vendedor);
        });
        assertEquals("Documento inválido para Pessoa Física", exception.getMessage());
    }

    @Test
    public void testCNPJValido() {
        Vendedor vendedor = createVendedor("12345678000100", "PJ");
        assertDoesNotThrow(() -> service.save(vendedor));
    }

    @Test
    public void testCNPJInvalido() {
        Vendedor vendedor = createVendedor("1234567800010", "PJ");
        doThrow(new IllegalArgumentException("Documento inválido para Pessoa Jurídica")).when(service).save(vendedor);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(vendedor);
        });
        assertEquals("Documento inválido para Pessoa Jurídica", exception.getMessage());
    }


    @Test
    public void testDocumentoNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Vendedor vendedor = new Vendedor();
            vendedor.setDocumento(null); // Isso deve lançar a exceção
        });
        assertEquals("Documento é obrigatório", exception.getMessage());
    }

    @Test
    public void testTipoContratacaoInvalido() {
        Vendedor vendedor = new Vendedor();
        vendedor.setDocumento("12345678901");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendedor.setTipoContratacao(null);
        });
        assertEquals("Tipo de contrataçao é obrigatório", exception.getMessage());
    }

}
