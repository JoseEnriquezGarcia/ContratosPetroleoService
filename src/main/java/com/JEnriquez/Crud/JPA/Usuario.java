package com.JEnriquez.Crud.JPA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "UGTP_TBL_Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estatus")
    private int Estatus;

    @Column(name = "password")
    private String Password;

    @JoinColumn(name = "idrol")
    @ManyToOne
    public Rol rol;

    @JoinColumn(name = "idusuario")
    @OneToMany
    @JsonIgnoreProperties("usuario")
    private List<Contrato> contratos;
}
