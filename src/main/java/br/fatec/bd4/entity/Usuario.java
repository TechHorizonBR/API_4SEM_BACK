package br.fatec.bd4.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 200)
    private String nome;

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Device device;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @JsonManagedReference
    private List<Registro> registros;

    public Usuario(String nome){
        this.nome = nome;
    }

}
