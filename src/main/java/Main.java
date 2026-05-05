import entities.Cliente;
import jakarta.persistence.EntityManager;
import repositories.ClienteRepository;
import utils.JpaUtil;

public class Main {

    static void main() {

            EntityManager em = JpaUtil.createEntityManager();



            em.close();
    }
}
