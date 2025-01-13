import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    @Test
    @DisplayName("Testing JUnit")
    void failingTest() {
        fail("a failing test");
    }
}