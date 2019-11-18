package wsg.lol.common.pojo.dto.spectator;

import java.util.List;

/**
 * // TODO: (Kingen, 2019/11/18)
 * @author EastSunrise
 */
public class Perks {

    /**
     * Primary runes path
     */
    private long perkStyle;

    /**
     * IDs of the perks/runes assigned.
     */
    private List<Long> perkIds;

    /**
     * Secondary runes path
     */
    private long perkSubStyle;

    public long getPerkStyle() {
        return perkStyle;
    }

    public void setPerkStyle(long perkStyle) {
        this.perkStyle = perkStyle;
    }

    public List<Long> getPerkIds() {
        return perkIds;
    }

    public void setPerkIds(List<Long> perkIds) {
        this.perkIds = perkIds;
    }

    public long getPerkSubStyle() {
        return perkSubStyle;
    }

    public void setPerkSubStyle(long perkSubStyle) {
        this.perkSubStyle = perkSubStyle;
    }
}