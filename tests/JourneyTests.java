import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

public class JourneyTests {

    private UUID cardExampleID = UUID.randomUUID();
    private UUID readerOriginID = UUID.randomUUID();
    private UUID readerDestinationID = UUID.randomUUID();
    private JourneyStartCustom journeyStartCustom;
    private JourneyEndCustom journeyEndCustom;
    private JourneyCustom journeyCustom;

    void createTestJourneyWithStartTimeAndEndTime(String humanReadableStartTime, String humanReadableEndTime) throws InterruptedException
    {
        long startTime = DateFormatter.format(humanReadableStartTime);
        long endTime = DateFormatter.format(humanReadableEndTime);
        journeyStartCustom = new JourneyStartCustom(cardExampleID, readerOriginID, startTime);
        journeyEndCustom = new JourneyEndCustom(cardExampleID, readerDestinationID, endTime);
        journeyCustom = new JourneyCustom(journeyStartCustom, journeyEndCustom);
    }

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();



    @Test
    public void journeyStartStaionIdEqualsOriginId() throws InterruptedException
    {
        createTestJourneyWithStartTimeAndEndTime("07:30:00","07:45:00");
        Assert.assertEquals(journeyCustom.originId(), journeyStartCustom.readerId());
    }

    @Test
    public void journeyEndStationIdEqualsDestinationId() throws InterruptedException
    {
        createTestJourneyWithStartTimeAndEndTime("07:30:00","07:45:00");
        Assert.assertEquals(journeyCustom.destinationId(),journeyEndCustom.readerId());
    }

    @Test
    public void journeyStartTimeIsBeforeJourneyEndTime() throws InterruptedException
    {
        createTestJourneyWithStartTimeAndEndTime("07:30:00","07:45:00");
        assertTrue(journeyCustom.startTime().before(journeyCustom.endTime()));
    }


}
