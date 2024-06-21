package com.me.desafio.controller;

import com.me.desafio.model.Vendedor;
import com.me.desafio.service.FilialService;
import com.me.desafio.service.VendedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import java.util.Optional;


public class VendedorControllerTest {

    @Mock
    private VendedorService service;

    @Mock
    private FilialService filialService;

    @InjectMocks
    private VendedorController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateVendedorValido() throws Exception {
        Vendedor vendedor = new Vendedor("Gandalf the white", "12345678901", "Gandalf@middleearth.com", "01-01-1990", "CLT", "1");
        vendedor.setMatricula("00000001-CLT");
        when(service.save(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Gandalf the white\",\"documento\":\"12345678901\",\"email\":\"Gandalf@middleearth.com\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"CLT\",\"idFilial\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Gandalf the white")))
                .andExpect(jsonPath("$.matricula", is("00000001-CLT")));
    }
    
    @Test
    public void testCreateVendedorDocumentoInvalido() throws Exception {
        Vendedor vendedor = new Vendedor("Gandalf the white", "", "Gandalf@middleearth.com", "01-01-1990", "CLT", "1");
        vendedor.setMatricula("00000001-CLT"); 
        when(service.save(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Gandalf the white\",\"documento\":\"\",\"email\":\"Gandalf@middleearth.com\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"CLT\",\"idFilial\":\"1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Documento é obrigatório")));
    }


    @Test
    public void testCreateVendedorNomeInvalido() throws Exception {
        Vendedor vendedor = new Vendedor("", "12345678901", "Gandalf@middleearth.com", "01-01-1990", "CLT", "1");
        vendedor.setMatricula("00000001-CLT"); 
        when(service.save(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"\",\"documento\":\"12345678901\",\"email\":\"Gandalf@middleearth.com\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"CLT\",\"idFilial\":\"1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Nome é obrigatório")));
    }

    @Test
    public void testCreateVendedorEmailInvalido() throws Exception {
        Vendedor vendedor = new Vendedor("Gandalf the white", "12345678901", "", "01-01-1990", "CLT", "1");
        vendedor.setMatricula("00000001-CLT"); 
        when(service.save(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Gandalf the white\",\"documento\":\"12345678901\",\"email\":\"\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"CLT\",\"idFilial\":\"1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Email é obrigatório")));
    }

    @Test
    public void testCreateVendedorIdFilialInvalido() throws Exception {
        Vendedor vendedor = new Vendedor("Gandalf the white", "12345678901", "Gandalf@middleearth.com", "01-01-1990", "CLT", "");
        vendedor.setMatricula("00000001-CLT"); 
        when(service.save(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Gandalf the white\",\"documento\":\"12345678901\",\"email\":\"Gandalf@middleearth.com\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"CLT\",\"idFilial\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Filial é obrigatória")));
    }

    @Test
    public void testCreateVendedorTipoContratacaoInvalido() throws Exception {
        Vendedor vendedor = new Vendedor("Gandalf the white", "12345678901", "Gandalf@middleearth.com", "01-01-1990", "", "1");
        when(service.save(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Gandalf the white\",\"documento\":\"12345678901\",\"email\":\"Gandalf@middleearth.com\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"\",\"idFilial\":\"1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Tipo de contrataçao é obrigatório")));
    }

    @Test
    public void testGetVendedorIdSuccesso() throws Exception {
        Long validId = 1L;
        Vendedor vendedor = new Vendedor();
        vendedor.setId(validId);
        vendedor.setNome("Gandalf the white");
        when(service.findById(validId)).thenReturn(Optional.of(vendedor));

        mockMvc.perform(get("/vendedores/{id}", validId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(validId.intValue())))
                .andExpect(jsonPath("$.nome", is("Gandalf the white")));
    }

    @Test
    public void testGetVendedorIdFalha() throws Exception {
        Long invalidId = 999L;
        when(service.findById(invalidId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/vendedores/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateVendedorSuccesso() throws Exception {
    Long validId = 1L;
    Vendedor existingVendedor = new Vendedor();
    existingVendedor.setId(validId);
    existingVendedor.setNome("Gandalf the white");

    Vendedor updatedVendedor = new Vendedor();
    updatedVendedor.setNome("Gandalf the white");

    when(service.findById(validId)).thenReturn(Optional.of(existingVendedor));
    when(service.save(any(Vendedor.class))).thenReturn(updatedVendedor);

    mockMvc.perform(put("/vendedores/{id}", validId)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nome\":\"Gandalf the white\",\"documento\":\"12345678901\",\"email\":\"Gandalf@middleearth.com\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"CLT\",\"idFilial\":\"1\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome", is("Gandalf the white")));
}

    @Test
    public void testUpdateVendedorIdInvalidoFalha() throws Exception {
        Long invalidId = 999L;
        Vendedor updatedVendedor = new Vendedor();
        updatedVendedor.setNome("Gandalf the white");

        when(service.findById(invalidId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/vendedores/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Gandalf the white\",\"documento\":\"12345678901\",\"email\":\"Gandalf@middleearth.com\",\"dataNascimento\":\"01-01-1990\",\"tipoContratacao\":\"CLT\",\"idFilial\":\"1\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteVendedorSuccesso() throws Exception {
        Long vendedorId = 1L;
        Vendedor vendedor = new Vendedor();
        vendedor.setId(vendedorId);

        when(service.findById(vendedorId)).thenReturn(Optional.of(vendedor));
        doNothing().when(service).deleteById(vendedorId);

        mockMvc.perform(delete("/vendedores/{id}", vendedorId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteVendedorIdFalha() throws Exception {
        mockMvc.perform(delete("/vendedores/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testDeleteVendedorIdInvalidoFalha() throws Exception {
        Long invalidId = 999L;
        
        mockMvc.perform(delete("/vendedores/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
