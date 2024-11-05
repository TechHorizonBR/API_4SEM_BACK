package br.fatec.bd4.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Polygon;

import lombok.Data;

@Data
@Entity
public class Demarcacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "ESPACO_DEMARCADO", columnDefinition = "SDO_GEOMETRY")
    private Polygon espaco_geometrico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
