package wsg.lol.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsg.lol.common.enums.champion.ChampionTipEnum;
import wsg.lol.common.enums.champion.SpellNumEnum;
import wsg.lol.common.enums.share.ImageGroupEnum;
import wsg.lol.common.pojo.domain.champion.*;
import wsg.lol.common.pojo.domain.item.BlockDo;
import wsg.lol.common.pojo.domain.item.RecommendedDo;
import wsg.lol.common.pojo.dto.champion.ChampionDto;
import wsg.lol.common.pojo.dto.champion.ChampionExtDto;
import wsg.lol.common.pojo.dto.champion.SpellDto;
import wsg.lol.common.pojo.dto.item.BlockDto;
import wsg.lol.common.pojo.dto.item.ImageDto;
import wsg.lol.common.pojo.dto.item.RecommendedExtDto;
import wsg.lol.common.pojo.transfer.ObjectTransfer;
import wsg.lol.dao.dragon.intf.DragonDao;
import wsg.lol.dao.mybatis.mapper.lol.champion.*;
import wsg.lol.dao.mybatis.mapper.lol.item.BlockMapper;
import wsg.lol.dao.mybatis.mapper.lol.item.RecommendedMapper;
import wsg.lol.service.base.BaseService;
import wsg.lol.service.intf.ChampionService;
import wsg.lol.service.intf.CollectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kingen
 */
@Service
public class ChampionServiceImpl extends BaseService implements ChampionService {

    private static final Logger logger = LoggerFactory.getLogger(ChampionService.class);

    private DragonDao dragonDao;

    private ChampionMapper championMapper;

    private SkinMapper skinMapper;

    private ChampionTipMapper championTipMapper;

    private ChampionStatsMapper championStatsMapper;

    private RecommendedMapper recommendedMapper;

    private BlockMapper blockMapper;

    private SpellMapper spellMapper;

    private CollectionService collectionService;

    @Override
    @Transactional
    public void updateChampions(String version) {
        logger.info("Updating the data of champions.");
        List<ChampionExtDto> championExtDtoList = dragonDao.readChampions(version);

        logger.info("Updating the champions.");
        List<ChampionDto> championDtoList = new ArrayList<>(championExtDtoList);
        List<ChampionDo> championDoList = ObjectTransfer.transferDtoList(championDtoList, ChampionDo.class);
        clear(championMapper);
        insertList(championMapper, championDoList);

        logger.info("Updating the images of champions.");
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (ChampionExtDto championExtDto : championExtDtoList) {
            ImageDto image = championExtDto.getImage();
            image.setRelatedId(championExtDto.getId());
            imageDtoList.add(image);
        }
        collectionService.updateImages(imageDtoList, ImageGroupEnum.Champion);

        logger.info("Updating the skins.");
        List<SkinDo> skinDoList = new ArrayList<>();
        for (ChampionExtDto championExtDto : championExtDtoList) {
            List<SkinDo> skins = ObjectTransfer.transferDtoList(championExtDto.getSkins(), SkinDo.class);
            Integer id = championExtDto.getId();
            for (SkinDo skin : skins) {
                skin.setChampionId(id);
            }
            skinDoList.addAll(skins);
        }
        clear(skinMapper);
        insertList(skinMapper, skinDoList);

        logger.info("Updating the tips of champions.");
        List<ChampionTipDo> championTipDoList = new ArrayList<>();
        for (ChampionExtDto championExtDto : championExtDtoList) {
            Integer id = championExtDto.getId();
            for (String tip : championExtDto.getAllytips()) {
                ChampionTipDo championTipDo = new ChampionTipDo();
                championTipDo.setChampionId(id);
                championTipDo.setTip(tip);
                championTipDo.setType(ChampionTipEnum.Ally);
                championTipDoList.add(championTipDo);
            }
            for (String tip : championExtDto.getEnemytips()) {
                ChampionTipDo championTipDo = new ChampionTipDo();
                championTipDo.setChampionId(id);
                championTipDo.setTip(tip);
                championTipDo.setType(ChampionTipEnum.Enemy);
                championTipDoList.add(championTipDo);
            }
        }
        clear(championTipMapper);
        insertList(championTipMapper, championTipDoList);

        logger.info("Updating the stats of champions.");
        List<ChampionStatsDo> statsDoList = new ArrayList<>();
        for (ChampionExtDto championExtDto : championExtDtoList) {
            ChampionStatsDo stats = ObjectTransfer.transferDto(championExtDto.getStats(), ChampionStatsDo.class);
            stats.setChampionId(championExtDto.getId());
            statsDoList.add(stats);
        }
        clear(championStatsMapper);
        insertList(championStatsMapper, statsDoList);

        logger.info("Updating the spells of champions.");
        List<SpellDo> spellDoList = new ArrayList<>();
        imageDtoList = new ArrayList<>();
        SpellNumEnum[] enums = new SpellNumEnum[]{
                SpellNumEnum.Q, SpellNumEnum.W, SpellNumEnum.E, SpellNumEnum.R
        };
        for (ChampionExtDto championExtDto : championExtDtoList) {
            List<SpellDto> spells = championExtDto.getSpells();
            Integer id = championExtDto.getId();
            for (int i = 0; i < spells.size(); i++) {
                SpellDto spellDto = spells.get(i);
                SpellDo spellDo = ObjectTransfer.transferDto(spellDto, SpellDo.class);
                spellDo.setChampionId(id);
                spellDo.setNum(enums[i]);
                spellDo.setId(SpellDo.generateId(id, enums[i]));
                spellDoList.add(spellDo);

                ImageDto image = spellDto.getImage();
                image.setRelatedId(spellDo.getId());
                imageDtoList.add(image);
            }

            SpellDto passiveDto = championExtDto.getPassive();
            SpellDo passive = ObjectTransfer.transferDto(passiveDto, SpellDo.class);
            passive.setChampionId(id);
            passive.setNum(SpellNumEnum.P);
            passive.setId(SpellDo.generateId(id, SpellNumEnum.P));
            passive.setKey(championExtDto.getKey() + SpellNumEnum.P.name());
            spellDoList.add(passive);
            ImageDto image = passiveDto.getImage();
            image.setRelatedId(passive.getId());
            imageDtoList.add(image);
        }
        updateSpells(spellDoList, SpellNumEnum.P, SpellNumEnum.Q, SpellNumEnum.W, SpellNumEnum.E, SpellNumEnum.R);
        logger.info("Updating the images of champion spells.");
        collectionService.updateImages(imageDtoList, ImageGroupEnum.Spell, ImageGroupEnum.Passive);

        logger.info("Updating the recommended items of champions.");
        List<RecommendedDo> recommendedDoList = new ArrayList<>();
        List<BlockDo> blockDoList = new ArrayList<>();
        for (ChampionExtDto championExtDto : championExtDtoList) {
            List<RecommendedExtDto> recommendedExtDtoList = championExtDto.getRecommended();
            for (int i = 0; i < recommendedExtDtoList.size(); i++) {
                RecommendedExtDto recommendedExtDto = recommendedExtDtoList.get(i);
                RecommendedDo recommendedDo = ObjectTransfer.transferDto(recommendedExtDto, RecommendedDo.class);
                Integer generateId = RecommendedDo.generateId(championExtDto.getId(), i);
                recommendedDo.setId(generateId);
                recommendedDoList.add(recommendedDo);

                for (BlockDto block : recommendedExtDto.getBlocks()) {
                    BlockDo blockDo = ObjectTransfer.transferDto(block, BlockDo.class);
                    blockDo.setRecommendedId(generateId);
                    blockDoList.add(blockDo);
                }
            }
        }
        clear(recommendedMapper);
        insertList(recommendedMapper, recommendedDoList);
        clear(blockMapper);
        insertList(blockMapper, blockDoList);

        logger.info("Succeed in updating the data of champions.");
    }

