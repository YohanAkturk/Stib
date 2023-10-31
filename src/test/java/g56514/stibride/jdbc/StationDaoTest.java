package g56514.stibride.jdbc;

import g56514.stibride.gestionDB.config.ConfigManager;
import g56514.stibride.gestionDB.dto.StationDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.StationDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author yohan
 */
public class StationDaoTest {
    private StationDao instance;

    private final StationDto station1;
    private final StationDto station2;
    private static final int KEY = 8012;
    private final List<StationDto> all;

    public StationDaoTest() throws SQLException, IOException, RepositoryException {
        System.out.println("==== FavoriDaoTest Constructor =====");
        station1 = new StationDto(KEY, "DE BROUCKERE");
        station2 = new StationDto(99_999, "NULL");

        all = new ArrayList<>();
        all.add(station1);
        all.add(station2);

        try {
            ConfigManager.getInstance().load();
            instance = StationDao.getInstance();
        } catch (RepositoryException | IOException ex) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", ex);
        }
    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Arrange
        StationDto expected = station1;
        //Action
        StationDto result = instance.select(KEY);
        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Arrange
        //Action
        StationDto result = instance.select(station2.getKey());
        //Assert
        assertNull(result);
    }

    @Test
    public void testSelectIncorrectParameter() throws Exception {
        System.out.println("testSelectIncorrectParameter");
        //Arrange
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.select(incorrect);
        });
    }

    @Test
    public void testSelectAll() throws Exception {
        System.out.println("testSelectAll");
        //Arrange
        int expected = 60; //because there are 2 datas
        //Action
        List<StationDto> result = instance.selectAll();
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        //Assert        
        assertEquals(expected, result.size());
    }

}
