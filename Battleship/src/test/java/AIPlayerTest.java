import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AIPlayerTest {
    public void AI_player_attack_direction_is_north_upon_enemy_ship_discovery(){
    }
    public void AI_player_attack_direction_switches_to_west_of_enemy_ship_discovery_following_north_coordinate_miss() {
    }
    public void AI_player_attack_direction_switches_to_south_of_enemy_ship_discovery_following_west_coordinate_miss() {
    }
    public void AI_player_attack_direction_switches_to_east_of_enemy_ship_discovery_following_south_coordinate_miss() {
    }
    public void AI_player_stores_coordinate_upon_ship_discovery_after_attack() {
    }
    public void AI_player_determines_ship_is_horizontal_if_attack_hits_directly_north_or_south_of_ship_discovery() {
    }
    public void AI_player_determines_ship_is_vertical_if_attack_hits_directly_east_or_west_of_ship_discovery() {
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