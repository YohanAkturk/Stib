package g56514.stibride.repository;

import g56514.stibride.gestionDB.dto.FavoriDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.FavoriDao;
import g56514.stibride.gestionDB.repository.FavoriRepository;
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
import static org.mockito.ArgumentMatchers.any;
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
public class FavoriRepositoryTest {

    @Mock
    private FavoriDao mock;

    private final FavoriDto favori1;
    private final FavoriDto favori2;
    private static final int KEY = 12_345;
    private final List<FavoriDto> all;

    public FavoriRepositoryTest() {
        System.out.println("FavoriRepositoryTest constructor");
        //Test data
        favori1 = new FavoriDto(KEY, "school", 8012, 8022);
        favori2 = new FavoriDto(99_999, "sport", 8432, 8232);

        all = new ArrayList<>();
        all.add(favori1);
        all.add(favori2);
    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.select(favori1.getKey())).thenReturn(favori1);
        Mockito.lenient().when(mock.select(favori2.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectWithName(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.deleteWithName(favori1.getName())).thenReturn(1);
        Mockito.lenient().when(mock.deleteWithName(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.updateForMockito(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.updateForMockito(new FavoriDto(KEY, "school_1", 8032, 8042))).thenReturn(1);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        FavoriDto expected = favori1;
        FavoriRepository repository = new FavoriRepository(mock);
        //Action
        FavoriDto result = repository.get(KEY);
        //Assert        
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("testGetNotExist");
        //Arrange
        FavoriRepository repository = new FavoriRepository(mock);
        //Action
        FavoriDto result = repository.get(favori2.getKey());
        //Assert        
        assertNull(result);
        Mockito.verify(mock, times(1)).select(favori2.getKey());
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        System.out.println("testGetIncorrectParameter");
        //Arrange
        FavoriRepository repository = new FavoriRepository(mock);
        String incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.getWithNameOfFavori(incorrect);
        });
        Mockito.verify(mock, times(1)).selectWithName(null);
    }

    @Test
    public void testAddWhenExisting() throws Exception {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriRepository repository = new FavoriRepository(mock);
        //Action
        repository.add(favori1);
        //Assert       
        Mockito.verify(mock, times(1)).insert(any(FavoriDto.class));
    }

    @Test
    public void testDeleteWhenExisting() throws Exception {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriRepository repository = new FavoriRepository(mock);
        //Action
        repository.deleteWithName(favori1.getName());
        //Assert       
        Mockito.verify(mock, times(1)).deleteWithName(favori1.getName());
    }

    @Test
    public void testDeleteIncorrectParameter() throws Exception {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriRepository repository = new FavoriRepository(mock);
        String incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.deleteWithName(incorrect);
        });
        Mockito.verify(mock, times(1)).deleteWithName(null);
    }

    @Test
    public void testUpdateWhenExisting() throws Exception {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriRepository repository = new FavoriRepository(mock);
        int expected = 1; //because one row must be updated
        //Action
        int result = repository.updateForMockito(new FavoriDto(KEY, "school_1", 8032, 8042));
        //Assert       
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).updateForMockito(new FavoriDto(KEY, "school_1", 8032, 8042));
    }

    @Test
    public void testUpdateIncorrectParameter() throws Exception {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriRepository repository = new FavoriRepository(mock);
        FavoriDto incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.updateForMockito(incorrect);
        });
        Mockito.verify(mock, times(1)).updateForMockito(null);
    }
    
    @Test
    public void testSelectAll() throws Exception {
        System.out.println("testSelectAll");
        //Arrange
        int expected = 2; //because there are 2 datas
        FavoriRepository repository = new FavoriRepository(mock);
        //Action
        List<FavoriDto> result = repository.getAll();
        //Assert        
        assertEquals(expected, result.size());
        Mockito.verify(mock, times(1)).selectAll();
    }

}
