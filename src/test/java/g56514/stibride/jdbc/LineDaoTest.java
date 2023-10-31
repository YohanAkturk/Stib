package g56514.stibride.jdbc;

import g56514.stibride.gestionDB.config.ConfigManager;
import g56514.stibride.gestionDB.dto.LineDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.LineDao;
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
public class LineDaoTest {

    private LineDao instance;

    private final LineDto line1;
    private final LineDto line2;
    private static final int KEY = 1;
    private final List<LineDto> all;

    public LineDaoTest() throws SQLException, IOException, RepositoryException {
        System.out.println("==== FavoriDaoTest Constructor =====");
        line1 = new LineDto(KEY);
        line2 = new LineDto(99_999);

        all = new ArrayList<>();
        all.add(line1);
        all.add(line2);

        try {
            ConfigManager.getInstance().load();
            instance = LineDao.getInstance();
        } catch (RepositoryException | IOException ex) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", ex);
        }
    }
    
    

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Arrange
        LineDto expected = line1;
        //Action
        LineDto result = instance.select(KEY);
        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Arrange
        //Action
        LineDto result = instance.select(line2.getKey());
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
        int expected = 4; //because there are 4 datas
        //Action
        List<LineDto> result = instance.selectAll();
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        //Assert        
        assertEquals(expected, result.size());
    }

}
