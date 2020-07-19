package com.product.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

/**
 * 短讯请求-工具类
 * 
 * @author syl
 *
 */
public class SmsHttpClient {

	private static RequestConfig config;
	static {
		config = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param encode
	 *            本地编码
	 * @param headers
	 *            请求头部
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String getJson(String url, Charset encode, Map<String, String> headers, Map<String, String> params) {
		String result = null;
		try {
			URIBuilder ub = new URIBuilder(url);
			if(params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					ub.setParameter(entry.getKey(), entry.getValue());
				}
			}
			ub.setCharset(encode);
			HttpGet get = new HttpGet(ub.build());
			get.setProtocolVersion(HttpVersion.HTTP_1_1);
			if(headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					get.setHeader(entry.getKey(), entry.getValue());
				}
			}
			
			ResponseHandler<String> handler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						if(entity != null) {
							ContentType type = ContentType.getOrDefault(entity);
							Charset charset = Consts.UTF_8;
							//通过 ContentType 获取服务器编码
							{
								Charset encoding = type.getCharset();
								if(encoding != null) {
									charset = encoding;
								}
							}
							//通过 parameter 获取服务器编码
							{
								String encoding = type.getParameter("encoding");
								if(StringUtils.isNotBlank(encoding)) {
									charset = Charset.forName(encoding);
								}
							}
							InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
							char[] buffer = new char[1024];
							StringBuilder sb = new StringBuilder();
							while (reader.read(buffer) != -1) {
								sb.append(buffer);
							}
							return sb.toString();
						}
					}

					return null;
				}
			};

			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setDefaultMaxPerRoute(1000);
			
			RequestConfig configs = RequestConfig.copy(config).build();
			CloseableHttpClient client = HttpClients.custom().setMaxConnPerRoute(300).disableConnectionState().setDefaultRequestConfig(configs).setConnectionManager(cm).build();
			
			result = client.execute(get, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param encode
	 *            本地编码
	 * @param headers
	 *            请求头部
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String postJson(String url, Charset encode, Map<String, String> headers, Map<String, String> params) {
		String result = null;
		try {
			URIBuilder ub = new URIBuilder(url);
			ub.setCharset(encode);
			HttpPost post = new HttpPost(ub.build());
			post.setProtocolVersion(HttpVersion.HTTP_1_1);
			post.setHeader("Content-Type","application/json");
			//设置请求参数
			{
				List<NameValuePair> nvps = new ArrayList<>();
				if(params != null && !params.isEmpty()) {
					for(Map.Entry<String, String> entry : params.entrySet()) {
						nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
					}
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			}
			if(headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					post.setHeader(entry.getKey(), entry.getValue());
				}
			}
			
			ResponseHandler<String> handler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						if(entity != null) {
							ContentType type = ContentType.getOrDefault(entity);
							Charset charset = Consts.UTF_8;
							//通过 ContentType 获取服务器编码
							{
								Charset encoding = type.getCharset();
								if(encoding != null) {
									charset = encoding;
								}
							}
							//通过 parameter 获取服务器编码
							{
								String encoding = type.getParameter("encoding");
								if(StringUtils.isNotBlank(encoding)) {
									charset = Charset.forName(encoding);
								}
							}
							InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
							char[] buffer = new char[1024];
							StringBuilder sb = new StringBuilder();
							while (reader.read(buffer) != -1) {
								sb.append(buffer);
							}
							return sb.toString();
						}
					}

					return null;
				}
			};

			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setDefaultMaxPerRoute(1000);
			
			RequestConfig configs = RequestConfig.copy(config).build();
			CloseableHttpClient client = HttpClients.custom().setMaxConnPerRoute(300).disableConnectionState().setDefaultRequestConfig(configs).setConnectionManager(cm).build();
			
			result = client.execute(post, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}