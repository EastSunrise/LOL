package wsg.lol.dao.mybatis.mapper.champion;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wsg.lol.common.pojo.dto.champion.ChampionSpellDto;
import wsg.lol.dao.mybatis.common.StaticStrategy;

/**
 * Mapper interface for spells of champions.
 *
 * @author Kingen
 */
@Repository
@Mapper
public interface ChampionSpellMapper extends StaticStrategy<ChampionSpellDto> {
}