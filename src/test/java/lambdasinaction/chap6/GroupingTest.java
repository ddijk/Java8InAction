/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambdasinaction.chap6;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dick
 */
public class GroupingTest {

    public GroupingTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMain() {

        Map<Dish.Type, List<Dish>> dishByType = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType));

        Assert.assertEquals(2, dishByType.get(Dish.Type.FISH).size());
        Assert.assertEquals(3, dishByType.get(Dish.Type.MEAT).size());
        Assert.assertEquals(4, dishByType.get(Dish.Type.OTHER).size());
    }
    
      @Test
    public void testHighestCalorieDish() {

        Map<Dish.Type, List<Dish>> dishByType = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType));
        
        Dish expected = new Dish("pork", false, 800, Dish.Type.MEAT);
        final Optional<Dish>  collect = Dish.menu.stream().collect(maxBy(Comparator.comparing(Dish::getCalories)));

        Assert.assertEquals(expected, collect.get());
       
    }
    
       @Test
    public void testLowestCalorieDish() {

        Map<Dish.Type, List<Dish>> dishByType = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType));
        
        Dish expected =new Dish("season fruit", true, 120, Dish.Type.OTHER);
        final Optional<Dish>  collect = Dish.menu.stream().collect(minBy(Comparator.comparing(Dish::getCalories)));

        Assert.assertEquals(expected, collect.get());
       
    }

}
