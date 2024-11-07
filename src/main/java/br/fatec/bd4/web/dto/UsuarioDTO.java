package br.fatec.bd4.web.dto;

public class UsuarioDTO {
    private Long id;
    private String nome;
    // outros atributos

    // Construtores, getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Exemplo de implementação de isEmpty
    public boolean isEmpty() {
        return id == null && (nome == null || nome.isEmpty());
    }
}
