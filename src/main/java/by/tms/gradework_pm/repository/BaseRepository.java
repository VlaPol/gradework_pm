package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.entity.Employee;

import java.util.Optional;

public interface BaseRepository<E, ID> {

    Optional<E> findById(ID id);

    void create(E entity);

    void remove(ID id);

    void update(E entity);
}
