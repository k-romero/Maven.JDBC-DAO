package daos;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DaoClassTest {

    DaoClass test;

    @Before
    public void setup(){
        test = new DaoClass();
    }

    @Test
    public void testConnection(){
        DaoClass.getConnection();
    }

    @Test
    public void findBy(){
        DtoClass actual = test.findById(1);
        assertEquals(actual.getMake(),"Saturn");
    }

    @Test
    public void findAllTest(){
        List<DtoClass> actual = test.findAll();

        assertEquals(actual.get(0).getMake(),"Saturn");
    }

    @Test
    public void createTest(){
        DtoClass newcar = new DtoClass(51,"Volkswagen","Jetta SEL",2019,"Black");
        assertTrue(test.create(newcar));
    }

    @Test
    public void updateTest(){
        DtoClass newcar = new DtoClass(51,"Volkswagen","Jetta SEL",2019,"Grey");
        assertTrue(test.update(newcar));
    }

    @Test
    public void deleteTest(){
        test.delete(51);
    }
}
