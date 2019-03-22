package wsg.lol.data.api;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import wsg.lol.common.utils.*;
import wsg.lol.data.config.ApiKey;
import wsg.lol.data.config.Config;
import wsg.lol.pojo.base.IJSONTransfer;
import wsg.lol.pojo.base.QueryDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wsg
 *
 * @author wangsigen
 * @date 2019-02-27 16:33
 */
public class BaseApi {

    private static int apiCount = 0;
    @Autowired
    private Config config;
    private final String CURRENT_HOST = config.getRegion().getHost();
    private final String GLOBAL_HOST = config.getGlobalProxy().getHost();

    // get valid api key
    // limit the rate of querying.
    // exclusive access
    private static synchronized Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Origin", "https://developer.riotgames.com");
        headers.put("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        while (!ApiKey.hasValidKey()) {
            try {
                LogUtil.info("There isn't valid key. Wait fot an hour.");
                Thread.sleep(DateUtil.ONE_HOUR);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LogUtil.info("Get api key " + ++apiCount + " times.");
        try {
            if (apiCount % 100 == 0) {
                LogUtil.info("Sleep 120s.");
                Thread.sleep(120000);
            } else if (apiCount % 20 == 0) {
                LogUtil.info("Sleep 1000ms.");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        headers.put("X-Riot-Token", ApiKey.getApiKey());
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/72.0.3626.121 Safari/537.36");
        return headers;
    }

    private String getJSONString(String apiRef, Map<String, Object> pathParams,
                                 Map<String, Object> queryParams) {
        return getJSONString(CURRENT_HOST, apiRef, pathParams, queryParams);
    }

    private String getJSONString(String host, String apiRef, Map<String, Object> pathParams, Map<String,
            Object> queryParams) {
        for (Map.Entry<String, Object> entry : pathParams.entrySet()) {
            apiRef = apiRef.replace("{" + entry.getKey() + "}", CodeUtil.encode(entry.getValue()));
        }
        String urlStr = HttpHelper.HTTPS + (host + apiRef + "?" + CodeUtil.encodeMap2UrlParams(queryParams)).replace(
                "+", "%20");
        return HttpHelper.getJSONString(urlStr, getRequestHeaders());
    }

    <Q extends QueryDto, T> T getDataObject(String apiRef, Map<String, Object> pathParams, Q queryDto,
                                            Class<T> clazz) {
        String jsonStr = getJSONString(apiRef, pathParams, BeanUtil.getQueryParams(queryDto));
        return JSON.parseObject(jsonStr, clazz);
    }

    <T> T getDataObject(String apiRef, Map<String, Object> pathParams, Class<T> clazz) {
        String jsonStr = getJSONString(apiRef, pathParams, new HashMap<String, Object>());
        return JSON.parseObject(jsonStr, clazz);
    }

    <T> T getDataObject(String apiRef, Class<T> clazz) {
        return getDataObject(apiRef, new HashMap<String, Object>(), clazz);
    }

    <T> T getCommonDataObject(String apiRef, Map<String, Object> pathParams, Class<T> clazz) {
        String jsonStr = getJSONString(GLOBAL_HOST, apiRef, pathParams, new HashMap<>());
        return JSON.parseObject(jsonStr, clazz);
    }

    <T> List<T> getDataArray(String apiRef, Class<T> clazz) {
        return getDataArray(apiRef, new HashMap<String, Object>(), clazz);
    }

    <T extends IJSONTransfer> T getDataExtObject(String apiRef, Map<String, Object> pathParams, Class<T> clazz) {
        String jsonStr = getJSONString(apiRef, pathParams, new HashMap<String, Object>());
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        object.parseFromJSONObject(JSON.parseObject(jsonStr));
        return object;
    }

    <T> List<T> getDataArray(String apiRef, Map<String, Object> pathParams, Class<T> clazz) {
        String jsonStr = getJSONString(apiRef, pathParams, new HashMap<>());
        return JSON.parseArray(jsonStr, clazz);
    }

    <T extends QueryDto> String postJSONString(String apiRef, T postDto) {
        return postJSONString(apiRef, null, postDto);
    }

    <T extends QueryDto, V extends QueryDto> String postJSONString(String apiRef, T queryDto,
                                                                   V postDto) {
        String urlStr = (GLOBAL_HOST + apiRef + "?" + (queryDto != null ?
                CodeUtil.encodeMap2UrlParams(BeanUtil.getQueryParams(queryDto)) : "")).replace("+", "%20");
        return HttpHelper.postJSONString(urlStr, BeanUtil.getQueryParams(postDto), getRequestHeaders());
    }
}
