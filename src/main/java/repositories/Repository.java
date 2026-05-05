package repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    void save(T entidad);
    Optional<T> findById(ID id);
    List<T> findAll();
    void update(T entidad);
    void delete(ID id);
}