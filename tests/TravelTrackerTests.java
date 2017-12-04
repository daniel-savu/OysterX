import com.tfl.billing.TravelTracker;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.fail;


public class TravelTrackerTests {

    private UUID cardExampleID = UUID.randomUUID();
    private UUID readerOriginID = UUID.randomUUID();
    private UUID readerDestinationID = UUID.randomUUID();

    public TravelTrackerTests() throws NoSuchMethodException {
    }


    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();



    TravelTracker travelTracker = new TravelTracker();

    @Test
    public void scanningUnknownCardIDThrowsUnknownOysterCardException() {
        UUID unknownOysterCardID = UUID.randomUUID();
        try {
            travelTracker.cardScanned(unknownOysterCardID, readerOriginID);
            fail("UnknownOysterCardException not thrown");
        } catch(Exception e) {
            Assert.assertTrue(e.getClass().getCanonicalName().equals("com.tfl.billing.UnknownOysterCardException"));
        }

    }

    @Test
    public void scanningKnownCardIDDoesNotThrowException() {
        UUID knownOysterCardID = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        try {
            travelTracker.cardScanned(knownOysterCardID, readerOriginID);

        } catch(Exception e) {
            fail("UnknownOysterCardException not thrown");

        }

    }


}
