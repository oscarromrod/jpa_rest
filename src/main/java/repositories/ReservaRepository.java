package repositories;

import entities.Reserva;

public class ReservaRepository extends JpaRepository<Reserva, Long> {

    public ReservaRepository(){
        super(Reserva.class);
    }
}
