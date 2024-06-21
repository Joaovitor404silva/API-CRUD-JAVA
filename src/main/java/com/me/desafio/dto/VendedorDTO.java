package com.me.desafio.dto;

import java.time.LocalDate;
import java.util.Map;

public class VendedorDTO {

    private Long id;
    private String matricula;
    private String nome;

    private LocalDate dataNascimento;

    private String documento;
    private String email;
    private String tipoContratacao;
    private Map<String, String> filial;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoContratacao() {
        return tipoContratacao;
    }

    public void setTipoContratacao(String tipoContratacao) {
        this.tipoContratacao = tipoContratacao;
    }

    public Map<String, String> getFilial() {
        return filial;
    }

    public void setFilial(Map<String, String> filial) {
        this.filial = filial;
    }
}
