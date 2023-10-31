package g56514.stibride.gestionDB.jdbc;

import g56514.stibride.gestionDB.dto.FavoriDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.repository.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Data Access Object of Favori.
 *
 * @author yohan
 */
public class FavoriDao implements Dao<Integer, FavoriDto> {

    private Connection connexion;

    /**
     * Constructor of <code>FavoriDao</code>.
     *
     * @return an instance of FavoriDao.
     * @throws RepositoryException if there is a repository error.
     */
    private FavoriDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Retunrs a new instance of <code>FavoriDao</code>.
     *
     * @return an instance of FavoriDao.
     * @throws RepositoryException if there is a repository error.
     */
    public static FavoriDao getInstance() throws RepositoryException {
        return FavoriDaoHolder.getInstance();
    }

    /**
     * Insert a new data in the database.
     *
     * @param item the item to be added into database.
     * @return the key of the added item.
     * @throws RepositoryException if there is a repository error.
     */
    @Override
    public Integer insert(FavoriDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucun élément donné en paramètre");
        }
        int id = 0;
        List<FavoriDto> list = selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(item.getName())) {
                throw new RepositoryException("ce nom favori existe déja !");
            }
        }
        String sql = "INSERT INTO FAVORI(name, departure, arrival) values(?, ?, ?)";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getDeparture());
            pstmt.setInt(3, item.getArrival());
            pstmt.executeUpdate();

            ResultSet result = pstmt.getGeneratedKeys();
            System.out.println(result);
            while (result.next()) {
                id = 1; //1 for success
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return id;
    }

    /**
     * Delete item from the database. The method deleteWithName is prefered
     * instead of this method. So, it doesn't be tested.
     *
     * @param key the key of item to be deleted.
     * @throws RepositoryException if there is a repository error.
     */
    @Override
    public void delete(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("aucun élément donné en paramètre");
        }
        String sql = "DELETE FROM FAVORI where name = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, sql);
            ResultSet rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Update item.
     *
     * @param item the item to be updated.
     * @throws RepositoryException if there is a repository error.
     */
    @Override
    public void update(FavoriDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("aucun élément donné en paramètre");
        }
        int nbUpdated = 0;
        String sql = "UPDATE FAVORI set name = ?, departure = ?, arrival = ? where id = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getDeparture());
            pstmt.setInt(3, item.getArrival());
            pstmt.setInt(4, item.getKey());
            nbUpdated = pstmt.executeUpdate();
            System.out.println("NB DE UODATED ROWS : " + nbUpdated);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Update item used for tests.
     *
     * @param item the item to be updated.
     * @return the number of updated rows.
     * @throws RepositoryException if there is a repository error.
     */
    public int updateForMockito(FavoriDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("aucun élément donné en paramètre");
        }
        int nbUpdated = 0;
        String sql = "UPDATE FAVORI set name = ?, departure = ?, arrival = ? where id = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getDeparture());
            pstmt.setInt(3, item.getArrival());
            pstmt.setInt(4, item.getKey());
            nbUpdated = pstmt.executeUpdate();
            System.out.println("NB DE UODATED ROWS : " + nbUpdated);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return nbUpdated;
    }

    /**
     * Returns all rows of the database.
     *
     * @return a list that contains all returned items.
     * @throws RepositoryException if there is a repository error.
     */
    @Override
    public List<FavoriDto> selectAll() throws RepositoryException {
        String sql = "SELECT id, name, departure, arrival FROM FAVORI";
        List<FavoriDto> dtos = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                FavoriDto dto = new FavoriDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return dtos;
    }

    /**
     * Returns a specific row from the database. The method selectWithName is
     * prefered instead of this method.
     *
     * @param key the id of the item.
     * @return the item with the specified key.
     * @throws RepositoryException if there is a repository error.
     */
    @Override
    public FavoriDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id, name, departure, arrival FROM FAVORI WHERE id = ?";
        FavoriDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new FavoriDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Record pas unique " + key);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * Returns a specific row from the database.
     *
     * @param name the name of the item.
     * @return the item with the specified name.
     * @throws RepositoryException if there is a repository error.
     */
    public FavoriDto selectWithName(String name) throws RepositoryException {
        if (name == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id, name, departure, arrival FROM FAVORI WHERE name = ?";
        FavoriDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new FavoriDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Record pas unique " + name);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * Delete item from the database. 
     *
     * @param favoriName the name of the item.
     * @return the number of deleted rows.
     * @throws RepositoryException if there is a repository error.
     */
    public int deleteWithName(String favoriName) throws RepositoryException {
        if (favoriName == null) {
            throw new RepositoryException("aucun élément donné en paramètre");
        }
        int nbDeleted = 0;
        String sql = "DELETE FROM FAVORI where name = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, favoriName);
            nbDeleted = pstmt.executeUpdate();
            System.out.println("NB DE DELETED ROWS : " + nbDeleted);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return nbDeleted;
    }

    private static class FavoriDaoHolder {

        private static FavoriDao getInstance() throws RepositoryException {
            return new FavoriDao();
        }
    }

}
