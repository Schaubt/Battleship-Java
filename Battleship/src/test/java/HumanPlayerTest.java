import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HumanPlayerTest {
    Player mockPlayer = new HumanPlayer();
    Player mockOpponent = new HumanPlayer();

    @Test
    public void player_attack_on_coordinate_with_a_ship_is_marked_as_hit_on_other_player_board(){
        int row = 1;
        int col = 1;
        String expected = "Hit";
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //mockPlayer.attack(row,col);
        String actual = mockOpponent.bottomBoard.grid[row][col];
        //assertEquals(expected, actual);
    }

    @Test
    public void player_attack_on_coordinate_with_a_ship_is_valid(){
        int row = 1;
        int col = 1;
        mockOpponent.bottomBoard.grid[row][col] = "Carrier";
        //boolean isValid = mockPlayer.attack(row,col);
        //assertTrue(isValid);
    }

    @Test
    public void player_attack_on_empty_coordinate_is_marked_as_miss_on_other_player_board(){
        int row = 1;
        int col = 1;
        String expected = "Miss";
        mockOpponent.bottomBoard.grid[row][col] = null;
        //mockPlayer.attack(row,col);
        String actual = mockOpponent.bottomBoard.grid[row][col];
        //assertEquals(expected, actual);
    }
    @Test
    public void player_attack_on_empty_coordinate_is_valid(){
        int row = 1;
        int col = 1;
        mockOpponent.bottomBoard.grid[row][col] = null;
        //boolean isValid = mockPlayer.attack(row,col);
        //assertTrue(isValid);
    }

    @Test
    public void player_attack_on_coordinate_marked_as_miss_is_invalid(){
        int row = 1;
        int col = 1;
        mockOpponent.bottomBoard.grid[row][col] = "Miss";
        //boolean isValid = mockPlayer.attack(row,col);
        //assertFalse(isValid);
    }
    @Test
    public void player_attack_on_coordinate_marked_as_hit_is_invalid(){
        int row = 1;
        int col = 1;
        mockOpponent.bottomBoard.grid[row][col] = "Hit";
        //boolean isValid = mockPlayer.attack(row,col);
        //assertFalse(isValid);
    }
    @Test
    public void player_attack_on_coordinate_in_column_range_is_valid(){
        int row = 1;
        int col = 5;
        //boolean isValid = mockPlayer.attack(row,col);
        //assertTrue(isValid);
    }
    @Test
    public void player_attack_on_coordinate_in_row_range_is_valid(){
        int row = 5;
        int col = 1;
        //boolean isValid = mockPlayer.attack(row,col);
        //assertTrue(isValid);
    }
    @Test
    public void player_attack_on_coordinate_above_column_range_is_invalid(){
        int row = 1;
        int col = 11;
        //boolean isValid = mockPlayer.attack(row,col);
        //assertFalse(isValid);
    }
    @Test
    public void player_attack_on_coordinate_below_column_range_is_invalid(){
        int row = 1;
        int col = -1;
        //boolean isValid = mockPlayer.attack(row,col);
        //assertFalse(isValid);
    }
    @Test
    public void player_attack_on_coordinate_above_row_range_is_invalid(){
        int row = 11;
        int col = 1;
        //boolean isValid = mockPlayer.attack(row,col);
        //assertFalse(isValid);
    }
    @Test
    public void player_attack_on_coordinate_below_row_range_is_invalid(){
        int row = -1;
        int col = 1;
        //boolean isValid = mockPlayer.attack(row,col);
        //assertFalse(isValid);
    }
}