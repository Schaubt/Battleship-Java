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
        int col = 5;
        int row = 5;
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
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockAIPlayer);
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
        mockAIPlayer.attackOrientation = 'v';
        mockAIPlayer.attackDirection = 'n';
        mockAIPlayer.lastResult = "Miss";
        int[] expected = new int[] {row-1,col};
        int[] actual = mockAIPlayer.determineAttackCoordinate(mockAIPlayer);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void AI_DetermineDirection_LastAttackDidMiss_VerticalEnemyShipDiscovered_AttackingNorth_setDirectionToSouth() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
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
}