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

    //Para solucionar el problema del fetch.LAZY he encontrado una solución poniendo JOIN FETCH en las consultas que considero que es necesario.

    /**
     * Listar todas las reservas con estado CONFIRMADA , ordenadas por fecha ascendente.
     * @return
     */
    public List<Reserva> getReservasConfirmadas(){
        EntityManager em = JpaUtil.createEntityManager();
        try{
            return em.createQuery("SELECT r FROM Reserva r JOIN FETCH r.cliente JOIN FETCH r.mesa m JOIN FETCH m.restaurante WHERE r.estado = :estado ORDER BY r.fechaReserva ASC", Reserva.class)
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
            return em.createQuery("SELECT r FROM Reserva r JOIN FETCH r.cliente JOIN FETCH r.mesa m JOIN FETCH m.restaurante restaurante WHERE restaurante.id = :idRestaurante", Reserva.class)
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
            return em.createQuery("SELECT r FROM Reserva r JOIN FETCH r.cliente JOIN FETCH r.mesa m JOIN FETCH m.restaurante WHERE r.estado = :estado AND r.fechaReserva = :hoy", Reserva.class)
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
            List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r JOIN FETCH r.mesa m JOIN FETCH m.restaurante", Reserva.class)
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
            return em.createQuery("SELECT r FROM Restaurante r LEFT JOIN FETCH r.mesas ORDER BY SIZE(r.mesas) DESC", Restaurante.class)
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
    public List<Reserva> getReservasProblematicas() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Reserva r JOIN FETCH r.cliente JOIN FETCH r.mesa m JOIN FETCH m.restaurante WHERE r.estado IN :estados ORDER BY r.fechaReserva DESC", Reserva.class)
                    .setParameter("estados", List.of(EstadoReserva.CANCELADA, EstadoReserva.NO_SHOW))
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Mostrar cuántas reservas existen agrupadas por ciudad del restaurante.
     */
    public Map<String, Long> getReservasPorCiudad() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            List<Object[]> resultados = em.createQuery("SELECT r.mesa.restaurante.ciudad, COUNT(r) FROM Reserva r GROUP BY r.mesa.restaurante.ciudad", Object[].class)
                    .getResultList();

            return resultados.stream()
                    .collect(Collectors.toMap(fila -> (String) fila[0], fila -> (Long) fila[1]));
        } finally {
            em.close();
        }
    }

    /**
     * Detectar las mesas con más reservas registradas.
     */
    public Map<Mesa, Long> getMesasMasSolicitadas() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            List<Object[]> resultados = em.createQuery("SELECT m, COUNT(r) FROM Reserva r JOIN r.mesa m JOIN FETCH m.restaurante GROUP BY m", Object[].class)
                    .getResultList();

            return resultados.stream()
                    .sorted((a, b) -> ((Long) b[1]).compareTo((Long) a[1]))
                    .collect(Collectors.toMap(fila -> (Mesa) fila[0], fila -> (Long) fila[1],(e1, e2) -> e1, LinkedHashMap::new));
        } finally {
            em.close();
        }
    }

    /**
     * Calcular el importe medio estimado de las reservas en mesas de terraza y en mesas interiores.
     */
    public Map<String, Double> getImporteMedioPorTerraza() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            List<Object[]> resultados = em.createQuery("SELECT m.terraza, AVG(r.importeEstimado) FROM Reserva r JOIN r.mesa m GROUP BY m.terraza", Object[].class)
                    .getResultList();

            return resultados.stream()
                    .collect(Collectors.toMap(fila -> (Boolean) fila[0] ? "Terraza" : "Interior", fila -> (Double) fila[1]));
        } finally {
            em.close();
        }
    }

    /**
     * Obtener los nombres de clientes con al menos minimoReservas reservas, sin repeticiones.
     */
    public List<String> getClientesFrecuentes(int minimoReservas) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            List<Cliente> clientes = em.createQuery("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.reservas r", Cliente.class)
                    .getResultList();

            return clientes.stream()
                    .filter(c -> c.getReservas().size() >= minimoReservas)
                    .map(Cliente::getNombre)
                    .distinct()
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
}
