package wsg.lol.common.pojo.dto.match;

import wsg.lol.common.base.BaseDto;
import wsg.lol.common.enums.game.SeasonEnum;
import wsg.lol.common.enums.rank.MatchLaneEnum;
import wsg.lol.common.enums.rank.MatchQueueEnum;
import wsg.lol.common.enums.rank.MatchRoleEnum;
import wsg.lol.common.enums.route.PlatformEnum;

import java.util.Date;

/**
 * // TODO: (Kingen, 2019/11/18)
 * @author EastSunrise
 */
public class MatchReferenceDto extends BaseDto {

    private MatchLaneEnum lane;
    private long gameId;
    private int champion;
    private PlatformEnum platformId;
    private SeasonEnum season;
    private MatchQueueEnum queue;
    private MatchRoleEnum role;


    private Date timestamp;

    private String summonerId;

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public MatchLaneEnum getLane() {
        return lane;
    }

    public void setLane(MatchLaneEnum lane) {
        this.lane = lane;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getChampion() {
        return champion;
    }

    public void setChampion(int champion) {
        this.champion = champion;
    }

    public PlatformEnum getPlatformId() {
        return platformId;
    }

    public void setPlatformId(PlatformEnum platformId) {
        this.platformId = platformId;
    }

    public SeasonEnum getSeason() {
        return season;
    }

    public void setSeason(SeasonEnum season) {
        this.season = season;
    }

    public MatchQueueEnum getQueue() {
        return queue;
    }

    public void setQueue(MatchQueueEnum queue) {
        this.queue = queue;
    }

    public MatchRoleEnum getRole() {
        return role;
    }

    public void setRole(MatchRoleEnum role) {
        this.role = role;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}