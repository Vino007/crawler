package com.vino.crawl4j.url;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 本爬虫通过crawler.properties设置相关参数
 * 默认抓取4CHAN 上的gif
 * @author Joker
 *
 */
public class Crawler {

	private static final Logger log = Logger.getLogger(Crawler.class);

	public static void main(String[] args) throws IOException,
			InstantiationException, IllegalAccessException {

		String baseUrl = null;//基地址
		String realUrl = null;//真实URL
		String selectorRule = null;//选择器选择规则
		String saveDir = null;//文件保存路径
		String html = null;//响应的html
		StringBuilder sb = new StringBuilder();
		Properties prop = new Properties();
		InputStream in = Crawler.class
				.getResourceAsStream("/crawler.properties");
		prop.load(in);
		in.close();
		
		baseUrl = prop.getProperty("url");
		selectorRule = prop.getProperty("selectorRule");
		saveDir = prop.getProperty("saveDir");
		for (int i = 1; i < 11; i++) {
			if (i > 1) {
				realUrl = baseUrl + i;// 第一页就这样，第二页开始加上页码，总共只有10页
				html = DataObtain.getHTMLByGET(realUrl);
			} else{
				html = DataObtain.getHTMLByGET(baseUrl);
				realUrl = baseUrl;
			}
			if (html != null) {
				Document document = Jsoup.parse(html);

				Elements img = document.select(selectorRule);
				for (Element ele : img) {
					String href = ele.attr("href");
					String title = ele.text();

					DataPersistence.downloadImage(saveDir + title, "http:"
							+ href);
					sb.append(title);
					log.info("url:" + realUrl + "  " + "资源路径:" + href + "文件名:"
							+ title + "默默保存下来啦");

				}
			}

			DataPersistence.saveFile(saveDir + "log.txt", sb.toString());
		}

	}
}
