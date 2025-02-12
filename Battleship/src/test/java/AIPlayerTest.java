import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AIPlayerTest {
    AIPlayer mockAIPlayer = new AIPlayer();
    Player mockOpponent = new HumanPlayer();
    public void AI_player_stores_row_of_a_ship_following_first_hit_and_no_current_target_ship(){
        int row = 5;
        int col = 5;
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //attack(int col, int row, Player targetPlayer)
        //int actual_row = mockAIPlayer.rowOfShipDiscovery;
        //assertEquals(expected_row, actual_row);
    }
    public void AI_player_stores_col_of_a_ship_following_first_hit_and_no_current_target_ship(){
        int col = 5;
        int row = 5;
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //attack(int col, int row, Player targetPlayer)
        //int actual_col = mockAIPlayer.colOfShipDiscovery;
        //assertEquals(col, actual_col);
    }
    public void AI_player_attack_direction_is_north_upon_initial_enemy_ship_discovery(){
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 'r';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 'n';
        //int actual = mockAIPlayer.attackDirection;
        //assertEquals(expected, actual);
    }
    public void AI_player_attack_direction_switches_to_west_of_enemy_ship_discovery_following_north_coordinate_miss() {
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 'n';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 'w';
        //int actual = mockAIPlayer.attackDirection;
        //assertEquals(expected, actual);
    }
    public void AI_player_attack_direction_switches_to_south_of_enemy_ship_discovery_following_west_coordinate_miss() {
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 'w';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 's';
        //int actual = mockAIPlayer.attackDirection;
        //assertEquals(expected, actual);
    }
    public void AI_player_attack_direction_switches_to_east_of_enemy_ship_discovery_following_south_coordinate_miss() {
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 's';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 'e';
        //int actual = mockAIPlayer.attackDirection;
        //assertEquals(expected, actual);
    }
    public void AI_player_determines_ship_is_vertical_if_attack_hits_directly_north_of_ship_discovery() {
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 'n';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //mockOpponent.bottomBoard.grid[row+1][col] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 'v';
        //int actual = mockAIPlayer.attackOrientation;
        //assertEquals(expected, actual);
    }
    public void AI_player_determines_ship_is_vertical_if_attack_hits_directly_south_of_ship_discovery() {
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 's';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //mockOpponent.bottomBoard.grid[row-1][col] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 'v';
        //int actual = mockAIPlayer.attackOrientation;
        //assertEquals(expected, actual);
    }
    public void AI_player_determines_ship_is_horizontal_if_attack_hits_directly_west_of_ship_discovery() {
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 'w';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //mockOpponent.bottomBoard.grid[row][col-1] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 'h';
        //int actual = mockAIPlayer.attackOrientation;
        //assertEquals(expected, actual);
    }
    public void AI_player_determines_ship_is_horizontal_if_attack_hits_directly_east_of_ship_discovery() {
        int col = 5;
        int row = 5;
        //mockAIPlayer.attackDirection = 'e';
        //mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //mockOpponent.bottomBoard.grid[row][col-1] = "Carrier";
        //attack(col, row, targetPlayer)
        //int expected = 'h';
        //int actual = mockAIPlayer.attackOrientation;
        //assertEquals(expected, actual);
    }
    public void AI_player_attack_miss_on_coordinate_directly_north_of_vertical_ship_switches_attack_intention_to_south() {
    }
    public void AI_player_attack_miss_on_coordinate_directly_south_of_vertical_ship_switches_attack_back_to_random() {
    }
    public void AI_player_attack_miss_on_coordinate_directly_west_of_horizontal_ship_switches_attack_intention_to_east() {
    }
    public void AI_player_attack_miss_on_coordinate_directly_east_of_horizontal_ship_switches_attack_intention_back_to_random() {
    }
}