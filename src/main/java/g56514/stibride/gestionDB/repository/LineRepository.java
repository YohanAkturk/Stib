package g56514.stibride.gestionDB.repository;

import g56514.stibride.gestionDB.dto.LineDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.LineDao;
import java.util.List;

/**
 * Represents the Repository of Line.
 * 
 * @author yohan
 */
public class LineRepository implements Repository<Integer, LineDto> {

    private final LineDao dao;

    /**
     * Creates a new instance of <code>LineRepository</code>.
     * 
     * @throws RepositoryException if there is a repository error.
     */
    public LineRepository() throws RepositoryException {
        dao = LineDao.getInstance();
    }

    /**
     * Retunrs a new instance of <code>LineRepository</code> 
     * with the dao.Used for mock tests.
     *
     * @param dao the dao.
     */
    public LineRepository(LineDao dao) {
        this.dao = dao;
    }

    @Override
    public Integer add(LineDto item) throws RepositoryException {
        Integer key = item.getKey();
        if (contains(item.getKey())) {
            dao.update(item);
        } else {
            key = dao.insert(item);
        }
        return key;
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<LineDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public LineDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        LineDto exist = dao.select(key);
        return exist != null;
    }

}
