package g56514.stibride.gestionDB.repository;

import g56514.stibride.gestionDB.dto.StationDto;
import g56514.stibride.gestionDB.dto.StopDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.StationDao;
import java.util.List;

/**
 * Represents the Repository of Station.
 *
 * @author yohan
 */
public class StationRepository implements Repository<Integer, StationDto> {

    private final StationDao dao;

    /**
     * Creates a new instance of <code>StationRepository</code>.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public StationRepository() throws RepositoryException {
        dao = StationDao.getInstance();
    }

    /**
     * Retunrs a new instance of <code>StationRepository</code> with the dao.
     * Used for mock tests.
     *
     * @param dao the dao.
     */
    public StationRepository(StationDao dao) {
        this.dao = dao;
    }

    @Override
    public Integer add(StationDto item) throws RepositoryException {
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
    public List<StationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    public List<StopDto> selectStationsForEveryLines(Integer stationActuelle) throws RepositoryException {
        return dao.selectStationsForEveryLines(stationActuelle);
    }

    public StationDto selectNeighbour(Integer line, Integer order, boolean precedent) throws RepositoryException {
        return dao.selectNeighbour(line, order, precedent);
    }

    public String selectName(Integer idStation) throws RepositoryException {
        return dao.selectName(idStation);
    }

    public List<Integer> selectLinesForStation(Integer idStation) throws RepositoryException {
        return dao.selectLinesForStation(idStation);
    }

    public int selectId(String nameStation) throws RepositoryException {
        return dao.selectId(nameStation);
    }

    public StationDto selectWithName(String nameStation) throws RepositoryException {
        return dao.selectWithName(nameStation);
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        StationDto exist = dao.select(key);
        return exist != null;
    }

}
