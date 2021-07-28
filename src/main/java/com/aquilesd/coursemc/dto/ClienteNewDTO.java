package com.aquilesd.coursemc.dto;

import com.aquilesd.coursemc.services.validation.ClienteInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteInsert
public class ClienteNewDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    @NotEmpty(message = "Nome obrigatório")
    @Length(min = 4, max = 200, message = "O tamanho deve ser entre 4 e 200 caracteres")
    private String nome;

    @NotEmpty(message = "E-email Obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotEmpty(message = "Senha Obrigatória!")
    private String senha;

    @NotEmpty(message = "Preenchimento obrigátorio")
    private String cpfOuCnpj;

    private Integer tipo;

    @NotEmpty(message = "Preenchimento obrigátorio")
    private String logradouro;

    @NotEmpty(message = "Preenchimento obrigátorio")
    private String numero;

    private String complemento;
    private String bairro;

    @NotEmpty(message = "Preenchimento obrigátorio")
    private String cep;

    @NotEmpty(message = "Preenchimento obrigátorio")
    private String telefone1;

    private String telefone2;
    private String telefone3;

    private Integer cidadeId;


    public ClienteNewDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }
}
