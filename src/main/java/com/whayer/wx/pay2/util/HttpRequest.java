package com.whayer.wx.pay2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRequest {
	//连接超时时间，默认10秒
    private static final int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private static final int connectTimeout = 30000;
	/**
	 * post请求
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	public static String sendPost(String url, Object xmlObj) throws ClientProtocolException, IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		

        
		HttpPost httpPost = new HttpPost(url);
//		//解决XStream对出现双下划线的bug
//        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
//        xStreamForRequestPostData.alias("xml", xmlObj.getClass());
//        //将要提交给API的数据对象转换成XML格式数据Post给API
//        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
		
		String postDataXML = XStreamUtil.Obj2Xml(xmlObj);

        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
	}
	
	public static String getRequest(HttpServletRequest request) throws Exception{
//		InputStream inputStream;
//		StringBuffer sb = new StringBuffer();
//		inputStream = request.getInputStream();
//		String s;
//		BufferedReader in=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
//		while((s = in.readLine()) != null){
//			sb.append(s);
//		}
//		in.close();
//		inputStream.close();
//		return sb.toString();
		InputStream inputStream = request.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        return buffer.toString();
	}
	
	/**
	 * 自定义证书管理器，信任所有证书
	 * @author 
	 *
	 */
	public static class MyX509TrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			
		}
		@Override
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			
		}
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
      }
}
