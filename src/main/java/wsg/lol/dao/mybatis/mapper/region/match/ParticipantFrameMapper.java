package wsg.lol.dao.mybatis.mapper.region.match;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import wsg.lol.common.pojo.domain.match.ParticipantFrameDo;

/**
 * Mapper for each frame of the participants in the match.
 *
 * @author Kingen
 */
@Repository
public interface ParticipantFrameMapper extends InsertListMapper<ParticipantFrameDo> {
}