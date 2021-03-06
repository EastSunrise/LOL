package wsg.lol.common.pojo.dto.champion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wsg.lol.common.base.BaseDto;

/**
 * DTO for the skin.
 *
 * @author Kingen
 */
@EqualsAndHashCode(callSuper = true)
@Data
class SkinDto extends BaseDto {

    private Integer id;
    private Integer num;
    private String name;
    private Boolean chromas;
}
