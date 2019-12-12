package wsg.lol.service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wsg.lol.common.base.Result;
import wsg.lol.common.enums.system.EventTypeEnum;
import wsg.lol.common.util.PageUtils;
import wsg.lol.service.intf.EventService;
import wsg.lol.service.intf.SystemService;

/**
 * Scheduler for events.
 *
 * @author Kingen
 */
@Component
public class EventScheduler {

    private SystemService systemService;

    private EventService eventService;

    /**
     * Add summoners by handling events of type {@link EventTypeEnum#Summoner}.
     * Request 4.
     */
    @Scheduled(cron = "${cron.event.summoner}")
    public void handleSummoners() {
        Result handle = eventService.handle(EventTypeEnum.Summoner, PageUtils.getRowBounds());
        systemService.sendWarnMessage(handle);
    }

    /**
     * Add matches by handling events of type {@link EventTypeEnum#Match}
     * Request 2.
     * <p>
     * Meanwhile, add events of type {@link EventTypeEnum#Summoner} if not exist from the participants of matches.
     */
    @Scheduled(cron = "${cron.event.match}")
    public void handleMatches() {
        Result result = eventService.handle(EventTypeEnum.Match, PageUtils.getRowBounds());
        systemService.sendWarnMessage(result);
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}