import java.util.UUID;

public class JourneyStartCustom extends JourneyEventCustom {
    public JourneyStartCustom(UUID cardId, UUID readerId, long time) {
        super(cardId, readerId, time);
    }
}
