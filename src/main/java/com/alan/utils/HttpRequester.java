package com.alan.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

/**
 * HTTP请求对象
 * 
 * @author Alan Huang
 */
public class HttpRequester {
	private String defaultContentEncoding;
	private String cookie = "";
	private String contentType = "application/json";

	public HttpRequester() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString) throws IOException {
		return send(urlString, "GET", null, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "GET", params, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
			throws IOException {
		return send(urlString, "GET", params, propertys);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString) throws IOException {
		return send(urlString, "POST", null, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "POST", params, null);
	}

	public String load(String url, String query) throws Exception {

		URL restURL = new URL(url);

		HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();

		conn.setRequestMethod("POST");

		conn.setDoOutput(true);

		conn.setAllowUserInteraction(false);

		PrintStream ps = new PrintStream(conn.getOutputStream());
		ps.print(query);

		ps.close();

		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line, resultStr = "";

		while (null != (line = bReader.readLine()))

		{

			resultStr += line;

		}

		bReader.close();

		return resultStr;

	}

	public HttpRespons sendPost(String urlString, String ParamsString, String contentType) throws IOException {
		// 创建url资源
		URL url = new URL(urlString);
		// 建立http连接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置允许输出
		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		// 设置不用缓存
		urlConnection.setUseCaches(true);
		// 设置传递方式
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("Cookie", getCookie());
		// 设置维持长连接
		urlConnection.setRequestProperty("Connection", "Keep-Alive");
		// 设置文件字符集:
		urlConnection.setRequestProperty("Charset", "UTF-8");
		// 转换为字节数组
		byte[] data = ParamsString.getBytes();
		// 设置文件长度
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

		urlConnection.setRequestProperty("accept", "*/*");
		// 设置文件类型:
		urlConnection.setRequestProperty("Content-Type", getContentType());

		// 开始连接请求
		urlConnection.connect();
		OutputStream out = urlConnection.getOutputStream();
		// 写入请求的字符串
		out.write(ParamsString.getBytes());
		out.flush();
		out.close();

		return makeContent(urlString, urlConnection);
	}

	public HttpRespons sendPost(String urlString, String ParamsString) throws IOException {
		// 创建url资源
	    System.err.println(urlString);
		URL url = new URL(urlString);
		// 建立http连接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置允许输出
		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		// 设置不用缓存
		urlConnection.setUseCaches(true);
		// 设置传递方式
		urlConnection.setRequestMethod("POST");
		// 设置维持长连接
		urlConnection.setRequestProperty("Connection", "Keep-Alive");
		// 设置文件字符集:
		urlConnection.setRequestProperty("Charset", "UTF-8");
		// 转换为字节数组
		byte[] data = ParamsString.getBytes();
		// 设置文件长度
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

		urlConnection.setRequestProperty("accept", "*/*");
		// 设置文件类型:
		urlConnection.setRequestProperty("Content-Type", "application/json");

		// 开始连接请求
		urlConnection.connect();
		OutputStream out = urlConnection.getOutputStream();
		// 写入请求的字符串
		out.write(ParamsString.getBytes());
		out.flush();
		out.close();

		return makeContent(urlString, urlConnection);
	}

	public HttpRespons sendPostUseAuthorization(String urlString, String ParamsString, String Authorization)
			throws IOException {
		// 创建url资源
		URL url = new URL(urlString);
		// 建立http连接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置允许输出
		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		// 设置不用缓存
		urlConnection.setUseCaches(true);
		// 设置传递方式
		urlConnection.setRequestMethod("POST");
		// 设置维持长连接
		urlConnection.setRequestProperty("Connection", "Keep-Alive");
		// 设置文件字符集:
		urlConnection.setRequestProperty("Charset", "UTF-8");
		// 转换为字节数组
		byte[] data = ParamsString.getBytes();
		// 设置文件长度
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
		urlConnection.setRequestProperty("Authorization", Authorization);
		urlConnection.setRequestProperty("App-Version", "2.9.4.1");
		urlConnection.setRequestProperty("App-Channel", "100001");
		urlConnection.setRequestProperty("Repeated-Submission", "" + new Date().getTime());
		urlConnection.setRequestProperty("accept", "*/*");
		// 设置文件类型:
		urlConnection.setRequestProperty("Content-Type", "application/json");

		// 开始连接请求
		urlConnection.connect();
		OutputStream out = urlConnection.getOutputStream();
		// 写入请求的字符串
		out.write(ParamsString.getBytes());
		out.flush();
		out.close();

		return makeContent(urlString, urlConnection);
	}

	public HttpRespons sendOptions(String urlString, String ParamsString, String Authorization) throws IOException {
		// 创建url资源
		URL url = new URL(urlString);
		// 建立http连接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置允许输出
		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		// 设置不用缓存
		urlConnection.setUseCaches(true);
		// 设置传递方式
		urlConnection.setRequestMethod("POST");
		// 设置维持长连接
		urlConnection.setRequestProperty("Connection", "Keep-Alive");
		// 设置文件字符集:
		urlConnection.setRequestProperty("Charset", "UTF-8");
		// 转换为字节数组
		byte[] data = ParamsString.getBytes();
		// 设置文件长度
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
		urlConnection.setRequestProperty("Authorization", Authorization);
		urlConnection.setRequestProperty("App-Version", "2.9.4.1");
		urlConnection.setRequestProperty("App-Channel", "100001");
		urlConnection.setRequestProperty("Repeated-Submission", "" + new Date().getTime());

		urlConnection.setRequestProperty("accept", "*/*");
		// 设置文件类型:
		urlConnection.setRequestProperty("Content-Type", "application/json");

		// 开始连接请求
		urlConnection.connect();
		OutputStream out = urlConnection.getOutputStream();
		// 写入请求的字符串
		out.write(ParamsString.getBytes());
		out.flush();
		out.close();

		return makeContent(urlString, urlConnection);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params, Map<String, String> propertys)
			throws IOException {
		return send(urlString, "POST", params, propertys);
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 * @return 响映对象
	 * @throws IOException
	 */
	private HttpRespons send(String urlString, String method, Map<String, String> parameters,
			Map<String, String> propertys) throws IOException {
		HttpURLConnection urlConnection = null;

		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}

		do {
			URL url = new URL(urlString);
			// HttpURLConnection.setFollowRedirects(true);
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestMethod(method);
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(true);
			urlConnection.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			urlConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");

			if (cookie.length() != 0) {
				urlConnection.setRequestProperty("Cookie", cookie);
			}

			urlConnection.setInstanceFollowRedirects(false);
			int code = urlConnection.getResponseCode();
			if (code == HttpURLConnection.HTTP_MOVED_TEMP) {
				cookie += urlConnection.getHeaderField("Set-Cookie") + ";";
			}
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK
					|| urlConnection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
				break;
			}
		} while (true);

		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}

		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
			urlConnection.setReadTimeout(500000);
		}

		return makeContent(urlString, urlConnection);
	}

	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return 响应对象
	 * @throws IOException
	 */
	private HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
		HttpRespons httpResponser = new HttpRespons();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, getDefaultContentEncoding()));
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();

			httpResponser.content = new String(temp.toString().getBytes());
			httpResponser.contentEncoding = defaultContentEncoding;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

//			getCookie(urlConnection);

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}

//	public String getCookie(HttpURLConnection urlConnection) {
//		
//		String cookieskey = "Set-Cookie";
//		Map<String, List<String>> maps = urlConnection.getHeaderFields();
//		List<String> coolist = maps.get(cookieskey);
//		Iterator<String> it = coolist.iterator();
//		StringBuffer sbu = new StringBuffer();
//		sbu.append("eos_style_cookie=default; ");
//		while(it.hasNext()){
//			sbu.append(it.next());
//		}
//		System.err.println(sbu.toString());
//		return sbu.toString();
//
//	}

	public String getCookie() {
		return this.cookie;
	}

	public void setCookie(String cookieStr) {
		this.cookie = cookieStr;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 默认的响应字符集
	 */
	public String getDefaultContentEncoding() {
		return this.defaultContentEncoding;
	}

	/**
	 * 设置默认的响应字符集
	 */
	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}
}
