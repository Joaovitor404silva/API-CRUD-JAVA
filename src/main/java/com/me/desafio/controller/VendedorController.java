package com.me.desafio.controller;

import com.me.desafio.dto.VendedorDTO;
import com.me.desafio.model.Vendedor;
import com.me.desafio.service.VendedorService;
import com.me.desafio.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService service;

    @Autowired
    private FilialService filialService;

    @PostMapping
    public ResponseEntity<Vendedor> create(@Valid @RequestBody Vendedor vendedor) {
        return ResponseEntity.ok(service.save(vendedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> updateById(@PathVariable Long id, @Valid @RequestBody Vendedor updatedVendedor) {
        Optional<Vendedor> optionalVendedor = service.findById(id);
        if (optionalVendedor.isPresent()) {
            Vendedor vendedor = optionalVendedor.get();
            vendedor.setNome(updatedVendedor.getNome());
            vendedor.setDocumento(updatedVendedor.getDocumento());
            vendedor.setEmail(updatedVendedor.getEmail());
             if (updatedVendedor.getDataNascimento() != null) {
            vendedor.setDataNascimento(updatedVendedor.getDataNascimento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            } else {
            vendedor.setDataNascimento((String) null);
            }
            vendedor.setTipoContratacao(updatedVendedor.getTipoContratacao());
            vendedor.setIdFilial(updatedVendedor.getIdFilial());
            return ResponseEntity.ok(service.save(vendedor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> getAll() {
        List<Vendedor> vendedores = service.findAll();
        List<VendedorDTO> vendedoresDTO = vendedores.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(vendedoresDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(vendedor -> {
                    VendedorDTO vendedorDTO = convertToDTO(vendedor);
                    return ResponseEntity.ok(vendedorDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    private VendedorDTO convertToDTO(Vendedor vendedor) {
        VendedorDTO vendedorDTO = new VendedorDTO();
        vendedorDTO.setId(vendedor.getId());
        vendedorDTO.setMatricula(vendedor.getMatricula());
        vendedorDTO.setNome(vendedor.getNome());
        vendedorDTO.setDataNascimento(vendedor.getDataNascimento());
        vendedorDTO.setDocumento(vendedor.getDocumento());
        vendedorDTO.setEmail(vendedor.getEmail());
        vendedorDTO.setTipoContratacao(vendedor.getTipoContratacao());
        vendedorDTO.setFilial(filialService.getFilialById(vendedor.getIdFilial()));
        return vendedorDTO;
    }
}
