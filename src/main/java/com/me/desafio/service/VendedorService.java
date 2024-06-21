package com.me.desafio.service;

import com.me.desafio.model.Vendedor;
import com.me.desafio.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository repository;

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");

    public Vendedor save(Vendedor vendedor) {
        validateDocumento(vendedor.getDocumento(), vendedor.getTipoContratacao());
        String original = vendedor.getTipoContratacao();

        if (vendedor.getMatricula() == null) {
            String prefix = switch (original.toUpperCase()) {
                case "OUTSOURCING" -> "OUT";
                case "CLT" -> "CLT";
                case "PESSOA JURÍDICA" -> "PJ";
                default -> throw new IllegalArgumentException("Tipo de contratação inválido");
            };
            String sequencial = String.format("%08d", repository.count() + 1); // Gera um sequencial de 8 dígitos
            vendedor.setMatricula(sequencial + "-" + prefix);
        }

        return repository.save(vendedor);
    }

    private void validateDocumento(String documento, String tipoContratacao) {
        if ("PESSOA JURÍDICA".equalsIgnoreCase(tipoContratacao) && !CNPJ_PATTERN.matcher(documento).matches()) {
            throw new IllegalArgumentException("Documento inválido para Pessoa Jurídica");
        }
        if (!"PESSOA JURÍDICA".equalsIgnoreCase(tipoContratacao) && !CPF_PATTERN.matcher(documento).matches()) {
            throw new IllegalArgumentException("Documento inválido para Pessoa Física");
        }
    }

    public List<Vendedor> findAll() {
        return repository.findAll();
    }

    public Optional<Vendedor> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
