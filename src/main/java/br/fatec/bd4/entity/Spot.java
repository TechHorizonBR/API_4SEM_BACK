package br.fatec.bd4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "latitude", nullable = false, precision = 10)
    private Double lat;

    @Column(name = "longitude", nullable = false, precision = 12, scale = 10)
    private Double lng;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "device", nullable = false)
    private String device;

    @Column(name = "localName", nullable = false)
    private String localName;

    @Column(name = "macAdress", nullable = false)
    private String macAdress;

}
