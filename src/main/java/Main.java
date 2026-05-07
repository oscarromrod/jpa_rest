import entities.*;
import jakarta.persistence.EntityManager;
import repositories.ClienteRepository;
import repositories.MesaRepository;
import repositories.ReservaRepository;
import repositories.RestauranteRepository;
import services.ReservaServicio;
import utils.JpaUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {

    static void main() {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            /*
            em.getTransaction().begin();
            RestauranteRepository restauranteRepo = new RestauranteRepository();
            ClienteRepository clienteRepo = new ClienteRepository();
            MesaRepository mesaRepo = new MesaRepository();
            ReservaRepository reservaRepo = new ReservaRepository();

            restauranteRepo.save(new Restaurante(null, "Rincón del Puerto", "Garrucha", "Explanada Del Puerto s/n", "950133043", null));
            restauranteRepo.save(new Restaurante(null, "Mohana", "Mojácar", "Paseo del Mediterráneo, 285", "950472941", null));

            Restaurante r1 = restauranteRepo.findById(1L).orElse(null);
            Restaurante r2 = restauranteRepo.findById(2L).orElse(null);

            mesaRepo.save(new Mesa(null, 1, 4, false, r1, null));
            mesaRepo.save(new Mesa(null, 2, 2, true, r1, null));
            mesaRepo.save(new Mesa(null, 3, 3, true, r1, null));
            mesaRepo.save(new Mesa(null, 4, 6, false, r1, null));
            mesaRepo.save(new Mesa(null, 5, 2, false, r1, null));
            mesaRepo.save(new Mesa(null, 6, 4, true, r1, null));
            mesaRepo.save(new Mesa(null, 1, 2, false, r2, null));
            mesaRepo.save(new Mesa(null, 2, 4, false, r2, null));
            mesaRepo.save(new Mesa(null, 3, 6, true, r2, null));
            mesaRepo.save(new Mesa(null, 4, 4, true, r2, null));
            mesaRepo.save(new Mesa(null, 5, 2, false, r2, null));
            mesaRepo.save(new Mesa(null, 6, 8, true, r2, null));

            Mesa m1 = mesaRepo.findById(1L).orElse(null);
            Mesa m2 = mesaRepo.findById(2L).orElse(null);
            Mesa m3 = mesaRepo.findById(3L).orElse(null);
            Mesa m4 = mesaRepo.findById(4L).orElse(null);
            Mesa m5 = mesaRepo.findById(5L).orElse(null);
            Mesa m6 = mesaRepo.findById(6L).orElse(null);
            Mesa m7 = mesaRepo.findById(7L).orElse(null);
            Mesa m8 = mesaRepo.findById(8L).orElse(null);
            Mesa m9 = mesaRepo.findById(9L).orElse(null);
            Mesa m10 = mesaRepo.findById(10L).orElse(null);
            Mesa m11 = mesaRepo.findById(11L).orElse(null);
            Mesa m12 = mesaRepo.findById(12L).orElse(null);

            clienteRepo.save(new Cliente(null, "Óscar", "oscar@gmail.com", "606060606", false, null));
            clienteRepo.save(new Cliente(null, "Felix", "felix@gmail.com", "601010101", true, null));
            clienteRepo.save(new Cliente(null, "Dani", "dani@gmail.com", "602020202", true, null));
            clienteRepo.save(new Cliente(null, "Mirian", "mirian@gmail.com", "603030303", false, null));
            clienteRepo.save(new Cliente(null, "Hugo", "hugo@gmail.com", "604040404", false, null));
            clienteRepo.save(new Cliente(null, "Moha", "moha@gmail.com", "605050505", true, null));
            clienteRepo.save(new Cliente(null, "Jesus", "jesus@gmail.com", "607070707", false, null));
            clienteRepo.save(new Cliente(null, "Javi", "javi@gmail.com", "608080808", true, null));
            clienteRepo.save(new Cliente(null, "Cati", "cati@gmail.com", "609090909", false, null));
            clienteRepo.save(new Cliente(null, "Ana", "ana@gmail.com", "616161616", true, null));

            Cliente c1 = clienteRepo.findById(1L).orElse(null);
            Cliente c2 = clienteRepo.findById(2L).orElse(null);
            Cliente c3 = clienteRepo.findById(3L).orElse(null);
            Cliente c4 = clienteRepo.findById(4L).orElse(null);
            Cliente c5 = clienteRepo.findById(5L).orElse(null);
            Cliente c6 = clienteRepo.findById(6L).orElse(null);
            Cliente c7 = clienteRepo.findById(7L).orElse(null);
            Cliente c8 = clienteRepo.findById(8L).orElse(null);
            Cliente c9 = clienteRepo.findById(9L).orElse(null);
            Cliente c10 = clienteRepo.findById(10L).orElse(null);

            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.CONFIRMADA, 200.0, m8, c1));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.CANCELADA, 100.0, m2, c2));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 8, EstadoReserva.PENDIENTE, 400.0, m12, c3));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.CONFIRMADA, 200.0, m1, c4));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 3, EstadoReserva.CONFIRMADA, 150.0, m3, c5));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.CANCELADA, 200.0, m6, c6));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.CANCELADA, 100.0, m7, c7));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.PENDIENTE, 100.0, m7, c8));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.PENDIENTE, 200.0, m1, c9));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 8, EstadoReserva.COMPLETADA, 400.0, m12, c10));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 8, EstadoReserva.CONFIRMADA, 400.0, m12, c1));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.COMPLETADA, 100.0, m11, c2));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 3, EstadoReserva.COMPLETADA, 150.0, m3, c3));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.NO_SHOW, 200.0, m4, c4));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 8, EstadoReserva.COMPLETADA, 400.0, m12, c5));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 3, EstadoReserva.PENDIENTE, 150.0, m3, c6));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.PENDIENTE, 100.0, m1, c7));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 3, EstadoReserva.CONFIRMADA, 150.0, m3, c8));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.CANCELADA, 200.0, m10, c9));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.NO_SHOW, 100.0, m5, c10));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 6, EstadoReserva.NO_SHOW, 100.0, m9, c1));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.COMPLETADA, 100.0, m5, c2));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 3, EstadoReserva.CANCELADA, 150.0, m3, c3));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.COMPLETADA, 200.0, m10, c4));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 8, EstadoReserva.COMPLETADA, 400.0, m1, c5));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 3, EstadoReserva.CONFIRMADA, 150.0, m3, c6));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 4, EstadoReserva.CONFIRMADA, 200.0, m10, c7));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 2, EstadoReserva.PENDIENTE, 100.0, m5, c8));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 8, EstadoReserva.PENDIENTE, 400.0, m12, c9));
            reservaRepo.save(new Reserva(null, LocalDate.now(), LocalTime.now(), 3, EstadoReserva.CONFIRMADA, 150.0, m3, c10));

            em.getTransaction().commit();
            */

            //=================== CONSULTAS ======================

            ReservaServicio rs = new ReservaServicio();

            IO.println("=== CONSULTA 1: Reservas confirmadas ===");
            rs.getReservasConfirmadas().forEach(IO::println);

            IO.println("=== CONSULTA 2: Reservas de un restaurante concreto ===");
            rs.getReservasPorRestaurante(2L).forEach(IO::println);

            IO.println("=== CONSULTA 3: Reservas pendientes para hoy ===");
            rs.getReservasPendientesHoy().forEach(IO::println);

            IO.println("=== CONSULTA 4: Recaudación total por restaurante ===");


            IO.println("=== CONSULTA 5: Restaurante con más mesas ===");


            IO.println("=== CONSULTA 6: Reservas canceladas o no presentadas ===");


            IO.println("=== CONSULTA 7: Número de reservas por ciudad ===");


            IO.println("=== CONSULTA 8: Mesas más solicitadas ===");


            IO.println("=== CONSULTA 9: Importe medio por reserva según terraza ===");


            IO.println("=== CONSULTA 10: Clientes frecuentes ===");







        } catch (Exception e){
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
