package com.etoak.app.common.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jstree树的对象
 * Created by zhangcheng on 2017/9/8.
 */
public class Tree<T> {
	/**
	 * 节点id,需要保证唯一,为空时jstree自动生成
	 */
	private String id;
	/**
	 * 节点显示文本
	 */
	private String text;
	/**
	 * 节点状态选项 selected/opened/disabled/checked/undetermined
	 */
	private Map<String, Boolean> state = new HashMap<>();
	/**
	 * 节点数据集,jstree自动映射为节点data属性
	 */
	private T data;
	/**
	 * 子节点
	 */
	private List<Tree> children = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Boolean> getState() {
		return state;
	}

	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}
}