package com.itheima.health.entity;

import java.io.Serializable;

/**
 * Description: 查询条件实体类
 *
 * @author zygui
 * @date 2020/3/22 9:48
 */
public class QueryPageBean implements Serializable {
	private Integer currentPage;//页码
	private Integer pageSize;//每页记录数
	private String queryString;//查询条件

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
}
