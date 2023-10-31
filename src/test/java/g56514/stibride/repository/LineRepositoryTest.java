package g56514.stibride.repository;

import g56514.stibride.gestionDB.dto.LineDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.LineDao;
import g56514.stibride.gestionDB.repository.LineRepository;
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
public class LineRepositoryTest {

    @Mock
    private LineDao mock;

    private final LineDto line1;
    private final LineDto line2;
    private static final int KEY = 1;
    private final List<LineDto> all;

    public LineRepositoryTest() {
        System.out.println("StopRepositoryTest constructor");
        //Test data
        line1 = new LineDto(KEY);
        line2 = new LineDto(99_999);
        all = new ArrayList<>();
        all.add(line1);
        all.add(line2);
    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.select(KEY)).thenReturn(line1);
        Mockito.lenient().when(mock.select(line2.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        LineDto expected = line1;
        LineRepository repository = new LineRepository(mock);
        //Action
        LineDto result = repository.get(KEY);
        //Assert        
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("testGetNotExist");
        //Arrange
        LineRepository repository = new LineRepository(mock);
        //Action
        LineDto result = repository.get(line2.getKey());
        //Assert        
        assertNull(result);
        Mockito.verify(mock, times(1)).select(line2.getKey());
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        System.out.println("testGetIncorrectParameter");
        //Arrange
        LineRepository repository = new LineRepository(mock);
        Integer incorrect = null;
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
        LineRepository repository = new LineRepository(mock);
        //Action
        List<LineDto> result = repository.getAll();
        //Assert        
        assertEquals(expected, result.size());
        Mockito.verify(mock, times(1)).selectAll();
    }
}
