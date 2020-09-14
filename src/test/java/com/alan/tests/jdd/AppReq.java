package com.alan.tests.jdd;

import com.alan.utils.HttpRequester;
import com.alan.utils.HttpRespons;
import com.jdd.fm.core.exception.AesException;
import com.jdd.fm.core.utils.TransferAesEncrypt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;


public class AppReq {
	
	/**
     * unicode 转换成 中文
     * 
     * @author leon 2016-3-15
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') aChar = '\t';
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
	
	 public final static String AESKEY = "ZnMmIU50NSF0Rm0mdlFUYUg=";
	
	/**
     * 加密
     * */
    public static String encrypt(String content) throws AesException {
        try {
            String result = TransferAesEncrypt.aesEncrypt(content, AESKEY, "utf-8");
            return result;
        } catch (Exception e) {
            throw new AesException("AES加密失败：Aescontent = " + content + "; charset = " + "utf-8", e);
        }
    }
    
	public static String getResStr(String url, String params) throws Exception {
		// String url = DataUrls.appadmin_url;
		// String params = DataUrls.params_90332;
		

		// 加密
		String content = TransferAesEncrypt.aesEncrypt(params, "d3YmI1BUOSE2S2YmalBVZUQ=", "utf-8");
		String re = "request=" + java.net.URLEncoder.encode(content, "utf-8");
		
//		LogWrite.saveToFile(java.net.URLDecoder.decode(java.net.URLEncoder.encode(content, "utf-8")));
		
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("UTF-8");
		HttpRespons hr = request.sendPost(url, re);
		String json = hr.getContent();

		if (json == null || json.equals("")) {
			System.out.println("Requester empty content");
		} else {
			System.out.println(json);
		}
		return json;
	}
	
	public static String getResStrNotAes(String url, String params) throws Exception {

		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("UTF-8");
		HttpRespons hr = request.sendPost(url, params);
		String json = hr.getContent();

		if (json == null || json.equals("")) {
			System.out.println("Requester empty content");
		} else {
			System.out.println(json);
		}
		return json;
	}
	
	public static String getResStrNotAes(String url,String params, String authorization) throws Exception {

		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("UTF-8");
		HttpRespons hr = request.sendPostUseAuthorization(url, params, authorization);
		String json = hr.getContent();

		if (json == null || json.equals("")) {
			System.out.println("Requester empty content");
		} else {
			System.out.println(json);
		}
		return json;
	}
	
	public static String getResStrByOptions(String url,String params, String authorization) throws Exception {

		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("UTF-8");
		HttpRespons hr = request.sendOptions(url, params, authorization);
		String json = hr.getContent();

		if (json == null || json.equals("")) {
			System.out.println("Requester empty content");
		} else {
			System.out.println(json);
		}
		return json;
	}
	
	public static String getResStrByGet(String url) throws Exception{
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("UTF-8");
		HttpRespons hr = request.sendGet(url);
		String json = hr.getContent();
		
		if (json == null || json.equals("")) {
			System.out.println("Requester empty content");
		} else {
			System.out.println(json);
		}
		return json;
	}
	
	public static String getResStrByGet(String url, String encoding) throws Exception{
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding(encoding);
		HttpRespons hr = request.sendGet(url);
		String json = hr.getContent();
		
		if (json == null || json.equals("")) {
			System.out.println("Requester empty content");
		} else {
			System.out.println(json);
		}
		return json;
	}
	
	public static String setParmas(String params, String Params) {
		JSONObject obj = JSONObject.fromObject(params);
		if (!Params.equals("")) {
			String[] hp = Params.split(";");
			for (int i = 0; i < hp.length; i++) {
				String param = hp[i];
				String[] p = param.split(",");
				obj.put(p[0], p[1]);
			}
		}

		params = obj.toString();
		return params;
	}

