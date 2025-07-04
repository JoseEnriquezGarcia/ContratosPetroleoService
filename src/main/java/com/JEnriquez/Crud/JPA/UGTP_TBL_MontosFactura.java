package com.JEnriquez.Crud.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class UGTP_TBL_MontosFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmontosfactura")
    private int IdMontosFactura;
    
    @JoinColumn(name = "idtarifadetalle")
    @OneToOne
    public UGTP_TBL_TarifaDetalle tarifaDetalle;
    
    @JoinColumn(name = "idcantidaddetalle")
    @OneToOne
    public UGTP_TBL_CantidadDetalle cantidadDetalle;
    
    @Column(name = "gasexceso")
    private double GasExceso;
    
    @Column(name = "cargouso")
    private double CargoUso;
    
    @Column(name = "cargogasexceso")
    private double CargoGasExceso;
}
