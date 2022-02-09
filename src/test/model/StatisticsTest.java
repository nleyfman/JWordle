package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {
    private Statistics testStats;

    @BeforeEach
    void runBefore() {
        testStats = new Statistics();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testStats.getNumWins());
        assertEquals(0, testStats.getNumLosses());
    }

    @Test
    void testAddWin() {
        testStats.addWin();
        assertEquals(1, testStats.getNumWins());
    }

    @Test
    void testAddLoss() {
        testStats.addLoss();
        assertEquals(1, testStats.getNumLosses());
    }
}
