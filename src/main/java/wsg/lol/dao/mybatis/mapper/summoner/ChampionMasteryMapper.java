package wsg.lol.dao.mybatis.mapper.summoner;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeyMapper;
import wsg.lol.common.pojo.dto.summoner.ChampionMasteryDto;

/**
 * Mapper for champion masteries.
 *
 * @author Kingen
 */
@Mapper
@Repository
public interface ChampionMasteryMapper extends InsertListMapper<ChampionMasteryDto>, UpdateByPrimaryKeyMapper<ChampionMasteryDto> {
}