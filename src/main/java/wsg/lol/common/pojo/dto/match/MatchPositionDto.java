package wsg.lol.common.pojo.dto.match;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wsg.lol.common.base.BaseDto;

/**
 * DTO for the position of the participant in the timeline.
 *
 * @author Kingen
 */
@EqualsAndHashCode(callSuper = true)
@Data
class MatchPositionDto extends BaseDto {

    private Integer y;
    private Integer x;
}
