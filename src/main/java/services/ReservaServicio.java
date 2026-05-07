package services;

import entities.*;
import jakarta.persistence.EntityManager;
import utils.JpaUtil;

import java.time.LocalDate;
import java.util.List;
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
//    public List<Reserva> getRecaudacionPorRestaurante(){
//        EntityManager em = JpaUtil.createEntityManager();
//        try{
//            List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r JOIN r.mesa m", Reserva.class)
//                    .getResultList();
//
//            return reservas.stream()
//                    .collect(Collectors.groupingBy())
//        }finally {
//            em.close();
//        }
//    }
}
