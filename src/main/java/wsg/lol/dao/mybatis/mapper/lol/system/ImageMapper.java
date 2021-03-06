package wsg.lol.dao.mybatis.mapper.lol.system;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import wsg.lol.common.enums.share.ImageGroupEnum;
import wsg.lol.common.pojo.domain.item.ImageDo;

/**
 * Mapper interface for information of images.
 *
 * @author Kingen
 */
@Repository
public interface ImageMapper extends InsertListMapper<ImageDo> {
    int deleteByGroup(ImageGroupEnum group);
}