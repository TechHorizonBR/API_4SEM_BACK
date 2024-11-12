package br.fatec.bd4.entity;

import java.io.Serializable;

import br.fatec.bd4.entity.Enum.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_sys")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSys implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "user_password", length = 250, nullable = false)
    private String password;

    @Column(name = "user_name", length = 250, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;
 
}
