package g56514.stibride.jdbc;

import g56514.stibride.gestionDB.config.ConfigManager;
import g56514.stibride.gestionDB.dto.FavoriDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.FavoriDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author yohan
 */
public class FavoriDaoTest {

    private FavoriDao instance;

    private final FavoriDto favori1;
    private final FavoriDto favori2;
    private static final int KEY = 1;
    private final List<FavoriDto> all;

    public FavoriDaoTest() throws SQLException {
        System.out.println("==== FavoriDaoTest Constructor =====");
        favori1 = new FavoriDto(1, "test1", 8012, 8032);
        favori2 = new FavoriDto(99_999, "sport", 8432, 8232);

        all = new ArrayList<>();
        all.add(favori1);
        all.add(favori2);

        try {
            ConfigManager.getInstance().load();
            instance = FavoriDao.getInstance();
        } catch (RepositoryException | IOException ex) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", ex);
        }
    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Arrange
        FavoriDto expected = favori1;
        //Action
        FavoriDto result = instance.select(KEY);
        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Arrange
        //Action
        FavoriDto result = instance.select(favori2.getKey());
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
        int expected = 7; //because there are 2 datas
        //Action
        List<FavoriDto> result = instance.selectAll();
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        //Assert        
        assertEquals(expected, result.size());
    }

    /*
    the tests below work if you do them one by one because due to data being lost
    as a result of changes, deletions, updates
     */
//    @Test
//    public void insert() throws Exception {
//        System.out.println("insert");
//        //Arrange
//        int expected = 1; //because there are 2 datas
//        //Action
//        int result = instance.insert(new FavoriDto(0, "daoTest", 8012, 8022));
//        //Assert        
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void update() throws Exception {
//        System.out.println("update");
//        //Arrange
//        int expected = 1; //because there are 2 datas
//        //Action
//        int result = instance.updateForMockito(new FavoriDto(15, "updatedDaoTest", 8012, 8032));
//        //Assert        
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void delete() throws Exception {
//        System.out.println("update");
//        //Arrange
//        int expected = 1; //because there are 2 datas
//        //Action
//        int result = instance.deleteWithName("updatedDaoTest");
//        //Assert        
//        assertEquals(expected, result);
//    }
}
