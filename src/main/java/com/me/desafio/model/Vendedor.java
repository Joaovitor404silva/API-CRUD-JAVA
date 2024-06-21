package com.me.desafio.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = ".*(OUT|CLT|PJ)$", message = "Matrícula deve terminar com OUT, CLT, ou PJ")
    private String matricula;

    @NotBlank
    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    @NotBlank
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String tipoContratacao;

    @NotBlank
    private String idFilial;

    public Vendedor() {}

    public Vendedor(String nome, String documento, String email, String dataNascimento, String tipoContratacao, String idFilial) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.dataNascimento = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.tipoContratacao = tipoContratacao.toUpperCase();
        this.idFilial = idFilial;
    }

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
        if (nome == null || nome == ""){
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        else{
            this.nome = nome;
        }
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        if (dataNascimento == null || dataNascimento == ""){
            this.dataNascimento = null;
        }
        else{
            this.dataNascimento = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        if (documento == null || documento == ""){
            throw new IllegalArgumentException("Documento é obrigatório");
        }
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email == ""){
            throw new IllegalArgumentException("Email é obrigatório");
        }
        this.email = email;
    }

    public String getTipoContratacao() {
        return tipoContratacao;
    }

    public void setTipoContratacao(String tipoContratacao) {
        if (tipoContratacao == null || tipoContratacao == ""){
            throw new IllegalArgumentException("Tipo de contrataçao é obrigatório");
        }
        this.tipoContratacao = tipoContratacao;
    }

    public String getIdFilial() {
        return idFilial;
    }

    public void setIdFilial(String idFilial) {
        if (idFilial == null || idFilial == ""){
            throw new IllegalArgumentException("Filial é obrigatória");
        }
        this.idFilial = idFilial;
    }
}
