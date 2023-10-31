package g56514.stibride.gestionDB.repository;

import g56514.stibride.gestionDB.dto.FavoriDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.FavoriDao;
import java.util.List;

/**
 * Represents the Repository of Favori.
 * 
 * @author yohan
 */
public class FavoriRepository implements Repository<Integer, FavoriDto> {

    private final FavoriDao dao;

    /**
     * Creates a new instance of <code>FavoriRepository</code>.
     * 
     * @throws RepositoryException if there is a repository error.
     */
    public FavoriRepository() throws RepositoryException {
        dao = FavoriDao.getInstance();
    }

    /**
     * Retunrs a new instance of <code>FavoriRepository</code> 
     * with the dao.Used for mock tests.
     *
     * @param dao the dao.
     */
    public FavoriRepository(FavoriDao dao) {
        this.dao = dao;
    }

    @Override
    public Integer add(FavoriDto item) throws RepositoryException {
        return dao.insert(item);
    }

    // Not used
    @Override
    public void remove(Integer key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<FavoriDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoriDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    public FavoriDto getWithNameOfFavori(String name) throws RepositoryException {
        return dao.selectWithName(name);
    }

    // Not used
    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return get(key) != null;
    }

    public void deleteWithName(String favoriName) throws RepositoryException {
        dao.deleteWithName(favoriName);
    }

    public void update(FavoriDto dto) throws RepositoryException {
        dao.update(dto);
    }
    
    public int updateForMockito(FavoriDto dto) throws RepositoryException {
        return dao.updateForMockito(dto);
    }
}
