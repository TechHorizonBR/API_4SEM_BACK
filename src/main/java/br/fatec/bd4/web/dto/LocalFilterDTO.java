package br.fatec.bd4.web.dto;

import java.time.LocalDateTime;

public class LocalFilterDTO {
    private LocalDateTime dataStart;
    private LocalDateTime endDate;
    private String nomeDevice;
    private String nomeUsuario;

    public LocalDateTime getDataStart() {
        return dataStart;
    }

    public void setDataStart(LocalDateTime dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getNomeDevice() {
        return nomeDevice;
    }

    public void setNomeDevice(String nomeDevice) {
        this.nomeDevice = nomeDevice;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
