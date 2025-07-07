package com.JEnriquez.Crud.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura")
    private int IdFactura;
    
    @JoinColumn(name = "idcontrato")
    @OneToMany
    public List<Contrato> contrato;
    
    @Column(name = "fecha")
    private Date Fecha;
    
    @Column(name = "cantidadnominadarecepcion")
    private double NominadaRecepcion;
    
    @Column(name = "cantidadasignadarecepcion")
    private double AsignadaRecepcion;
    
    @Column(name = "cantidadnominadaentrega")
    private double NominadaEntrega;
    
    @Column(name = "cantidadasignadaentrega")
    private double AsignadaEntrega;
    
    @Column(name = "tarifaexcesofirme")
    private double ExcesoFirme;
    
    @Column(name = "tarifausointerrumpible")
    private double UsoInterrumpible;
    
    @Column(name = "gasexceso")
    private double GasExceso;

    @Column(name = "cargouso")
    private double CargoUso;

    @Column(name = "cargogasexceso")
    private double CargoGasExceso;
    
    @Column(name = "totalfactura")
    private double TotalFactura;
}
