package br.fatec.bd4.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "pontos") // Nome da tabela no banco de dados
public class Ponto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id;

    private double lat;
    private double lon;
    private LocalDateTime hr;
    private boolean isStopped;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public LocalDateTime getHr() {
        return hr;
    }

    public void setHr(LocalDateTime hr) {
        this.hr = hr;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }
}
