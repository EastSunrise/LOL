package wsg.lol.dao.mybatis.mapper.summoner;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeyMapper;
import wsg.lol.common.pojo.dto.summoner.LeagueEntryDto;

/**
 * Mapper for entries of the league.
 *
 * @author Kingen
 */
@Mapper
@Repository
public interface LeagueEntryMapper extends InsertListMapper<LeagueEntryDto>, UpdateByPrimaryKeyMapper<LeagueEntryDto> {
}