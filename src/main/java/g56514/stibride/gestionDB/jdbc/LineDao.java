package g56514.stibride.gestionDB.jdbc;

import g56514.stibride.gestionDB.dto.LineDto;
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
 * Represents the Data Access Object of Line.
 * 
 * @author yohan
 */
public class LineDao implements Dao<Integer, LineDto> {

    private Connection connexion;

    /**
     * Constructor of <code>LineDao</code>.
     *
     * @return an instance of LineDao.
     * @throws RepositoryException if there is a repository error.
     */
    private LineDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Retunrs a new instance of <code>LineDao</code>.
     *
     * @return an instance of LineDao.
     * @throws RepositoryException if there is a repository error.
     */
    public static LineDao getInstance() throws RepositoryException {
        return LineDaoHolder.getInstance();
    }

    @Override
    public Integer insert(LineDto item) throws RepositoryException {
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
    public void update(LineDto item) throws RepositoryException {
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
    public List<LineDto> selectAll() throws RepositoryException {
        String sql = "SELECT id FROM LINES";
        List<LineDto> dtos = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                LineDto dto = new LineDto(rs.getInt(1));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        System.out.println(dtos.size());
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
    public LineDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id FROM LINES WHERE id = ?";
        LineDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new LineDto(rs.getInt(1));
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

    private static class LineDaoHolder {

        private static LineDao getInstance() throws RepositoryException {
            return new LineDao();
        }
    }

}
