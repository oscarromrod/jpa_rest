package services;

import entities.*;
import jakarta.persistence.EntityManager;
import utils.JpaUtil;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReservaServicio {

    /**
     * Listar todas las reservas con estado CONFIRMADA , ordenadas por fecha ascendente.
     * @return
     */
    public List<Reserva> getReservasConfirmadas(){
        EntityManager em = JpaUtil.createEntityManager();
        try{
            return em.createQuery("FROM Reserva r WHERE r.estado = :estado ORDER BY r.fechaReserva ASC", Reserva.class)
                    .setParameter("estado", EstadoReserva.CONFIRMADA)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Mostrar todas las reservas asociadas a un restaurante dado.
     */
    public List<Reserva> getReservasPorRestaurante(Long restauranteId){
        EntityManager em = JpaUtil.createEntityManager();
        try{
            return em.createQuery("SELECT r FROM Reserva r JOIN r.mesa m JOIN m.restaurante restaurante WHERE restaurante.id = :idRestaurante", Reserva.class)
                    .setParameter("idRestaurante", restauranteId)
                    .getResultList();
        }finally {
            em.close();
        }
    }

    /**
     * Obtener las reservas pendientes del día actual.
     */
    public List<Reserva> getReservasPendientesHoy(){
        EntityManager em = JpaUtil.createEntityManager();
        try{
            return em.createQuery("SELECT r FROM Reserva r WHERE r.estado = :estado AND r.fechaReserva = :hoy", Reserva.class)
                    .setParameter("estado", EstadoReserva.PENDIENTE)
                    .setParameter("hoy", LocalDate.now())
                    .getResultList();
        }finally {
            em.close();
        }
    }

    /**
     * Calcular la recaudación total estimada de cada restaurante, mostrando el nombre del
     * restaurante y el total, de mayor a menor.
     */
    public Map<String, Double> getRecaudacionPorRestaurante(){
        EntityManager em = JpaUtil.createEntityManager();
        try{
            List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r JOIN r.mesa m", Reserva.class)
                    .getResultList();

            return reservas.stream()
                    .collect(Collectors.groupingBy(r -> r.getMesa().getRestaurante().getNombre(), Collectors.summingDouble(Reserva::getImporteEstimado)))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
        }finally {
            em.close();
        }
    }

    /**
     * Obtener el restaurante que tenga el mayor número de mesas registradas.
     */
    public Optional<Restaurante> getRestauranteConMasMesas() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Restaurante r ORDER BY SIZE(r.mesas) DESC", Restaurante.class)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } finally {
            em.close();
        }
    }

    /**
     * Listar las reservas con estado CANCELADA o NO_SHOW , ordenadas por fecha descendente.
     */

    /**
     * Mostrar cuántas reservas existen agrupadas por ciudad del restaurante.
     */

    /**
     * Detectar las mesas con más reservas registradas.
     */

    /**
     * Calcular el importe medio estimado de las reservas en mesas de terraza y en mesas interiores.
     */

    /**
     * Obtener los nombres de clientes con al menos minimoReservas reservas, sin repeticiones.
     */
}
