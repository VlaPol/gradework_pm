package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepositoryImpl<E, ID> implements BaseRepository<E, ID> {

    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<E> entityClass;

    @Override
    public Optional<E> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public void create(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void remove(ID id) {
        E tmpEntity = entityManager.find(entityClass, id);
        entityManager.remove(tmpEntity);
    }

}
