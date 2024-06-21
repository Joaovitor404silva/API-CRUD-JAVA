package com.me.desafio.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/filiais")
public class FilialMock {

    @GetMapping
    public List<Map<String, String>> getAll() {
        return Arrays.asList(
            new LinkedHashMap<String, String>() {{
                put("id", "1");
                put("nome", "Filial A");
                put("cnpj", "12345678000100");
                put("cidade", "São Paulo");
                put("uf", "SP");
                put("tipo", "Matriz");
                put("ativo", "true");
                put("dataCadastro", "2022-01-01");
                put("ultimaAtualizacao", "2023-01-01");
            }},
            new LinkedHashMap<String, String>() {{
                put("id", "2");
                put("nome", "Filial B");
                put("cnpj", "12345678000200");
                put("cidade", "Rio de Janeiro");
                put("uf", "RJ");
                put("tipo", "Filial");
                put("ativo", "true");
                put("dataCadastro", "2022-02-01");
                put("ultimaAtualizacao", "2023-02-01");
            }}
        );
    }
}
