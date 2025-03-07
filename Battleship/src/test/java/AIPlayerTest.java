import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AIPlayerTest {
    AIPlayer mockAIPlayer = new AIPlayer();
    Player mockOpponent = new HumanPlayer();
    @Test
    public void AI_player_stores_row_of_a_ship_following_first_hit_and_no_current_target_ship(){
        int row = 5;
        int col = 5;
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        mockAIPlayer.attack(col, row, mockOpponent);
        int actual_row = mockAIPlayer.rowOfShipDiscovery;
        assertEquals(row, actual_row);
    }
    @Test
    public void AI_player_stores_col_of_a_ship_following_first_hit_and_no_current_target_ship(){
        int col = 5;
        int row = 5;
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.attack(col, row, mockOpponent);
        int actual_col = mockAIPlayer.colOfShipDiscovery;
        assertEquals(col, actual_col);
    }
    @Test
    public void AI_player_attack_direction_is_north_upon_initial_enemy_ship_discovery(){
        mockAIPlayer.attackDirection = 'r';
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.lastResult = "Hit";
        mockAIPlayer.determineAttackDirection();
        int expected = 'n';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_attack_direction_switches_to_west_of_enemy_ship_discovery_following_north_coordinate_miss() {
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.lastResult = "Miss";
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.determineAttackDirection();
        int expected = 'w';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_attack_direction_switches_to_south_of_enemy_ship_discovery_following_west_coordinate_miss() {
        mockAIPlayer.lastResult = "Miss";
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.determineAttackDirection();
        int expected = 's';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_attack_direction_switches_to_east_of_enemy_ship_discovery_following_south_coordinate_miss() {
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.lastResult = "Miss";
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.determineAttackDirection();
        int expected = 'e';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_determines_ship_is_vertical_if_attack_hits_directly_north_of_ship_discovery() {
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.lastResult = "Hit";
        mockAIPlayer.determineAttackOrientation();
        int expected = 'v';
        int actual = mockAIPlayer.attackOrientation;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_determines_ship_is_vertical_if_attack_hits_directly_south_of_ship_discovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.lastResult = "Hit";
        mockAIPlayer.determineAttackOrientation();
        int expected = 'v';
        int actual = mockAIPlayer.attackOrientation;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_determines_ship_is_horizontal_if_attack_hits_directly_west_of_ship_discovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.lastResult = "Hit";
        mockAIPlayer.determineAttackOrientation();
        int expected = 'h';
        int actual = mockAIPlayer.attackOrientation;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_determines_ship_is_horizontal_if_attack_hits_directly_east_of_ship_discovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.lastResult = "Hit";
        mockAIPlayer.determineAttackOrientation();
        int expected = 'h';
        int actual = mockAIPlayer.attackOrientation;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidHit_HorizontalEnemyShipDiscovered_AttackingEast_ReturnCoordinateEastOfLastHit() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.colOfLastAttack = col+1;
        mockAIPlayer.rowOfLastAttack = row;
        mockAIPlayer.attackOrientation = 'h';
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.lastResult = "Hit";
        int[] expected = new int[] {row,mockAIPlayer.colOfLastAttack+1};
        int[] actual = mockAIPlayer.determineAttackCoordinate();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidHit_VerticalEnemyShipDiscovered_AttackingNorth_ReturnCoordinateNorthOfLastHit() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.colOfLastAttack = col;
        mockAIPlayer.rowOfLastAttack = row+1;
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.lastResult = "Hit";
        int[] expected = new int[] {row+2,col};
        int[] actual = mockAIPlayer.determineAttackCoordinate();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidHit_HorizontalEnemyShipDiscovered_AttackingWest_ReturnCoordinateWestOfLastHit() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.colOfLastAttack = col-1;
        mockAIPlayer.rowOfLastAttack = row;
        mockAIPlayer.attackOrientation = 'h';
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.lastResult = "Hit";
        int[] expected = new int[] {row,mockAIPlayer.colOfLastAttack-1};
        int[] actual = mockAIPlayer.determineAttackCoordinate();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidHit_VerticalEnemyShipDiscovered_AttackingSouth_ReturnCoordinateSouthOfLastHit() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.colOfLastAttack = col;
        mockAIPlayer.rowOfLastAttack = row-1;
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.lastResult = "Hit";
        int[] expected = new int[] {mockAIPlayer.rowOfLastAttack-1,col};
        int[] actual = mockAIPlayer.determineAttackCoordinate();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidMiss_HorizontalEnemyShipDiscovered_AttackingEast_ReturnCoordinateWestOfShipDiscovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.rowOfLastAttack = row;
        mockAIPlayer.attackOrientation = 'h';
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.lastResult = "Miss";
        int[] expected = new int[] {row,mockAIPlayer.colOfShipDiscovery-1};
        int[] actual = mockAIPlayer.determineAttackCoordinate();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidMiss_VerticalEnemyShipDiscovered_AttackingNorth_ReturnCoordinateSouthOfShipDiscovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.lastResult = "Miss";
        int[] expected = new int[] {row-1,col};
        int[] actual = mockAIPlayer.determineAttackCoordinate();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineDirection_LastAttackDidMiss_VerticalEnemyShipDiscovered_AttackingNorth_setDirectionToSouth() {
        int col = 5;
        mockAIPlayer.rowOfShipDiscovery = 5;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.lastResult = "Miss";
        char expected = 's';
        mockAIPlayer.determineAttackDirection();
        char actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_DetermineDirection_LastAttackDidMiss_HorizontalEnemyShipDiscovered_AttackDirectionEast_setDirectionToWest() {
        int col = 5;
        mockAIPlayer.rowOfShipDiscovery = 5;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'h';
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.lastResult = "Miss";
        char expected = 'w';
        mockAIPlayer.determineAttackDirection();
        char actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_DetermineDirection_LastAttackDidMiss_HorizontalEnemyShipDiscovered_AttackDirectionWest_setDirectionToRandom() {
        int col = 5;
        mockAIPlayer.rowOfShipDiscovery = 5;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'h';
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.lastResult = "Miss";
        char expected = 'r';
        mockAIPlayer.determineAttackDirection();
        char actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_DetermineDirection_LastAttackDidMiss_VerticalEnemyShipDiscovered_AttackDirectionSouth_setDirectionToRandom() {
        int col = 5;
        mockAIPlayer.rowOfShipDiscovery = 5;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.lastResult = "Miss";
        char expected = 'r';
        mockAIPlayer.determineAttackDirection();
        char actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void set_discovery_coordinates_to_match_argument_row(){
        int expected = 5;
        int col = 7;
        mockAIPlayer.setShipDiscoveryCoordinates(expected, col);
        int actual = mockAIPlayer.rowOfShipDiscovery;
        assertEquals(expected, actual);
    }
    @Test
    public void set_discovery_coordinates_to_match_argument_col(){
        int row = 5;
        int expected = 7;
        mockAIPlayer.setShipDiscoveryCoordinates(row, expected);
        int actual = mockAIPlayer.colOfShipDiscovery;
        assertEquals(expected, actual);
    }
    @Test
    public void set_orientation_to_match_argument(){
        char expected = 'h';
        mockAIPlayer.setAttackOrientation(expected);
        char actual = mockAIPlayer.attackOrientation;
        assertEquals(expected, actual);
    }
    @Test
    public void set_attack_direction_to_match_argument(){
        char expected = 'n';
        mockAIPlayer.setAttackDirection(expected);
        char actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void attackDirectionIsEastOrWest_DirectionIsNorth_ReturnFalse(){
        mockAIPlayer.attackDirection = 'n';
        boolean actual = mockAIPlayer.attackDirectionIsEastOrWest();
        assertFalse(actual);
    }
    @Test
    public void attackDirectionIsEastOrWest_DirectionIsSouth_ReturnFalse(){
        mockAIPlayer.attackDirection = 's';
        boolean actual = mockAIPlayer.attackDirectionIsEastOrWest();
        assertFalse(actual);
    }
    @Test
    public void attackDirectionIsNorthOrSouth_DirectionIsEast_ReturnFalse(){
        mockAIPlayer.attackDirection = 'e';
        boolean actual = mockAIPlayer.attackDirectionIsNorthOrSouth();
        assertFalse(actual);
    }
    @Test
    public void attackDirectionIsNorthOrSouth_DirectionIsWest_ReturnFalse(){
        mockAIPlayer.attackDirection = 'w';
        boolean actual = mockAIPlayer.attackDirectionIsNorthOrSouth();
        assertFalse(actual);
    }
    @Test
    public void attackDirectionIsEastOrWest_DirectionIsEast_ReturnTrue(){
        mockAIPlayer.attackDirection = 'e';
        boolean actual = mockAIPlayer.attackDirectionIsEastOrWest();
        assertTrue(actual);
    }
    @Test
    public void attackDirectionIsEastOrWest_DirectionIsWest_ReturnTrue(){
        mockAIPlayer.attackDirection = 'w';
        boolean actual = mockAIPlayer.attackDirectionIsEastOrWest();
        assertTrue(actual);
    }
    @Test
    public void attackDirectionIsNorthOrSouth_DirectionIsNorth_ReturnTrue(){
        mockAIPlayer.attackDirection = 'n';
        boolean actual = mockAIPlayer.attackDirectionIsNorthOrSouth();
        assertTrue(actual);
    }
    @Test
    public void attackDirectionIsNorthOrSouth_DirectionIsSouth_ReturnTrue(){
        mockAIPlayer.attackDirection = 's';
        boolean actual = mockAIPlayer.attackDirectionIsNorthOrSouth();
        assertTrue(actual);
    }
    @Test
    public void ship_probe_status_toggles_to_false_when_true(){
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.toggleShipProbeStatus();
        boolean actual = mockAIPlayer.shipProbeInProgress;
        assertFalse(actual);
    }
    @Test
    public void ship_probe_status_toggles_to_true_when_false(){
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.toggleShipProbeStatus();
        boolean actual = mockAIPlayer.shipProbeInProgress;
        assertTrue(actual);
    }

    @Test
    public void attackIntentionIsWestAndHorizontal_DirectionIsNotWest_ReturnsFalse(){
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.attackIntentionIsWestAndHorizontal();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsWestAndHorizontal_OrientationIsNotHorizontal_ReturnsFalse(){
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.attackOrientation = 'v';
        boolean actual = mockAIPlayer.attackIntentionIsWestAndHorizontal();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsWestAndHorizontal_DirectionIsWest_OrientationIsHorizontal_ReturnsTrue(){
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.attackIntentionIsWestAndHorizontal();
        assertTrue(actual);
    }
    @Test
    public void attackIntentionIsEastAndHorizontal_DirectionIsNotEast_ReturnsFalse(){
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.attackIntentionIsEastAndHorizontal();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsEastAndHorizontal_OrientationIsNotHorizontal_ReturnsFalse(){
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.attackOrientation = 'v';
        boolean actual = mockAIPlayer.attackIntentionIsEastAndHorizontal();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsEastAndHorizontal_DirectionIsEast_OrientationIsHorizontal_ReturnsTrue(){
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.attackIntentionIsEastAndHorizontal();
        assertTrue(actual);
    }
    @Test
    public void attackIntentionIsNorthAndVertical_DirectionIsNotNorth_ReturnsFalse(){
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.attackOrientation = 'v';
        boolean actual = mockAIPlayer.attackIntentionIsNorthAndVertical();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsNorthAndVertical_DirectionIsNotVertical_ReturnsFalse(){
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.attackIntentionIsNorthAndVertical();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsNorthAndVertical_DirectionIsNorth_OrientationIsVertical_ReturnsTrue(){
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.attackOrientation = 'v';
        boolean actual = mockAIPlayer.attackIntentionIsNorthAndVertical();
        assertTrue(actual);
    }
    @Test
    public void attackIntentionIsSouthAndVertical_DirectionIsNotSouth_ReturnsFalse(){
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.attackOrientation = 'v';
        boolean actual = mockAIPlayer.attackIntentionIsSouthAndVertical();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsSouthAndVertical_DirectionIsNotVertical_ReturnsFalse(){
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.attackIntentionIsSouthAndVertical();
        assertFalse(actual);
    }
    @Test
    public void attackIntentionIsSouthAndVertical_DirectionIsSouth_OrientationIsVertical_ReturnsTrue(){
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.attackOrientation = 'v';
        boolean actual = mockAIPlayer.attackIntentionIsSouthAndVertical();
        assertTrue(actual);
    }
    @Test
    public void getNorthCoordinate_ReturnsIncrementedRow(){
        int row = 5;
        int col = 5;
        int expected = row+1;
        int actual = mockAIPlayer.getNorthCoordinate(row, col)[0];
        assertEquals(expected, actual);
    }
    @Test
    public void getNorthCoordinate_ReturnsUnchangedColumn(){
        int row = 5;
        int col = 5;
        int actual = mockAIPlayer.getNorthCoordinate(row, col)[1];
        assertEquals(col, actual);
    }
    @Test
    public void getSouthCoordinate_ReturnsDecrementedRow(){
        int row = 5;
        int col = 5;
        int expected = row-1;
        int actual = mockAIPlayer.getSouthCoordinate(row, col)[0];
        assertEquals(expected, actual);
    }
    @Test
    public void getSouthCoordinate_ReturnsUnchangedColumn(){
        int row = 5;
        int col = 5;

        int expected = row-1;
        int actual = mockAIPlayer.getSouthCoordinate(row, col)[0];
        assertEquals(expected, actual);
    }
    @Test
    public void getEastCoordinate_ReturnsIncrementedColumn(){
        int row = 5;
        int col = 5;
        int expected = col+1;
        int actual = mockAIPlayer.getEastCoordinate(row, col)[1];
        assertEquals(expected, actual);
    }
    @Test
    public void getWestCoordinate_ReturnsDecrementedColumn(){
        int row = 5;
        int col = 5;
        int expected = col-1;
        int actual = mockAIPlayer.getWestCoordinate(row, col)[1];
        assertEquals(expected, actual);
    }
    @Test
    public void handleMiss_ShipAttackInProgress_attackIntentionIsWestAndHorizontal_ResetDiscoveryCoordinates(){
        int row = 5;
        int col = 5;
        mockAIPlayer.attackDirection = 'w';
        mockAIPlayer.attackOrientation = 'h';
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.handleMiss();
        int[] expected = new int[]{-1,-1};
        int[] actual = new int[]{mockAIPlayer.rowOfShipDiscovery, mockAIPlayer.colOfShipDiscovery};
        assertArrayEquals(expected, actual);
    }
    @Test
    public void handleMiss_ShipAttackInProgress_attackIntentionIsSouthAndVertical_ResetDiscoveryCoordinates(){
        int row = 5;
        int col = 5;
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.handleMiss();
        int[] expected = new int[]{-1,-1};
        int[] actual = new int[]{mockAIPlayer.rowOfShipDiscovery, mockAIPlayer.colOfShipDiscovery};
        assertArrayEquals(expected, actual);
    }
    @Test
    public void handleMiss_ShipAttackNotInProgress_attackIntentionIsSouthAndVertical_DoNotChangeDiscoveryCoordinates(){
        int row = 5;
        int col = 5;
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.handleMiss();
        int[] expected = new int[]{row,col};
        int[] actual = new int[]{mockAIPlayer.rowOfShipDiscovery, mockAIPlayer.colOfShipDiscovery};
        assertArrayEquals(expected, actual);
    }
    @Test
    public void handleMiss_ShipAttackInProgress_attackIntentionIsNOYSouthAndVerticalOr_WestAndHorizontal_DoNotChangeDiscoveryCoordinates(){
        int row = 5;
        int col = 5;
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.handleMiss();
        int[] expected = new int[]{row,col};
        int[] actual = new int[]{mockAIPlayer.rowOfShipDiscovery, mockAIPlayer.colOfShipDiscovery};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void handleAIAttack_LastAttackDidHit_InvokeHandleHit() {
        int row = 5;
        int col = 5;
        mockOpponent.bottomBoard.grid[row][col] = "Hit";
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        spyAIPlayer.handleAIAttack(col, row, mockOpponent);
        verify(spyAIPlayer, times(1)).handleHit(row, col);
    }
    @Test
    public void handleAIAttack_LastAttackDidMiss_InvokeHandleMiss() {
        int row = 5;
        int col = 5;
        mockOpponent.bottomBoard.grid[row][col] = "Miss";
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        spyAIPlayer.handleAIAttack(col, row, mockOpponent);
        verify(spyAIPlayer, times(1)).handleMiss();
    }
    @Test
    public void handleAIAttack_LastAttackIsNull_DoNotInvokeNeitherHandleMissNotHandleHit() {
        int row = 5;
        int col = 5;
        mockOpponent.bottomBoard.grid[row][col] = "@";
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        spyAIPlayer.handleAIAttack(col, row, mockOpponent);
        verify(spyAIPlayer, never()).handleMiss();
        verify(spyAIPlayer, never()).handleHit(row, col);
    }
    @Test
    public void handleHit_InvokesToggleShipProbeStatus(){
        int row = 5;
        int col = 5;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        spyAIPlayer.handleHit(row, col);
        verify(spyAIPlayer, times(1)).toggleShipProbeStatus();
    }
    @Test
    public void handleHit_ShipProbeNotInProgress_InvokeSetShipDiscoveryCoordinates(){
        int row = 5;
        int col = 5;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        spyAIPlayer.handleHit(row, col);
        verify(spyAIPlayer, times(1)).setShipDiscoveryCoordinates(row, col);
    }
    @Test
    public void handleHit_ShipProbeInProgress_DoNotInvokeSetShipDiscoveryCoordinates(){
        int row = 5;
        int col = 5;
        mockAIPlayer.shipProbeInProgress = true;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        spyAIPlayer.handleHit(row, col);
        verify(spyAIPlayer, never()).setShipDiscoveryCoordinates(row, col);
    }
    @Test
    public void handleMiss_ShipAttackInProgress_IntentionIsVerticalAndSouth_InvokeSetShipDiscoveryCoordinatesToReset(){
        int row = -1, col = -1;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(true);
        Mockito.when(spyAIPlayer.attackIntentionIsSouthAndVertical()).thenReturn(true);

        spyAIPlayer.handleMiss();
        verify(spyAIPlayer, times(1)).setShipDiscoveryCoordinates(row, col);
    }
    @Test
    public void handleMiss_ShipAttackInProgress_IntentionIsHorizontalAndWest_InvokeSetShipDiscoveryCoordinatesToReset(){
        int row = -1, col = -1;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(true);
        Mockito.when(spyAIPlayer.attackIntentionIsWestAndHorizontal()).thenReturn(true);

        spyAIPlayer.handleMiss();
        verify(spyAIPlayer, times(1)).setShipDiscoveryCoordinates(row, col);
    }

    @Test
    public void handleMiss_ShipAttackInProgress_IntentionIsNOTHorizontalAndWestNorVerticalAndSouth_DoNotInvokeSetShipDiscoveryCoordinatesToReset(){
        int row = -1, col = -1;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(true);
        Mockito.when(spyAIPlayer.attackIntentionIsWestAndHorizontal()).thenReturn(false);
        Mockito.when(spyAIPlayer.attackIntentionIsSouthAndVertical()).thenReturn(false);

        spyAIPlayer.handleMiss();
        verify(spyAIPlayer, never()).setShipDiscoveryCoordinates(row, col);
    }
    @Test
    public void handleMiss_ShipAttackNotInProgress_IntentionIsHorizontalAndWest_DoNotInvokeSetShipDiscoveryCoordinatesToReset(){
        int row = -1, col = -1;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(false);
        Mockito.when(spyAIPlayer.attackIntentionIsWestAndHorizontal()).thenReturn(true);

        spyAIPlayer.handleMiss();
        verify(spyAIPlayer, never()).setShipDiscoveryCoordinates(row, col);
    }
    @Test
    public void handleMiss_ShipAttackNotInProgress_IntentionIsVerticalAndSouth_DoNotInvokeSetShipDiscoveryCoordinatesToReset(){
        int row = -1, col = -1;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(false);
        Mockito.when(spyAIPlayer.attackIntentionIsSouthAndVertical()).thenReturn(true);

        spyAIPlayer.handleMiss();
        verify(spyAIPlayer, never()).setShipDiscoveryCoordinates(row, col);
    }
    @Test
    public void getAttackCoordinate_AttackDirectionIsEast_AttackOrientationIsHorizontal_InvokeGetEastCoordinate(){

        int row = 5, col = 5;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.attackIntentionIsEastAndHorizontal()).thenReturn(true);
        spyAIPlayer.getAttackCoordinate(row, col);
        verify(spyAIPlayer, times(1)).getEastCoordinate(row, col);
    }
    @Test
    public void getAttackCoordinate_ShipAttackInProgress_AttackDirectionIsWest_AttackOrientationIsHorizontal_InvokeGetWestCoordinate(){

        int row = 5, col = 5;
        mockAIPlayer.shipProbeInProgress = true;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.attackIntentionIsWestAndHorizontal()).thenReturn(true);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(true);
        spyAIPlayer.getAttackCoordinate(row, col);
        verify(spyAIPlayer, times(1)).getWestCoordinate(row, col);
    }
    @Test
    public void getAttackCoordinate_AttackDirectionIsNorth_AttackOrientationIsVertical_InvokeGetNorthCoordinate(){
        int row = 5, col = 5;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.attackIntentionIsNorthAndVertical()).thenReturn(true);
        spyAIPlayer.getAttackCoordinate(row, col);
        verify(spyAIPlayer, times(1)).getNorthCoordinate(row, col);
    }
    @Test
    public void getAttackCoordinate_ShipAttackInProgress_AttackDirectionIsSouth_AttackOrientationIsVertical_InvokeGetSouthCoordinate(){
        int row = 5, col = 5;
        mockAIPlayer.shipProbeInProgress = true;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.attackIntentionIsSouthAndVertical()).thenReturn(true);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(true);
        spyAIPlayer.getAttackCoordinate(row, col);
        verify(spyAIPlayer, times(1)).getSouthCoordinate(row, col);
    }
    @Test
    public void getAttackCoordinate_AttackDirectionIsSouth_AttackOrientationIsVertical_InvokeGetRandomCoordinate(){
        int row = 5, col = 5;
        mockAIPlayer.shipProbeInProgress = true;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.attackIntentionIsSouthAndVertical()).thenReturn(true);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(false);
        spyAIPlayer.getAttackCoordinate(row, col);
        verify(spyAIPlayer, times(1)).getRandomCoordinate();
    }
    @Test
    public void getAttackCoordinate_AttackDirectionIsWest_AttackOrientationIsHorizontal_InvokeGetRandomCoordinate() {
        int row = 5, col = 5;
        mockAIPlayer.shipProbeInProgress = true;
        AIPlayer spyAIPlayer = Mockito.spy(mockAIPlayer);
        Mockito.when(spyAIPlayer.attackIntentionIsSouthAndVertical()).thenReturn(true);
        Mockito.when(spyAIPlayer.shipAttackInProgress()).thenReturn(false);
        spyAIPlayer.getAttackCoordinate(row, col);
        verify(spyAIPlayer, times(1)).getRandomCoordinate();
    }

    @Test
    public void ShipAttackInProgress_ShipProbeNotInProgress_ShipDiscoveryCoordinatesExist_AttackOrientationIsNotRandom_ReturnTrue(){
        int row = 5, col = 5;
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.shipAttackInProgress();
        assertTrue(actual);
    }
    @Test
    public void ShipAttackInProgress_shipProbeInProgress_ReturnFalse(){
        int row = 5, col = 5;
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.shipAttackInProgress();
        assertFalse(actual);
    }
    @Test
    public void ShipAttackInProgress_DiscoveryRowCoordinateDoesNotExist_ReturnFalse(){
        int col = 5;
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.rowOfShipDiscovery = -1;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.shipAttackInProgress();
        assertFalse(actual);
    }
    @Test
    public void ShipAttackInProgress_DiscoveryColCoordinateDoesNotExist_ReturnFalse(){
        int row = 5;
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = -1;
        mockAIPlayer.attackOrientation = 'h';
        boolean actual = mockAIPlayer.shipAttackInProgress();
        assertFalse(actual);
    }
    @Test
    public void ShipAttackInProgress_attackOrientationIsRandom_ReturnFalse(){
        int row = 5, col = 5;
        mockAIPlayer.shipProbeInProgress = false;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'r';
        boolean actual = mockAIPlayer.shipAttackInProgress();
        assertFalse(actual);
    }
}