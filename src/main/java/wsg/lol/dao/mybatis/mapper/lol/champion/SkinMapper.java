package wsg.lol.dao.mybatis.mapper.lol.champion;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wsg.lol.common.annotation.Platform;
import wsg.lol.common.pojo.domain.champion.SkinDo;
import wsg.lol.dao.mybatis.common.StaticMapper;

/**
 * Mapper interface for skins of champions.
 *
 * @author Kingen
 */
@Repository
@Mapper
@Platform
public interface SkinMapper extends StaticMapper<SkinDo> {
}