package com.me.desafio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.me.desafio.model.Vendedor;
import com.me.desafio.repository.VendedorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class VendedorServiceTest {

    @Mock
    private VendedorRepository repository;

    @InjectMocks
    private VendedorService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostVendedor() {
        Vendedor vendedor = new Vendedor("Gandalf", "12345678901", "GandalfGrey@middleearth.com", "01-01-1990", "CLT", "1");
        when(repository.count()).thenReturn(0L);
        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);

        Vendedor savedVendedor = service.save(vendedor);

        assertEquals("00000001-CLT", savedVendedor.getMatricula());
    }

    @Test
    public void testPostVendedorInvalidoTipoContratacao() {
        Vendedor vendedor = new Vendedor("Gandalf", "12345678901", "GandalfGrey@middleearth.com", "01-01-1990", "", "1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(vendedor);
        });

        assertEquals("Tipo de contratação inválido", exception.getMessage());
    }

    @Test
    public void testPostVendedorInvalidoDocumentoCLT() {
        Vendedor vendedor = new Vendedor("Gandalf", "1234567890", "GandalfGrey@middleearth.com", "01-01-1990", "CLT", "1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(vendedor);
        });

        assertEquals("Documento inválido para Pessoa Física", exception.getMessage());
    }

    @Test
    public void testPostVendedorInvalidoDocumentoPJ() {
        Vendedor vendedor = new Vendedor("Gandalf", "1234567890", "GandalfGrey@middleearth.com", "01-01-1990", "PESSOA JURÍDICA", "1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(vendedor);
        });

        assertEquals("Documento inválido para Pessoa Jurídica", exception.getMessage());
    }
}

