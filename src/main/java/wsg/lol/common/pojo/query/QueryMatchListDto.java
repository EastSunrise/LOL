package wsg.lol.common.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.time.DateUtils;
import wsg.lol.common.annotation.Optional;
import wsg.lol.common.base.QueryDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Condition to query matched.
 *
 * @author Kingen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryMatchListDto extends QueryDto {

    public static final int MAX_INDEX_RANGE = 100;

    public static final long MAX_TIME_RANGE = DateUtils.MILLIS_PER_DAY * 7;

    /**
     * Set of champion IDs for filtering the matchlist.
     */
    @Optional
    private List<Integer> champion;

    /**
     * Set of queue IDs for filtering the matchlist.
     */
    @Optional
    private List<Integer> queue;

    /**
     * The end and begin time to use for filtering matchlist specified as epoch milliseconds. If beginTime is specified,
     * but not endTime, then endTime defaults to the the current unix timestamp in milliseconds (the maximum time range
     * limitation is not observed in this specific case). If endTime is specified, but not beginTime, then beginTime
     * defaults to the start of the account's match history returning a 400 due to the maximum time range limitation. If
     * both are specified, then endTime should be greater than beginTime. The maximum time range allowed is one week,
     * otherwise a 400 error code is returned.
     */
    @Optional
    private Long endTime;
    @Optional
    private Long beginTime;

    /**
     * The end and begin index to use for filtering matchlist. If beginIndex is specified, but not endIndex, then
     * endIndex defaults to beginIndex+100. If endIndex is specified, but not beginIndex, then beginIndex defaults to 0.
     * If both are specified, then endIndex must be greater than beginIndex. The maximum range allowed is 100, otherwise
     * a 400 error code is returned.
     */
    @Optional
    private Long endIndex;
    @Optional
    private Long beginIndex;

    public boolean isValid() {
        return (beginIndex == null || endIndex == null || (beginIndex < endIndex && beginIndex + MAX_INDEX_RANGE >= endIndex))
                && (beginTime == null || endTime == null || (beginTime < endTime && beginTime + MAX_TIME_RANGE >= endTime));
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("champion", champion);
        map.put("queue", queue);
        map.put("endTime", endTime);
        map.put("beginTime", beginTime);
        map.put("endIndex", endIndex);
        map.put("beginIndex", beginIndex);
        return map;
    }
}
