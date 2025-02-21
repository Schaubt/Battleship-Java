import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
        mockAIPlayer.attack(col, row, mockOpponent);
        int actual_col = mockAIPlayer.colOfShipDiscovery;
        assertEquals(col, actual_col);
    }
    @Test
    public void AI_player_attack_direction_is_north_upon_initial_enemy_ship_discovery(){
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 'r';
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        mockAIPlayer.attack(col, row, mockOpponent);
        int expected = 'n';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_attack_direction_switches_to_west_of_enemy_ship_discovery_following_north_coordinate_miss() {
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 'n';
        mockOpponent.bottomBoard.grid[row][col] = null;
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.attack(col, row, mockOpponent);
        int expected = 'w';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_attack_direction_switches_to_south_of_enemy_ship_discovery_following_west_coordinate_miss() {
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 'w';
        mockOpponent.bottomBoard.grid[row][col] = null;
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.attack(col, row, mockOpponent);
        int expected = 's';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_attack_direction_switches_to_east_of_enemy_ship_discovery_following_south_coordinate_miss() {
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 's';
        mockOpponent.bottomBoard.grid[row][col] = null;
        mockAIPlayer.shipProbeInProgress = true;
        mockAIPlayer.attack(col, row, mockOpponent);
        int expected = 'e';
        int actual = mockAIPlayer.attackDirection;
        assertEquals(expected, actual);
    }
    @Test
    public void AI_player_determines_ship_is_vertical_if_attack_hits_directly_north_of_ship_discovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.shipProbeInProgress = true;
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        mockOpponent.bottomBoard.grid[row+1][col] = "Carrier";
        mockAIPlayer.attack(col, row, mockOpponent);
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
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        mockOpponent.bottomBoard.grid[row-1][col] = "Carrier";
        mockAIPlayer.attack(col, row, mockOpponent);
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
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        mockOpponent.bottomBoard.grid[row][col-1] = "Carrier";
        mockAIPlayer.attack(col, row, mockOpponent);
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
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        mockOpponent.bottomBoard.grid[row][col-1] = "Carrier";
        mockAIPlayer.attack(col, row, mockOpponent);
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
        int[] expected = new int[] {row,col+2};
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockOpponent);
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
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockAIPlayer);
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
        int[] expected = new int[] {row,col-2};
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockAIPlayer);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidHit_VerticalEnemyShipDiscovered_AttackingSouth_ReturnCoordinateWestOfLastHit() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.colOfLastAttack = col;
        mockAIPlayer.rowOfLastAttack = row-1;
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 's';
        mockAIPlayer.lastResult = "Hit";
        int[] expected = new int[] {row-2,col};
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockAIPlayer);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidMiss_HorizontalEnemyShipDiscovered_AttackingEast_ReturnCoordinateWestOfShipDiscovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.colOfLastAttack = col+1;
        mockAIPlayer.rowOfLastAttack = row;
        mockAIPlayer.attackOrientation = 'h';
        mockAIPlayer.attackDirection = 'e';
        mockAIPlayer.lastResult = "Miss";
        int[] expected = new int[] {row,col-1};
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockOpponent);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineCoordinate_LastAttackDidMiss_VerticalEnemyShipDiscovered_AttackingNorth_ReturnCoordinateSouthOfShipDiscovery() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.colOfLastAttack = col;
        mockAIPlayer.rowOfLastAttack = row-1;
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.lastResult = "Miss";
        int[] expected = new int[] {row-1,col};
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockAIPlayer);
        assertArrayEquals(expected, actual);
    }
}