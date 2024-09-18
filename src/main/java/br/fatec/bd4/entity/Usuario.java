package br.fatec.bd4.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.ViewFilterUserDevice.class)
    @Column(name = "nome", length = 200)
    private String nome;

    @JsonView(View.ViewFilterUserDevice.class)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario")
    private Device device;

}
