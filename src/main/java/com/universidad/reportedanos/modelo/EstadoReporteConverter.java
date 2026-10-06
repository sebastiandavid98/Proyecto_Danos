package com.universidad.reportedanos.modelo;

import comportamentales.state.EstadoReporte;
import comportamentales.state.EstadoPendiente;
import comportamentales.state.EstadoEnRevision;
import comportamentales.state.EstadoEnProceso;
import comportamentales.state.EstadoSolucionado;
import comportamentales.state.EstadoCerrado;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// Convierte entre el objeto EstadoReporte y su representacion en base de datos
@Converter(autoApply = false)
public class EstadoReporteConverter implements AttributeConverter<EstadoReporte, String> {

    @Override
    public String convertToDatabaseColumn(EstadoReporte estado) {
        if (estado == null) return null;
        if (estado instanceof EstadoPendiente)   return "PENDIENTE";
        if (estado instanceof EstadoEnRevision)  return "EN_REVISION";
        if (estado instanceof EstadoEnProceso)   return "EN_PROCESO";
        if (estado instanceof EstadoSolucionado) return "SOLUCIONADO";
        if (estado instanceof EstadoCerrado)     return "CERRADO";
        throw new IllegalArgumentException("Estado desconocido: " + estado.getClass().getName());
    }

    @Override
    public EstadoReporte convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData.toUpperCase()) {
            case "PENDIENTE"   -> new EstadoPendiente();
            case "EN_REVISION" -> new EstadoEnRevision();
            case "EN_PROCESO"  -> new EstadoEnProceso();
            case "SOLUCIONADO" -> new EstadoSolucionado();
            case "CERRADO"     -> new EstadoCerrado();
            default -> throw new IllegalArgumentException("Estado no soportado: " + dbData);
        };
    }
}
