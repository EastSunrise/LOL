package wsg.lol.data.api;

import wsg.lol.common.constants.annotation.AccessInterval;
import wsg.lol.common.enums.impl.name.TierEnum;
import wsg.lol.common.enums.impl.others.DivisionEnum;
import wsg.lol.common.enums.impl.others.PositionEnum;
import wsg.lol.common.enums.impl.others.RankQueueEnum;
import wsg.lol.dmo.league.PositionDmo;
import wsg.lol.dto.api.league.LeagueExtDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author King
 * @date 2019/2/12
 */
public class LeagueV4 extends BaseApi {

    /**
     * Get the apex league for given queue and tier.
     */
    @AccessInterval
    public static LeagueExtDto getApexLeagueByQueue(RankQueueEnum queue, TierEnum tier) {
        Map<String, Object> params = new HashMap<>();
        params.put("queue", queue);
        return getDataExtObject("/lol/league/v4/" + tier.name().toLowerCase() + "leagues/by-queue/{queue}",
                params, LeagueExtDto.class);
    }

    /**
     * Get league with given ID, including inactive entries.
     */
    @AccessInterval
    public static LeagueExtDto getLeagueById(String leagueId) {
        Map<String, Object> params = new HashMap<>();
        params.put("leagueId", leagueId);
        return getDataExtObject("/lol/league/v4/leagues/{leagueId}", params, LeagueExtDto.class);
    }

    /**
     * Get the queues that have positional ranks enabled.
     */
    @AccessInterval
    public static List<String> getPositionalRankQueues() {
        return getDataArray("/lol/league/v4/positional-rank-queues", String.class);
    }

    /**
     * Get league positions in all queues for a given summoner ID.
     */
    @AccessInterval
    public static List<PositionDmo> getLeaguePositionsBySummonerId(String summonerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("encryptedSummonerId", summonerId);
        return getDataArray("/lol/league/v4/positions/by-summoner/{encryptedSummonerId}", params, PositionDmo.class);
    }

    /**
     * Get all the positional league entries.
     *
     * @param queue    positional value
     * @param tier     positional value
     * @param position positional value
     */
    @AccessInterval
    public static List<PositionDmo> getAllPositionLeagues(RankQueueEnum queue, TierEnum tier, DivisionEnum division,
                                                          PositionEnum position, int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("positionalQueue", queue);
        params.put("tier", tier);
        params.put("division", division);
        params.put("position", position);
        params.put("page", page);
        return getDataArray("/lol/league/v4/positions/{positionalQueue}/{tier}/{division}/{position}/{page" +
                "}", params, PositionDmo.class);
    }
}