package com.JEnriquez.Crud.JPA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "UGTP_TBL_Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura")
    private int idFactura;
    
    @JoinColumn(name = "idcontrato")
    @ManyToOne
    @JsonIgnoreProperties("facturas")
    public Contrato contrato;
    
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
