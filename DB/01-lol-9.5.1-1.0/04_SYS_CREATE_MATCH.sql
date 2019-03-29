-- 比赛参与者
CREATE TABLE m_reference
(
  ID                INT AUTO_INCREMENT COMMENT '主键',
  GAME_ID           BIGINT COMMENT '比赛ID',
  SUMMONER_ID       VARCHAR(32) COMMENT '召唤师ID',
  LANE              TINYINT COMMENT '分路',
  CHAMPION          INT COMMENT '使用的英雄ID',
  PLATFORM_ID       TINYINT COMMENT '平台',
  SEASON_ID         TINYINT COMMENT '赛季',
  QUEUE             SMALLINT COMMENT '游戏队列',
  ROLE              TINYINT COMMENT '角色',
  GAME_CREATION     TIMESTAMP COMMENT '开始时间',
  LAST_CHECKED_TIME TIMESTAMP COMMENT '最近一次校对时间',
  CONSTRAINT M_REFERENCE_PK PRIMARY KEY (ID),
  CONSTRAINT M_REFERENCE_FK FOREIGN KEY (SUMMONER_ID) REFERENCES s_summoner (ID)
);