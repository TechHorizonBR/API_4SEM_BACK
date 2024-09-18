package br.fatec.bd4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dataHora")
    private LocalDateTime dataHora = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id") // DEVICE
    private Device device;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "local_id") // LOCAL
    private Local local;
}
