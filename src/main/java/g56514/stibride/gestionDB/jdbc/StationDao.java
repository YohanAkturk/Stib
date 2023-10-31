package g56514.stibride.gestionDB.jdbc;

import g56514.stibride.gestionDB.dto.StationDto;
import g56514.stibride.gestionDB.dto.StopDto;
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
 * Represents the Data Access Object of Station.
 *
 * @author yohan
 */
public class StationDao implements Dao<Integer, StationDto> {

    private Connection connexion;

    /**
     * Constructor of <code>StationDao</code>.
     *
     * @return an instance of StationDao.
     * @throws RepositoryException if there is a repository error.
     */
    private StationDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Retunrs a new instance of <code>StationDao</code>.
     *
     * @return an instance of StationDao.
     * @throws RepositoryException if there is a repository error.
     */
    public static StationDao getInstance() throws RepositoryException {
        return StationDaoHolder.getInstance();
    }

    @Override
    public Integer insert(StationDto item) throws RepositoryException {
//        if (item == null) {
//            throw new RepositoryException("Aucun élément donné en paramètre");
//        }
//        Integer id = 0;
//        String sql = "INSERT INTO GRADES(score, date, dateModified, id_student, id_lesson) values(?, ?, ?, ?, ?)";
//        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
//            pstmt.setInt(1, item.getScore());
//            pstmt.setString(2, item.getDate());
//            pstmt.setString(3, item.getDateModified());
//            pstmt.setInt(4, item.getId_student());
//            pstmt.setString(5, item.getId_lesson());
//            pstmt.executeUpdate();
//
//            ResultSet result = pstmt.getGeneratedKeys();
//            while (result.next()) {
//                id = result.getInt(1);
//            }
//        } catch (SQLException e) {
//            throw new RepositoryException(e);
//        }
//        return id;
        return -1;
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
//        if (key == null) {
//            throw new RepositoryException("Aucune clé donnée en paramètre");
//        }
//        String sql = "DELETE FROM GRADES WHERE id = ?";
//        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
//            pstmt.setInt(1, key);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RepositoryException(e);
//        }
    }

    @Override
    public void update(StationDto item) throws RepositoryException {
//        if (item == null) {
//            throw new RepositoryException("Aucune clé donnée en paramètre");
//        }
//        String sql = "UPDATE GRADES SET score=?, date=?, dateModified=?, id_student=?, id_lesson=? where id=?";
//        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
//            pstmt.setInt(1, item.getScore());
//            pstmt.setString(2, item.getDate());
//            pstmt.setString(3, item.getDateModified());
//            pstmt.setInt(4, item.getId_student());
//            pstmt.setString(5, item.getId_lesson());
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RepositoryException(e);
//        }
    }

    /**
     * Returns all rows of the database.
     *
     * @return a list that contains all returned items.
     * @throws RepositoryException if there is a repository error.
     */
    @Override
    public List<StationDto> selectAll() throws RepositoryException {
        String sql = "SELECT id, name FROM STATIONS";
        List<StationDto> dtos = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                StationDto dto = new StationDto(rs.getInt(1), rs.getString(2));
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
    public StationDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id, name FROM STATIONS WHERE id = ?";
        StationDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StationDto(rs.getInt(1), rs.getString(2));
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
     * Returns stations for each line from the database.
     *
     * @param stationActuelle the current station.
     * @return the list of stations (stops) for every line.
     * @throws RepositoryException if there is a repository error.
     */
    public List<StopDto> selectStationsForEveryLines(Integer stationActuelle) throws RepositoryException {
        if (stationActuelle == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id_line, id_station, id_order FROM STATIONS SA JOIN STOPS SO ON SA.id = SO.id_station WHERE id_station = ? GROUP BY id_station, id_line";
        List<StopDto> dtos = new ArrayList<>();
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, stationActuelle);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return dtos;
    }

    /**
     * Returns all lines for a specific station from the database.
     *
     * @param idStation the id of the station.
     * @return the list of lines.
     * @throws RepositoryException if there is a repository error.
     */
    public List<Integer> selectLinesForStation(Integer idStation) throws RepositoryException {
        if (idStation == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id_line, id_station, id_order FROM STOPS JOIN STATIONS ON STOPS.id_station = STATIONS.id where STATIONS.id = ?";
        List<Integer> lines = new ArrayList<>();
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idStation);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                Integer line = (Integer) dto.getKey().getFirst();
                lines.add(line);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return lines;
    }

    /**
     * Returns the neighbour stop for a given station.
     *
     * @param line the line of the station.
     * @param order the order of the station in the line.
     * @param precedent a value that specified if this is the next or the
     * precedent stop.
     * @return the neighbour stop of the given station.
     * @throws RepositoryException if there is a repository error.
     */
    public StationDto selectNeighbour(Integer line, Integer order, boolean precedent) throws RepositoryException {
        if (line == null || order == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id, name FROM STATIONS SA JOIN STOPS SO ON SA.id = SO.id_station WHERE SO.id_line = ? AND SO.id_order = ?";
        StationDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, line);
            if (precedent) {
                pstmt.setInt(2, (order + 1));
            } else {
                pstmt.setInt(2, (order - 1));
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new StationDto(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * Returns the id of the station thanks to its name from the database.
     *
     * @param nameStation the name of the station.
     * @return the id of the station.
     * @throws RepositoryException if there is a repository error.
     */
    public int selectId(String nameStation) throws RepositoryException {
        if (nameStation == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id FROM STATIONS where name = ?";
        int id = -1;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, nameStation);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("dao : " + nameStation);

            while (rs.next()) {
                System.out.println("rs : " + rs.getInt("id"));
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return id;
    }

    /**
     * Returns a specific row from the database.
     *
     * @param nameStation the name of the station.
     * @return the item with the specified name.
     * @throws RepositoryException if there is a repository error.
     */
    public StationDto selectWithName(String nameStation) throws RepositoryException {
        String sql = "SELECT * FROM STATIONS WHERE name = ?";
        StationDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, nameStation);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dto = new StationDto(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * Returns a specific row from the database.
     *
     * @param idStation the id of the station.
     * @return the item with the specified name.
     * @throws RepositoryException if there is a repository error.
     */
    public String selectName(Integer idStation) throws RepositoryException {
        if (idStation == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT name FROM STATIONS WHERE id = ?";
        String name = "";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idStation);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return name;
    }

    private static class StationDaoHolder {

        private static StationDao getInstance() throws RepositoryException {
            return new StationDao();
        }
    }

}
