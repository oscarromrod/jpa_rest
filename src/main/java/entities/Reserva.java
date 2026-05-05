package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fechaReserva;

    @Column(nullable = false)
    private LocalTime horaReserva;

    @Column(nullable = false)
    private Integer numPersonas;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;


    private Double importeEstimado;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    @ToString.Exclude
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @ToString.Exclude
    private Cliente cliente;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ID: ");
        sb.append(id);
        sb.append(" | ").append(fechaReserva).append(" ").append(horaReserva);
        sb.append(" | ").append(numPersonas).append(" personas");
        sb.append(" | ").append(estado);
        sb.append(" | ").append(importeEstimado).append("€");
        sb.append(" | ").append(cliente.getNombre());
        sb.append(" | Mesa ").append(mesa.getNumero());
        return sb.toString();
    }
}
