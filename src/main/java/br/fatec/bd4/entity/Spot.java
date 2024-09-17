package br.fatec.bd4.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "LocalTracker") // Use @Document para MongoDB
public class Spot {

    @Id
    private String id; // O campo _id do MongoDB é String, não Long.

    private LocalDateTime CreatedAt = LocalDateTime.now();

    private Double lat;

    private Double lng;

    private String fullName;

    private String code;

    private String location;

    private String macAdress;

    private String codeDevice;

    private String idDevice;

}
