package wsg.lol.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wsg.lol.service.intf.ChampionService;
import wsg.lol.test.base.BaseTest;

/**
 * Test for champion service.
 *
 * @author Kingen
 */
public class ChampionServiceTest extends BaseTest {

    @Autowired
    private ChampionService championService;

    @Test
    public void updateChampions() {
        testVersion(version -> championService.updateChampions(version));
    }

    @Test
    public void updateSummonerSpells() {
        testVersion(version -> championService.updateSummonerSpells(version));
    }
}