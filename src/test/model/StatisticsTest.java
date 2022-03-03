package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {
    private Statistics testStats;

    @BeforeEach
    void runBefore() {
        testStats = new Statistics(0, 0, new ArrayList<>());
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

    @Test
    void testAddWinStat() {
        testStats.addWinStat(6);
        testStats.addWinStat(3);
        testStats.addWinStat(4);
        assertEquals(3, testStats.getWinStats().size());
        assertEquals(6, testStats.getWinStats().get(0));
        assertEquals(3, testStats.getWinStats().get(1));
        assertEquals(4, testStats.getWinStats().get(2));
    }

    @Test
    void testAverageGuessesEmpty() {
        assertEquals(-1, testStats.averageGuesses());
    }

    @Test
    void testAverageGuessesNotEmpty() {
        testStats.addWinStat(6);
        testStats.addWinStat(3);
        testStats.addWinStat(3);
        assertEquals(4, testStats.averageGuesses());
    }
}
