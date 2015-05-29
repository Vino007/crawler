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
 * ������ͨ��crawler.properties������ز���
 * Ĭ��ץȡ4CHAN �ϵ�gif
 * @author Joker
 *
 */
public class Crawler {

	private static final Logger log = Logger.getLogger(Crawler.class);

	public static void main(String[] args) throws IOException,
			InstantiationException, IllegalAccessException {

		String baseUrl = null;//����ַ
		String realUrl = null;//��ʵURL
		String selectorRule = null;//ѡ����ѡ�����
		String saveDir = null;//�ļ�����·��
		String html = null;//��Ӧ��html
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
				realUrl = baseUrl + i;// ��һҳ���������ڶ�ҳ��ʼ����ҳ�룬�ܹ�ֻ��10ҳ
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
					log.info("url:" + realUrl + "  " + "��Դ·��:" + href + "�ļ���:"
							+ title + "ĬĬ����������");

				}
			}

			DataPersistence.saveFile(saveDir + "log.txt", sb.toString());
		}

	}
}
