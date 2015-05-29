package com.vino.crawl4j.url;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DataObtain {
	public static String getHTMLByGET(String url) {
		
		CloseableHttpClient client = HttpClients.createDefault();	
		HttpGet httpGet = new HttpGet(url);
		//αװ�������
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		CloseableHttpResponse response = null;
		String html = null;
		try {
			response = client.execute(httpGet);

			HttpEntity entity = response.getEntity();

			if (entity != null)
				html = EntityUtils.toString(entity);

			EntityUtils.consume(entity);
			return html;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return html;
	}
	/**
	 * ͨ����������ȡhtml
	 * @param url
	 * @param hostname
	 * @param port
	 * @return
	 */
	public static String getHTMLByProxy(String url,String hostname,int port) {
		
		HttpHost proxy=new HttpHost(hostname, port);
		CloseableHttpClient client = HttpClients.custom().setProxy(proxy).build();
	
		HttpGet httpGet = new HttpGet(url);
		//αװ�������
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		CloseableHttpResponse response = null;
		String html = null;
		try {
			response = client.execute(httpGet);

			HttpEntity entity = response.getEntity();

			if (entity != null)
				html = EntityUtils.toString(entity);

			EntityUtils.consume(entity);
			return html;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return html;
	}
}
