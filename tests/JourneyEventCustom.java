import java.util.UUID;

public abstract class JourneyEventCustom {

    private final UUID cardId;
    private final UUID readerId;
    private final long time;

    public JourneyEventCustom(UUID cardId, UUID readerId, long time) {
        this.cardId = cardId;
        this.readerId = readerId;
        this.time = time;
    }

    public UUID cardId() {
        return cardId;
    }

    public UUID readerId() {
        return readerId;
    }

    public long time() {
        return time;
    }
}
