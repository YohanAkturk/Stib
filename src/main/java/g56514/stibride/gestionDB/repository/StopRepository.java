package g56514.stibride.gestionDB.repository;

import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.dto.StopDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.StopDao;
import java.util.List;

/**
 * Represents the Repository of Stop.
 * 
 * @author yohan
 */
public class StopRepository implements Repository<Pair, StopDto> {

    private final StopDao dao;

    /**
     * Creates a new instance of <code>StopRepository</code>.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public StopRepository() throws RepositoryException {
        dao = StopDao.getInstance();
    }

    /**
     * Retunrs a new instance of <code>StopRepository</code> with the dao.
     * Used for mock tests.
     *
     * @param dao the dao.
     */
    public StopRepository(StopDao dao) {
        this.dao = dao;
    }

    //not used
    @Override
    public Pair add(StopDto item) throws RepositoryException {
        Pair key = item.getKey();
        if (contains(item.getKey())) {
            dao.update(item);
        } else {
            key = dao.insert(item);
        }
        return key;
    }

    //not used
    @Override
    public void remove(Pair key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(Pair key) throws RepositoryException {
        return dao.select(key);
    }

    //not used
    @Override
    public boolean contains(Pair key) throws RepositoryException {
        StopDto exist = dao.select(key);
        return exist != null;
    }
}
