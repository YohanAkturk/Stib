package g56514.stibride.jdbc;

import g56514.stibride.gestionDB.config.ConfigManager;
import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.dto.StopDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.StopDao;
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
public class StopDaoTest {

    private StopDao instance;

    private final StopDto stop1;
    private final StopDto stop2;
    private static final int ID_LINE = 1;
    private static final int ID_STATION = 8382;
    private static final int ID_ORDER = 1;

    private final List<StopDto> all;

    public StopDaoTest() throws SQLException, IOException, RepositoryException {
        System.out.println("==== FavoriDaoTest Constructor =====");
        stop1 = new StopDto(ID_LINE, ID_STATION, ID_ORDER);
        stop2 = new StopDto(99_999, 99_999, 99_999);

        all = new ArrayList<>();
        all.add(stop1);
        all.add(stop2);

        try {
            ConfigManager.getInstance().load();
            instance = StopDao.getInstance();
        } catch (RepositoryException | IOException ex) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", ex);
        }
    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Arrange
        StopDto expected = stop1;
        //Action
        StopDto result = instance.select(new Pair(ID_LINE, ID_STATION));
        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Arrange
        //Action
        StopDto result = instance.select(stop2.getKey());
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
            instance.select(null);
        });
    }

    @Test
    public void testSelectAll() throws Exception {
        System.out.println("testSelectAll");
        //Arrange
        int expected = 94; //because there are 4 datas
        //Action
        List<StopDto> result = instance.selectAll();
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        //Assert        
        assertEquals(expected, result.size());
    }
}
