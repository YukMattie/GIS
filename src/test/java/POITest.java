import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



/**
 * The POITest class contains JUnit test methods for the POI class.
 */
class POITest {


    /**
     * Test method for getting the x coordinate of a POI.
     * Creates a new POI with given parameters and checks whether its x coordinate is equal to the expected value.
     */
    @Test
    void getX() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals(20, testPOI.getX());
        assertNotEquals(10, testPOI.getX());
    }


    /**
     * Test method for getting the y coordinate of a POI.
     * Creates a new POI with given parameters and checks whether its y coordinate is equal to the expected value.
     */
    @Test
    void getY() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals(40, testPOI.getY());
        assertNotEquals(10, testPOI.getY());
    }

    /**
     * Test method for getting the name of a POI.
     * Creates a new POI with given parameters and checks whether its name is equal to the expected value.
     */
    @Test
    void getName() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals("new_Name", testPOI.getName());
        assertNotEquals("fAkEnaMe", testPOI.getName());
    }


    /**
     * Test method for getting the room number of a POI.
     * Creates a new POI with given parameters and checks whether its room number is equal to the expected value.
     */
    @Test
    void getRoomNumber() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals("new_Number", testPOI.getRoomNumber());
        assertNotEquals("fAkEnaMe", testPOI.getRoomNumber());
    }


    /**
     * Test method for getting the type of a POI.
     * Creates a new POI with given parameters and checks whether its type is equal to the expected value.
     */
    @Test
    void getType() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals("new_Type", testPOI.getType());
        assertNotEquals("fAkEnaMe", testPOI.getType());
    }


    /**
     * Test method for getting the building of a POI.
     * Creates a new POI with given parameters and checks whether its building is equal to the expected value.
     */
    @Test
    void getBuilding() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals("mass_hall", testPOI.getBuilding());
        assertNotEquals("fAkEnaMe", testPOI.getBuilding());
    }


    /**
     * Test method for getting the floor of a POI.
     * Creates a new POI with given parameters and checks whether its floor is equal to the expected value.
     */
    @Test
    void getFloor() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals(3, testPOI.getFloor());
        assertNotEquals(1, testPOI.getFloor());
    }


    /**
     * Test method for getting the description of a POI.
     * Creates a new POI with given parameters and checks whether its description is equal to the expected value.
     */
    @Test
    void getDescription() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertEquals("new_Dis", testPOI.getDescription());
        assertNotEquals("fAkEnaMe", testPOI.getDescription());
    }

    /**
     * Test method for checking whether two POIs are equal.
     * Creates three new POIs with given parameters and checks whether the first and second POIs are equal,
     * and whether the first and third POIs are not equal.
     */
    @Test
    void checkEqual() {
        POI testPOI = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        POI testPOI2 = new POI(20, 40, "new_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        POI testPOI3 = new POI(20, 40, "_Name", "new_Number", "new_Type", "mass_hall", 3, "new_Dis");
        assertTrue(testPOI.checkEqual(testPOI2, testPOI));
        assertFalse(testPOI.checkEqual(testPOI2, testPOI3));
    }
}
