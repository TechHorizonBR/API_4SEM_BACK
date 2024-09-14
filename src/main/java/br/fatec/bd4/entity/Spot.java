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
    private Long _id;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime CreatedAt = LocalDateTime.now();

    @Column(name = "lat", nullable = false, precision = 10)
    private Double lat;

    @Column(name = "long", nullable = false, precision = 12, scale = 10)
    private Double lng;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "code", nullable = false)
    private String code ;

    @Column(name = "location")
    private String location;

    @Column(name = "macAdress", nullable = false)
    private String macAdress;

    @Column(name = "codeDevice", nullable = false)
    private String codeDevice;

    @Column(name = "idDevice", nullable = false)
    private String idDevice;

}
