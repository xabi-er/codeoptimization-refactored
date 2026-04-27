/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.dam.codeoptimization.fantasy;

import es.dam.codeoptimization.PlayerStats;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FantasyCalculatorTest {

    // ==========================================
    // TESTS FOR GOALKEEPER (PORTERO)
    // ==========================================

    @Test
    public void testGoalkeeperIdealMatch() {
        PlayerStats s = new PlayerStats();
        s.position = "PORTERO";
        s.minutes = 90;        // >=60 mins = 5 pts
        s.goals = 0;           // 0 pts
        s.assists = 1;         // 1 * 6 = 6 pts
        s.saves = 5;           // 5 * 1 = 5 pts
        s.goalsAgainst = 0;    // 0 goals = 5 pts
        s.yellowCard = false;  // 0 pts
        s.redCard = false;     // 0 pts
        s.matchResult = 'G';   // Win = 5 pts
        
        // Expected: 5 + 6 + 5 + 5 + 5 = 26
        assertEquals(26, FantasyCalculator.calculatePoints(s), "GK Ideal Match calculation failed");
    }

    @Test
    public void testGoalkeeperDisasterMatch() {
        PlayerStats s = new PlayerStats();
        s.position = "PORTERO";
        s.minutes = 90;        // 5 pts
        s.goals = 0;
        s.assists = 0;
        s.saves = 0;           // 0 pts
        s.goalsAgainst = 4;    // >3 goals = 0 pts
        s.yellowCard = true;   // -3 pts
        s.redCard = true;      // -5 pts
        s.matchResult = 'D';   // Loss = 0 pts
        
        // Expected: 5 + 0 + 0 - 3 - 5 + 0 = -3
        assertEquals(-3, FantasyCalculator.calculatePoints(s), "GK Disaster Match calculation failed");
    }

    @Test
    public void testGoalkeeperEdgeCaseZeroMinutes() {
        PlayerStats s = new PlayerStats();
        s.position = "PORTERO";
        s.minutes = 0;         // 0 mins = 0 pts (from minutes logic)
        s.goals = 0;
        s.assists = 0;
        s.saves = 0;
        s.goalsAgainst = 0;    // Clean sheet = 5 pts (Current logic adds this even if 0 mins played!)
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'G';   // Win = 5 pts (Current logic adds this even if 0 mins played!)
        
        // Expected: 0 + 5 + 5 = 10 
        // Note for Teacher: This is a logic flaw in the dirty code, but the test reflects what the code currently does.
        assertEquals(10, FantasyCalculator.calculatePoints(s), "GK Edge Case 0 minutes calculation failed");
    }

    @Test
    public void testGoalkeeperSubbedEarly() {
        PlayerStats s = new PlayerStats();
        s.position = "PORTERO";
        s.minutes = 45;        // <60 mins = 3 pts
        s.goals = 0;
        s.assists = 0;
        s.saves = 2;           // 2 pts
        s.goalsAgainst = 1;    // 1 goal = 3 pts
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'E';   // Draw = 2 pts
        
        // Expected: 3 + 2 + 3 + 2 = 10
        assertEquals(10, FantasyCalculator.calculatePoints(s), "GK Subbed Early calculation failed");
    }

    @Test
    public void testGoalkeeperScoringGoal() {
        PlayerStats s = new PlayerStats();
        s.position = "PORTERO";
        s.minutes = 90;        // 5 pts
        s.goals = 1;           // 1 * 5 = 5 pts
        s.assists = 0;
        s.saves = 0;
        s.goalsAgainst = 0;    // 5 pts
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'G';   // 5 pts
        
        // Expected: 5 + 5 + 5 + 5 = 20
        assertEquals(20, FantasyCalculator.calculatePoints(s), "GK Scoring Goal calculation failed");
    }

    // ==========================================
    // TESTS FOR DEFENDER (DEFENSA)
    // ==========================================

    @Test
    public void testDefenderIdealMatch() {
        PlayerStats s = new PlayerStats();
        s.position = "DEFENSA";
        s.minutes = 90;        // 5 pts
        s.goals = 1;           // 5 pts
        s.assists = 0;
        s.goalsAgainst = 0;    // 5 pts
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'G';   // 5 pts
        
        // Expected: 5 + 5 + 5 + 5 = 20
        assertEquals(20, FantasyCalculator.calculatePoints(s), "DEF Ideal Match calculation failed");
    }

    @Test
    public void testDefenderDisasterMatch() {
        PlayerStats s = new PlayerStats();
        s.position = "DEFENSA";
        s.minutes = 90;        // 5 pts
        s.goals = 0;
        s.assists = 0;
        s.goalsAgainst = 3;    // >2 goals = 0 pts
        s.yellowCard = false;
        s.redCard = true;      // -5 pts
        s.matchResult = 'D';   // 0 pts
        
        // Expected: 5 + 0 - 5 + 0 = 0
        assertEquals(0, FantasyCalculator.calculatePoints(s), "DEF Disaster Match calculation failed");
    }

    @Test
    public void testDefenderEdgeCaseFiftyNineMinutes() {
        PlayerStats s = new PlayerStats();
        s.position = "DEFENSA";
        s.minutes = 59;        // Boundary: <60 mins = 3 pts
        s.goals = 0;
        s.assists = 0;
        s.goalsAgainst = 2;    // 2 goals = 1 pt
        s.yellowCard = true;   // -3 pts
        s.redCard = false;
        s.matchResult = 'E';   // 2 pts
        
        // Expected: 3 + 1 - 3 + 2 = 3
        assertEquals(3, FantasyCalculator.calculatePoints(s), "DEF Edge Case 59 mins calculation failed");
    }

    @Test
    public void testDefenderSubbedEarlyCleanSheet() {
        PlayerStats s = new PlayerStats();
        s.position = "DEFENSA";
        s.minutes = 30;        // 3 pts
        s.goals = 0;
        s.assists = 0;
        s.goalsAgainst = 0;    // 5 pts
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'G';   // 5 pts
        
        // Expected: 3 + 5 + 5 = 13
        assertEquals(13, FantasyCalculator.calculatePoints(s), "DEF Subbed Early Clean Sheet failed");
    }

    @Test
    public void testDefenderPlaymaker() {
        PlayerStats s = new PlayerStats();
        s.position = "DEFENSA";
        s.minutes = 90;        // 5 pts
        s.goals = 0;
        s.assists = 3;         // 3 * 6 = 18 pts
        s.goalsAgainst = 0;    // 5 pts
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'E';   // 2 pts
        
        // Expected: 5 + 18 + 5 + 2 = 30
        assertEquals(30, FantasyCalculator.calculatePoints(s), "DEF Playmaker calculation failed");
    }

    // ==========================================
    // TESTS FOR MIDFIELDER (MEDIO)
    // ==========================================

    @Test
    public void testMidfielderIdealMatch() {
        PlayerStats s = new PlayerStats();
        s.position = "MEDIO";
        s.minutes = 90;        // 5 pts
        s.goals = 2;           // 2 * 5 = 10 pts
        s.assists = 1;         // 1 * 6 = 6 pts
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'G';   // 5 pts
        
        // Expected: 5 + 10 + 6 + 5 = 26
        assertEquals(26, FantasyCalculator.calculatePoints(s), "MID Ideal Match calculation failed");
    }

    @Test
    public void testMidfielderDisasterMatch() {
        PlayerStats s = new PlayerStats();
        s.position = "MEDIO";
        s.minutes = 45;        // 3 pts
        s.goals = 0;
        s.assists = 0;
        s.yellowCard = true;   // -3 pts
        s.redCard = true;      // -5 pts
        s.matchResult = 'D';   // 0 pts
        
        // Expected: 3 - 3 - 5 + 0 = -5
        assertEquals(-5, FantasyCalculator.calculatePoints(s), "MID Disaster Match calculation failed");
    }

    @Test
    public void testMidfielderEdgeCaseExactlySixty() {
        PlayerStats s = new PlayerStats();
        s.position = "MEDIO";
        s.minutes = 60;        // Boundary: >=60 mins = 5 pts
        s.goals = 0;
        s.assists = 0;
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'E';   // 2 pts
        
        // Expected: 5 + 2 = 7
        assertEquals(7, FantasyCalculator.calculatePoints(s), "MID Edge Case Exactly 60 calculation failed");
    }

    @Test
    public void testMidfielderNoStats() {
        PlayerStats s = new PlayerStats();
        s.position = "MEDIO";
        s.minutes = 90;        // 5 pts
        s.goals = 0;
        s.assists = 0;
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'D';   // 0 pts
        
        // Expected: 5
        assertEquals(5, FantasyCalculator.calculatePoints(s), "MID No Stats calculation failed");
    }

    @Test
    public void testMidfielderAssistKing() {
        PlayerStats s = new PlayerStats();
        s.position = "MEDIO";
        s.minutes = 90;        // 5 pts
        s.goals = 0;
        s.assists = 4;         // 4 * 6 = 24 pts
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'G';   // 5 pts
        
        // Expected: 5 + 24 + 5 = 34
        assertEquals(34, FantasyCalculator.calculatePoints(s), "MID Assist King calculation failed");
    }

    // ==========================================
    // TESTS FOR FORWARD (DELANTERO)
    // ==========================================

    @Test
    public void testForwardHatTrick() {
        PlayerStats s = new PlayerStats();
        s.position = "DELANTERO";
        s.minutes = 90;        // 5 pts
        s.goals = 3;           // 3 * 5 = 15 pts (Forwards get 6 per goal)
        s.assists = 1;         // 1 * 6 = 6 pts (Forwards get 5 per assist)
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'G';   // 5 pts
        
        // Expected: 5 + 15 + 6 + 5 = 31
        assertEquals(31, FantasyCalculator.calculatePoints(s), "FWD Hat Trick calculation failed");
    }

    @Test
    public void testForwardDisasterMatch() {
        PlayerStats s = new PlayerStats();
        s.position = "DELANTERO";
        s.minutes = 90;        // 5 pts
        s.goals = 0;
        s.assists = 0;
        s.yellowCard = false;
        s.redCard = true;      // -5 pts
        s.matchResult = 'D';   // 0 pts
        
        // Expected: 5 - 5 + 0 = 0
        assertEquals(0, FantasyCalculator.calculatePoints(s), "FWD Disaster Match calculation failed");
    }

    @Test
    public void testForwardEdgeCaseOneMinute() {
        PlayerStats s = new PlayerStats();
        s.position = "DELANTERO";
        s.minutes = 1;         // Boundary: 1 min = 3 pts
        s.goals = 0;
        s.assists = 0;
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'D';   // 0 pts
        
        // Expected: 3
        assertEquals(3, FantasyCalculator.calculatePoints(s), "FWD Edge Case 1 minute calculation failed");
    }

    @Test
    public void testForwardSubbedScoring() {
        PlayerStats s = new PlayerStats();
        s.position = "DELANTERO";
        s.minutes = 45;        // 3 pts
        s.goals = 2;           // 2 * 6 = 12 pts
        s.assists = 0;
        s.yellowCard = false;
        s.redCard = false;
        s.matchResult = 'E';   // 2 pts
        
        // Expected: 3 + 12 + 2 = 17
        assertEquals(17, FantasyCalculator.calculatePoints(s), "FWD Subbed Scoring calculation failed");
    }

    @Test
    public void testForwardAggressive() {
        PlayerStats s = new PlayerStats();
        s.position = "DELANTERO";
        s.minutes = 90;        // 5 pts
        s.goals = 1;           // 1 * 6 = 6 pts
        s.assists = 0;
        s.yellowCard = true;   // -3 pts
        s.redCard = true;      // -5 pts
        s.matchResult = 'G';   // 5 pts
        
        // Expected: 5 + 6 - 3 - 5 + 5 = 8
        assertEquals(8, FantasyCalculator.calculatePoints(s), "FWD Aggressive Player calculation failed");
    }
}
