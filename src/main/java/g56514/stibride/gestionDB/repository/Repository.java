package g56514.stibride.gestionDB.repository;

import g56514.stibride.gestionDB.dto.Dto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import java.util.List;

/**
 *
 * @author yohan
 */
public interface Repository<K, T extends Dto<K>> {

    K add(T item) throws RepositoryException;

    void remove(K key) throws RepositoryException;

    List<T> getAll() throws RepositoryException;

    T get(K key) throws RepositoryException;

    boolean contains(K key) throws RepositoryException;

}
