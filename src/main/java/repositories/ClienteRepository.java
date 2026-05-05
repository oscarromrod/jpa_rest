package repositories;

import entities.Cliente;

public class ClienteRepository extends JpaRepository<Cliente, Long> {

    public ClienteRepository() {
        super(Cliente.class);
    }
}