    @Override
    @Transactional
    public void updateSummonerSpells(String version) {
        logger.info("Updating the summoner spells.");
        List<SpellDto> spellDtoList = dragonDao.readSummonerSpells(version);
        List<ImageDto> images = new ArrayList<>();
        List<SpellDo> spellDoList = new ArrayList<>();
        for (SpellDto spellDto : spellDtoList) {
            SpellDo spellDo = ObjectTransfer.transferDto(spellDto, SpellDo.class);
            spellDo.setNum(SpellNumEnum.S);
            spellDoList.add(spellDo);

            ImageDto image = spellDto.getImage();
            image.setRelatedId(spellDto.getId());
            image.setGroup(ImageGroupEnum.SummonerSpell);
            images.add(image);
        }
        this.updateSpells(spellDoList, SpellNumEnum.S);
        logger.info("Updating the images of summoner spells.");
        collectionService.updateImages(images, ImageGroupEnum.SummonerSpell);

        logger.info("Succeed in updating the data of summoner spells.");
    }

    private void updateSpells(List<SpellDo> spells, SpellNumEnum... nums) {
        int count;
        for (SpellNumEnum num : nums) {
            count = spellMapper.deleteByNum(num);
            logger.info("Deleted " + count + " spells of " + num);
        }

        insertList(spellMapper, spells);
    }

    @Autowired
    public void setDragonDao(DragonDao dragonDao) {
        this.dragonDao = dragonDao;
    }

    @Autowired
    public void setChampionMapper(ChampionMapper championMapper) {
        this.championMapper = championMapper;
    }

    @Autowired
    public void setSkinMapper(SkinMapper skinMapper) {
        this.skinMapper = skinMapper;
    }

    @Autowired
    public void setChampionTipMapper(ChampionTipMapper championTipMapper) {
        this.championTipMapper = championTipMapper;
    }

    @Autowired
    public void setChampionStatsMapper(ChampionStatsMapper championStatsMapper) {
        this.championStatsMapper = championStatsMapper;
    }

    @Autowired
    public void setRecommendedMapper(RecommendedMapper recommendedMapper) {
        this.recommendedMapper = recommendedMapper;
    }

    @Autowired
    public void setBlockMapper(BlockMapper blockMapper) {
        this.blockMapper = blockMapper;
    }

    @Autowired
    public void setSpellMapper(SpellMapper spellMapper) {
        this.spellMapper = spellMapper;
    }

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }
}