	public static String setParams(String params, String hParams, String bParams) {

		JSONObject obj = JSONObject.fromObject(params);
		JSONObject header = obj.getJSONObject("header");

		if (!hParams.equals("")) {
			String[] hp = hParams.split(";");
			for (int i = 0; i < hp.length; i++) {
				String param = hp[i];
				String[] p = param.split(",");
				header.put(p[0], p[1]);
			}
		}
		obj.put("header", header);

		String bodys = obj.getString("body");
		JSONObject body = JSONObject.fromObject(bodys);

		if (!bParams.equals("")) {
			String[] bp = bParams.split(";");
			for (int i = 0; i < bp.length; i++) {
				String param = bp[i];
				String[] p = param.split(",");
				body.put(p[0], p[1]);
			}
		}
		obj.put("body", body);
		params = obj.toString();
		return params;
	}
	
	public static String setParamsLY(String params, String hParams, String bParams) {

		JSONObject obj = JSONObject.fromObject(params);
		JSONObject request = obj.getJSONObject("request");
		JSONObject header = request.getJSONObject("header");

		if (!hParams.equals("")) {
			String[] hp = hParams.split(";");
			for (int i = 0; i < hp.length; i++) {
				String param = hp[i];
				String[] p = param.split(",");
				header.put(p[0], p[1]);
			}
		}
		request.put("header", header);

		JSONObject body = request.getJSONObject("body");

		if (!bParams.equals("")) {
			String[] bp = bParams.split(";");
			for (int i = 0; i < bp.length; i++) {
				String param = bp[i];
				String[] p = param.split(",");
				body.put(p[0], p[1]);
			}
		}
		request.put("body", body);
		params = obj.toString();
		System.err.println(params);
		return params;
	}
	
	public static String setSaveNewsParams(String params, String reP) {

		JSONObject obj = JSONObject.fromObject(params);

		if (!reP.equals("")) {
			String[] rep = reP.split(";");
			for (int i = 0; i < rep.length; i++) {
				String param = rep[i];
				String[] p = param.split(",");
				obj.put(p[0], p[1]);
			}
		}
		
		params = obj.toString();
		return params;
	}
	
	public static String setParams(String params, String hParams, String bParams, String nParams) {

		JSONObject obj = JSONObject.fromObject(params);
		JSONObject header = obj.getJSONObject("header");
		if (!hParams.equals("")) {
			String[] hp = hParams.split(";");
			for (int i = 0; i < hp.length; i++) {
				String param = hp[i];
				String[] p = param.split(",");
				header.put(p[0], p[1]);
			}
		}
		obj.put("header", header);

		String bodys = obj.getString("body");
		JSONObject body = JSONObject.fromObject(bodys);
		if (!bParams.equals("")) {
			String[] bp = bParams.split(";");
			for (int i = 0; i < bp.length; i++) {
				String param = bp[i];
				String[] p = param.split(",");
//				if (p[0].equals("Money") || p[0].equals("LotteryID") || p[0].equals("Multiple")) {
//					body.put(p[0], Integer.valueOf(p[1]));
//				} else {
					body.put(p[0], p[1]);
//				}
			}
		}
		
		String NumberS = body.getString("Number");
		JSONObject Number = (JSONObject) JSONArray.fromObject(NumberS).get(0);
		if (!nParams.equals("")) {
			String[] np = nParams.split(";");
			for (int i = 0; i < np.length; i++) {
				String param = np[i];
				String[] p = param.split(",");
//				if (p[0].equals("playid")) {
//					Number.put(p[0], Integer.valueOf(p[1]));
//				} else {
					Number.put(p[0], p[1]);
//				}
			}
		}
		body.put("Number", "[" + Number + "]");
		
		obj.put("body", body);
		params = obj.toString();
		return params;
	}

	@SuppressWarnings("rawtypes")
	public static String setParams(String params, Map<String, String> hParams, Map<String, String> bParams) {

		JSONObject obj = JSONObject.fromObject(params);
		JSONObject header = obj.getJSONObject("header");

		if (!hParams.equals(null)) {
			for (Map.Entry entry : hParams.entrySet()) {
				header.put(entry.getKey(), entry.getValue());
			}
		}
		obj.put("header", header);

		String bodys = obj.getString("body");
		JSONObject body = JSONObject.fromObject(bodys);
		if (!bParams.equals(null)) {
			for (Map.Entry entry : bParams.entrySet()) {
				body.put(entry.getKey(), entry.getValue());
			}
		}
		obj.put("body", body);
		params = obj.toString();
		return params;
	}

//	public static void main(String[] args) throws AesException, IOException {
//
//	}
}
