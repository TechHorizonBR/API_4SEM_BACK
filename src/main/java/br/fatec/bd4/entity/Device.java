package br.fatec.bd4.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.ViewFilterUserDevice.class)
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "mac_address")
    private String macAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Device(String codigo, String macAddress, Usuario usuario){
        this.codigo = codigo;
        this.macAddress = macAddress;
        this.usuario = usuario;
    }
}
