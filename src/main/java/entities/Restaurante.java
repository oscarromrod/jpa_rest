package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="restaurantes")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_restaurante")
    private String nombre;


    private String ciudad;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mesa> mesas;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ID: ");
        sb.append(id);
        sb.append(" | ").append(nombre);
        sb.append(" | ").append(ciudad);
        sb.append(" | ").append(direccion);
        sb.append(" | ").append(telefono);
        return sb.toString();
    }
}
