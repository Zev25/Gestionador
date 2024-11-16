package modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tarea {
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String categoria;
    private String enlaceArchivo;
    private String prioridad;
    private String comentarios; // Campo para comentarios
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha

    public Tarea(String nombre, Date fechaInicio, Date fechaFin, String categoria, String enlaceArchivo, String prioridad, String comentarios) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.categoria = categoria;
        this.enlaceArchivo = enlaceArchivo;
        this.prioridad = prioridad;
        this.comentarios = comentarios; // Inicializar comentarios
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEnlaceArchivo() {
        return enlaceArchivo;
    }

    public void setEnlaceArchivo(String enlaceArchivo) {
        this.enlaceArchivo = enlaceArchivo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return nombre + ";" + DATE_FORMAT.format(fechaInicio) + ";" + DATE_FORMAT.format(fechaFin) + ";" + categoria + ";" + enlaceArchivo + ";" + prioridad + ";" + comentarios;
    }

    public static Tarea fromString(String tareaStr) throws ParseException {
        String[] parts = tareaStr.split(";");
        return new Tarea(
            parts[0],
            DATE_FORMAT.parse(parts[1]),
            DATE_FORMAT.parse(parts[2]),
            parts[3],
            parts[4],
            parts[5],
            parts.length > 6 ? parts[6] : "" // Manejar la longitud de partes para incluir comentarios
        );
    }
}
