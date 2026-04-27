/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.dam.codeoptimization.fantasy;

import es.dam.codeoptimization.PlayerStats;

/**
 * THE CLASS YOU HAVE TO MODIFY
 *
 * @author Boris
 */
public class FantasyCalculator {
    
    /**
     * Calcula la puntuación total del jugador sumando
     * puntos comunes y los específicos según su posición.
     *
     * @param stats estadísticas del jugador
     * @return puntos totales obtenidos
     */

    public static int calculatePoints(PlayerStats stats) {
        return calculateCommonPoints(stats) 
                + calculatePointsByPosition(stats);
    }

    /**
     * Calcula los puntos adicionales en función de la
     * posición del jugador.
     *
     * @param stats estadísticas del jugador
     * @return puntos según la posición
     */
    
    private static int calculatePointsByPosition(PlayerStats stats) {
        final String GOALKEEPER_STRING = "PORTERO";
        final String DEFENCE_STRING = "DEFENSA";
        final String MIDFIELDER_STRING = "MEDIO";
        final String FORWARD_STRING = "DELANTERO";
        int points = 0;
        
        switch (stats.position) {
            case GOALKEEPER_STRING:
                points = calculateGoalkeeperPoints(stats);
                break;
            case DEFENCE_STRING:
                points = calculateDefencePoints(stats);
                break;
            case MIDFIELDER_STRING:
                points = calculateMidfielderPoints(stats);
                break;
            case FORWARD_STRING:
                points = calculateForwardPoints(stats);
                break;
            default:
                break;
        }
        return points;
    }
    
    /**
     * Calcula los puntos comunes a todos los jugadores.
     *
     * @param stats estadísticas del jugador
     * @return puntos comunes obtenidos
     */

    private static int calculateCommonPoints(PlayerStats stats) {
        return calculateMinutesPlayed(stats.minutes)
                + calculateYellowCard(stats.yellowCard)
                + calculateRedCard(stats.redCard)
                + calculateMatchResult(stats.matchResult);
    }

    private static int calculateForwardPoints(PlayerStats stats) {
        return calculateGoals(stats.goals)
                + calculateAssists(stats.assists);
    }

    private static int calculateMidfielderPoints(PlayerStats stats) {
        return calculateGoals(stats.goals)
                + calculateAssists(stats.assists);
    }

    private static int calculateDefencePoints(PlayerStats stats) {
        return calculateGoalsReceived(stats.goalsAgainst) 
                + calculateGoals(stats.goals)
                + calculateAssists(stats.assists);
    }

    private static int calculateGoalkeeperPoints(PlayerStats stats) {
        return calculateGoals(stats.goals)
                + calculateAssists(stats.assists)
                + calculateSaves(stats.saves)
                + calculateGoalsReceived(stats.goalsAgainst);
    }

    private static int calculateAssists(int assists) {
        final int POINTS_PER_ASSIST = 6;

        return assists * POINTS_PER_ASSIST;
    }

    private static int calculateSaves(int saves) {
        final int POINTS_PER_SAVE = 1;

        return saves * POINTS_PER_SAVE;
    }

    private static int calculateMatchResult(char matchResult) {
        final char MATCH_WON = 'G';
        final char MATCH_DRAW = 'E';
        final int POINTS_FOR_WINNING_MATCH = 5;
        final int POINTS_FOR_DRAWING_MATCH = 2;
        final int POINTS_FOR_LOSING_MATCH = 0;
        int matchResultPoints;

        switch (matchResult) {
            case MATCH_WON:
                matchResultPoints = POINTS_FOR_WINNING_MATCH;
                break;
            case MATCH_DRAW:
                matchResultPoints = POINTS_FOR_DRAWING_MATCH;
                break;
            default:
                matchResultPoints = POINTS_FOR_LOSING_MATCH;
                break;
        }
        return matchResultPoints;
    }

    private static int calculateRedCard(boolean redCard) {
        final int POINTS_FOR_RED_CARD = -5;
        int redCardPointsObtained = 0;

        if (redCard) {
            redCardPointsObtained = POINTS_FOR_RED_CARD;
        }        
        
        return redCardPointsObtained;
    }

    private static int calculateYellowCard(boolean yellowCard) {
        final int POINTS_FOR_YELLOW_CARD = -3;
        int yellowCardPointsObtained = 0;

        if (yellowCard) {
            yellowCardPointsObtained = POINTS_FOR_YELLOW_CARD;
        }        
        
        return yellowCardPointsObtained;
    }

    private static int calculateGoalsReceived(int goalsAgainst) {
        final int POINTS_FOR_ZERO_GOALS_RECEIVED = 5;
        final int POINTS_FOR_ONE_GOAL_RECEIVED = 3;
        final int POINTS_FOR_TWO_GOALS_RECEIVED = 1;
        int pointsForGoalsReceived;

        switch (goalsAgainst) {
            case 0:
                pointsForGoalsReceived = POINTS_FOR_ZERO_GOALS_RECEIVED;
                break;
            case 1:
                pointsForGoalsReceived = POINTS_FOR_ONE_GOAL_RECEIVED;
                break;
            case 2:
                pointsForGoalsReceived = POINTS_FOR_TWO_GOALS_RECEIVED;
                break;
            default:
                pointsForGoalsReceived = 0;
                break;
        }
        return pointsForGoalsReceived;
    }

    private static int calculateGoals(int goals) {
        final int POINTS_PER_GOAL = 5;

        return goals * POINTS_PER_GOAL;
    }

    private static int calculateMinutesPlayed(int minutes) {
        final int POINTS_PLAYED_SOME_MINUTES = 3;
        final int POINTS_PLAYED_MOST_MINUTES = 5;
        final int MINUTES_LIMIT_FOR_MOST_MINUTES = 60;
        int pointsForMinutesPlayed;

        if (minutes > 0 && minutes < MINUTES_LIMIT_FOR_MOST_MINUTES) {
            pointsForMinutesPlayed = POINTS_PLAYED_SOME_MINUTES;
        } else if (minutes >= MINUTES_LIMIT_FOR_MOST_MINUTES) {
            pointsForMinutesPlayed = POINTS_PLAYED_MOST_MINUTES;
        } else {
            pointsForMinutesPlayed = 0;
        }
        return pointsForMinutesPlayed;
    }
}
