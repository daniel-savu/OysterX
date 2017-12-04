import java.util.UUID;

public class JourneyEndCustom extends JourneyEventCustom {

    public JourneyEndCustom(UUID cardId, UUID readerId, long time) {
        super(cardId, readerId, time);
    }
}
