package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

/**
 * 新闻舆情对象
 * @author lengzq
 *
 */
public class PublicOpinion implements Serializable {
	private String articleUrl;//舆情地址
	private String articleContent;//舆情内容（简介）
	private String articleCount;//舆情总条数
	private String articleFrom;//舆情来源
	private String articleImage;//舆情图片
	private String articleTime;//舆情发布时间
	private String articleTitle;//舆情标题
	private String articleType;//舆情类型（现在默认为：舆情信息）
	public String getArticleUrl() {
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public String getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}
	public String getArticleFrom() {
		return articleFrom;
	}
	public void setArticleFrom(String articleFrom) {
		this.articleFrom = articleFrom;
	}
	public String getArticleImage() {
		return articleImage;
	}
	public void setArticleImage(String articleImage) {
		this.articleImage = articleImage;
	}
	public String getArticleTime() {
		return articleTime;
	}
	public void setArticleTime(String articleTime) {
		this.articleTime = articleTime;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	@Override
	public String toString() {
		return "PublicOpinion [articleUrl=" + articleUrl + ", articleContent="
				+ articleContent + ", articleCount=" + articleCount
				+ ", articleFrom=" + articleFrom + ", articleImage="
				+ articleImage + ", articleTime=" + articleTime
				+ ", articleTitle=" + articleTitle + ", articleType="
				+ articleType + "]";
	}
}
