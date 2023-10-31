package g56514.stibride.repository;

import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.dto.StopDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.StopDao;
import g56514.stibride.gestionDB.repository.StopRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author yohan
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopRepositoryTest {

    @Mock
    private StopDao mock;

    private final StopDto stop1;
    private final StopDto stop2;
    private static final int KEY = 12_345;
    private final List<StopDto> all;

    public StopRepositoryTest() {
        System.out.println("StopRepositoryTest constructor");
        //Test data
        stop1 = new StopDto(1, KEY, 10);
        stop2 = new StopDto(1, 99_999, 10);
        all = new ArrayList<>();
        all.add(stop1);
        all.add(stop2);
    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.select(stop1.getKey())).thenReturn(stop1);
        Mockito.lenient().when(mock.select(stop2.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        StopDto expected = stop1;
        StopRepository repository = new StopRepository(mock);
        //Action
        StopDto result = repository.get(new Pair(stop1.getKey().getFirst(), KEY));
        //Assert        
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(new Pair(stop1.getKey().getFirst(), KEY));
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("testGetNotExist");
        //Arrange
        StopRepository repository = new StopRepository(mock);
        //Action
        StopDto result = repository.get(new Pair(stop2.getKey().getFirst(), stop2.getKey().getSecond()));
        //Assert        
        assertNull(result);
        Mockito.verify(mock, times(1)).select(new Pair(stop2.getKey().getFirst(), stop2.getKey().getSecond()));
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        System.out.println("testGetIncorrectParameter");
        //Arrange
        StopRepository repository = new StopRepository(mock);
        Pair incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(1)).select(incorrect);
    }

    @Test
    public void testSelectAll() throws Exception {
        System.out.println("testSelectAll");
        //Arrange
        int expected = 2; //because there are 2 datas
        StopRepository repository = new StopRepository(mock);
        //Action
        List<StopDto> result = repository.getAll();
        //Assert        
        assertEquals(expected, result.size());
        Mockito.verify(mock, times(1)).selectAll();
    }

}
