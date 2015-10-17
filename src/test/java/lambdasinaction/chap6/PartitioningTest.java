/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambdasinaction.chap6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dick
 */
public class PartitioningTest {
    
    public PartitioningTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPartitioningByWithoutExplicitOrdering() {
        
        List<Dish> expectedVeggie = Arrays.asList( new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
                           new Dish("season fruit", true, 120, Dish.Type.OTHER),
                           new Dish("pizza", true, 550, Dish.Type.OTHER));
        
        
        List<Dish> actual = Dish.menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian)).get(true);
        Assert.assertEquals(expectedVeggie, actual );
    }
    
     @Test
    public void testPartitioningBy() {
        
        List<Dish> expectedVeggie = Arrays.asList( new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
                           new Dish("season fruit", true, 120, Dish.Type.OTHER),
                           new Dish("pizza", true, 550, Dish.Type.OTHER)).stream().sorted(Comparator.comparing(Dish::getName)).collect(toList());
        
        
        List<Dish> actual = Dish.menu.stream().sorted(Comparator.comparing(Dish::getName)).collect(Collectors.partitioningBy(Dish::isVegetarian)).get(true);
        Assert.assertEquals(expectedVeggie, actual );
    }
    
    @Test
    public void testMaxCaloriesByVeggie() {
        Dish maxCalVeggie = new Dish("pizza", true, 550, Dish.Type.OTHER);
        Dish maxCalMeat = new Dish("pork", false, 800, Dish.Type.MEAT);
        
        Map<Boolean, Optional<Dish>> actual=  Dish.menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.maxBy(Comparator.comparing(Dish::getCalories))));
        
        Assert.assertEquals(2, actual.size());
       
        Assert.assertEquals(maxCalVeggie, actual.get(true).get());
        Assert.assertEquals(maxCalMeat, actual.get(false).get());
    }
    
     @Test
    public void testMaxCaloriesByVeggieAndThen() {
        Dish maxCalVeggie = new Dish("pizza", true, 550, Dish.Type.OTHER);
        Dish maxCalMeat = new Dish("pork", false, 800, Dish.Type.MEAT);
        
        Map<Boolean, Dish> actual=  Dish.menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)),i->i.get())));
        
        Assert.assertEquals(2, actual.size());
       
        Assert.assertEquals(maxCalVeggie, actual.get(true));
        Assert.assertEquals(maxCalMeat, actual.get(false));
    }
}
