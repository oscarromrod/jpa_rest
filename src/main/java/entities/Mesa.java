package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "mesas")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_mesa", nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Boolean terraza;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Restaurante restaurante;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Reserva> reservas;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ID: ");
        sb.append(id);
        sb.append(" | Mesa ").append(numero);
        sb.append(" | Capacidad:").append(capacidad);
        sb.append(" | Terraza").append(terraza);
        sb.append(" | ").append(restaurante.getNombre());
        return sb.toString();
    }


}
