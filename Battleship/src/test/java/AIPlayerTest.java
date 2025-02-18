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
    public void AI_player_chooses_attack_coordinate_horizontally_right_of_horizontal_ship() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'h';
        int expectedRow = row;
        int expectedCol = col+1;
        //int actualRow =  mockAIPlayer.calcAttackCoordinateRow();
        //int actualCol = mockAIPlayer.calcAttackCoordinateCol();
        //assertEquals(expectedRow, actualRow);
        //assertEquals(expectedCol, actualCol);
    }
    public void AI_player_chooses_attack_coordinate_vertically_above_of_vertical_ship() {
        int col = 5;
        int row = 5;
        mockAIPlayer.rowOfShipDiscovery = row;
        mockAIPlayer.colOfShipDiscovery = col;
        mockAIPlayer.attackOrientation = 'h';
        int expectedRow = row+1;
        int expectedCol = col;
        //int actualRow =  mockAIPlayer.calcAttackCoordinateRow();
        //int actualCol = mockAIPlayer.calcAttackCoordinateCol();
        //assertEquals(expectedRow, actualRow);
        //assertEquals(expectedCol, actualCol);
    }
}