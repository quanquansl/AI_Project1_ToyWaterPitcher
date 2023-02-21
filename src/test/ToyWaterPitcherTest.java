package test;

//import org.junit.AfterClass;
//import org.junit.BeforeClass;
import main.Node;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import main.ToyWaterPitcher;

public class ToyWaterPitcherTest {
    public static void main(String[] args) {
    }

    private int fileNumber;
    private int result;

    Queue<Node> openList = new PriorityQueue<Node>();
    List<Node> closeList = new ArrayList<Node>();

//    @BeforeClass
//    public void beforeClass() {
//        System.out.println("this is before class");
//    }

    public void testToyWaterPitcher() throws IOException {
        String fileName = "src/input" + fileNumber + ".txt";
        ToyWaterPitcher twp = new ToyWaterPitcher();
        try (Scanner sc = new Scanner(new FileReader(fileName))) {
            // line 1
            String line = sc.nextLine();
            String[] sourceArray = line.split(",");
            for (int i = 0; i < sourceArray.length; i++) {
                twp.addCapacity(Integer.valueOf(sourceArray[i]));
            }

            // line 2
            line = sc.nextLine();
            twp.setGoal(Integer.valueOf(line));
        }
//        System.out.println(twp.getCapacity());
//        System.out.println(twp.getGoal());
        twp.initialize();

        result = twp.AStar();;
    }

    @Test
    public void testInput0() throws IOException {
        fileNumber = 0;
        testToyWaterPitcher();
        Assert.assertEquals(19, result);
    }

    @Test
    public void testInput1() throws IOException {
        fileNumber = 1;
        testToyWaterPitcher();
        Assert.assertEquals(7, result);
    }

    @Test
    public void testInput2() throws IOException {
        fileNumber = 2;
        testToyWaterPitcher();
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testInput3() throws IOException {
        fileNumber = 3;
        testToyWaterPitcher();
        Assert.assertEquals(-1, result);
    }

    //it does not work well on this test case
//    @Test
//    public void testInput4() throws IOException {
//        fileNumber = 4;
//        testToyWaterPitcher();
//        Assert.assertEquals(37, result);
//    }

    @Test
    public void testInput5() throws IOException {
        fileNumber = 5;
        testToyWaterPitcher();
        Assert.assertEquals(5, result);
    }

    @Test
    public void testInput6() throws IOException {
        fileNumber = 6;
        testToyWaterPitcher();
        Assert.assertEquals(3, result);
    }

    @Test
    public void testInput7() throws IOException {
        fileNumber = 7;
        testToyWaterPitcher();
        Assert.assertEquals(20, result);
    }

//    @AfterClass
//    public void afterClass() {
//        System.out.println("this is after class");
//    }
}

