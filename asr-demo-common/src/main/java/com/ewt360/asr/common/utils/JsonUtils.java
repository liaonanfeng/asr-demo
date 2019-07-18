package com.ewt360.asr.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ewt360.asr.common.exception.ServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * json处理工具类
 * 
 */
@Slf4j
public class JsonUtils {

	private static final ObjectMapper mapper = new ObjectMapper();

	private JsonUtils() {

	}

	/**
	 *  校验是不是json格式的字符串
	 *
	 * @param content
	 * @return
	 */
	public static boolean isObject(String content){
		try {
			JSONObject jsonStr= JSONObject.parseObject(content);
			return  true;
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean isArray(String content){
		try {
			JSONArray jsonStr= JSONObject.parseArray(content);
			return  true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Object -> json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			log.error("Object转化成Json失败", e);
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * json -> Object
	 * 
	 * @param <T>
	 * @param content
	 * @param valueType
	 * @return
	 */
	public static <T> T toObject(String content, Class<T> valueType) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		try {
			return mapper.readValue(content, valueType);
		} catch (Exception e) {
			throw new ServiceException("参数Json转化成Object失败", e);
		}
	}

	/**
	 * json -> List
	 * 
	 * @param <T>
	 * @param content
	 * @param valueType
	 * @return
	 */
	public static <T> List<T> toList(String content, Class<T> valueType) {
		JsonNode node = null;
		try {
			node = mapper.readTree(content);
		} catch (Exception e) {
			log.error("Json转化成List失败:{}", e.getMessage());
			log.warn("Json转化成List失败:{}", e);
		}
		if (node == null) {
			return new ArrayList<T>(0);
		}
		Iterator<JsonNode> iterator = node.elements();
		List<T> list = new ArrayList<T>();
		while (iterator.hasNext()) {
			String json = iterator.next().toString();
			T e = toObject(json, valueType);
			list.add(e);
		}
		return list;
	}



}
