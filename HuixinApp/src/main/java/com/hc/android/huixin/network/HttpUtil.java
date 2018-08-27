package com.hc.android.huixin.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.hc.android.huixin.util.LogUtil;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpUtil {

	private static final int NET_TIME_OUT = 100000;

	public static boolean networkIsAvailable(Activity act) {
		ConnectivityManager manager = (ConnectivityManager) act.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	public static String getFromUrl(String url) {
		String strResult = "";
		HttpClient httpclient = null;
		HttpResponse httpResponse;
		try {
			httpclient = getHttpClient();
			LogUtil.logE("get:url=" + url);
	
			HttpGet httpRequest = new HttpGet(url);
			httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			LogUtil.logE("get:strResult=" + strResult);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		
		return strResult;
	}
	public static String getFromUrlNoLog(String url) {
		String strResult = "";
		HttpClient httpclient = null;
		HttpResponse httpResponse;
		try {
			httpclient = getHttpClient();
			HttpGet httpRequest = new HttpGet(url);
			httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}

		return strResult;
	}
	/**
	 * json格式的post请求
	 */
	public static String postToUrl(String url, String data) {
		String strResult = "";
		HttpClient httpclient = null;
		try {
			LogUtil.logE("post:url=" + url);
			LogUtil.logE("post:data=" + data.toString());
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setEntity(new StringEntity(data, HTTP.UTF_8));//StringEntity发送的是json格式的数据
			httpclient = getHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			LogUtil.logE("post:strResult=" + strResult);
		} catch (Exception e) {
			LogUtil.logE("post:strResult" + e);
			return e.toString();
		}
		finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return strResult;
	}
	/**
	 * json格式的post请求
	 */
	public static String postToUrlNoLog(String url, String data) {
		String strResult = "";
		HttpClient httpclient = null;
		try {
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setEntity(new StringEntity(data, HTTP.UTF_8));//StringEntity发送的是json格式的数据
			httpclient = getHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			return e.toString();
		}
		finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return strResult;
	}
	/**
	 * 键值对格式的post请求
	 */
	public static String postToUrl(String url, List<NameValuePair> parameters) {
		String strResult = "";
		HttpClient httpclient = null;
		try {
			LogUtil.logE("post:url=" + url);
			HttpPost httpRequest = new HttpPost(url);
			LogUtil.logE("post:parameters=" + parameters);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//UrlEncodedFormEntity发送的是键值对格式的数据
			httpRequest.setEntity(entity);
			httpclient = getHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			LogUtil.logE("post:strResult=" + strResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return strResult;
	}

	private static DefaultHttpClient getHttpClient() {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, NET_TIME_OUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, NET_TIME_OUT);
		return client;
	}

	public static void download(String url, String savePath, INetCallback listener) {
		boolean result = false;
		HttpClient client = getHttpClient();
		HttpGet get = new HttpGet(url);
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			if (inputStream != null) {
				File file = new File(savePath);
				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				if (file.exists()) {
					file.delete();
				}
				fileOutputStream = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int ch = -1;
				while ((ch = inputStream.read(buf)) != -1) {
					fileOutputStream.write(buf, 0, ch);
				}
			}
			fileOutputStream.flush();
			result = true;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			listener.onCallback(result, null);
		}
	}
	public static String post(String urls,String data){
        try {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");

            //至少要设置的两个请求头
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", data.length()+"");

            //post的方式提交实际上是留的方式提交给服务器
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());

            //获得结果码
            int responseCode = connection.getResponseCode();
            if(responseCode ==200){
                //请求成功
                InputStream is = connection.getInputStream();
                return dealResponseResult(is);
            }else {
                //请求失败
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	  public static String dealResponseResult(InputStream inputStream) {
	      String resultData = null;      //存储处理结果
	       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      byte[] data = new byte[1024];
	      int len = 0;
	       try {
	          while((len = inputStream.read(data)) != -1) {
	             byteArrayOutputStream.write(data, 0, len);
	          }
	     } catch (IOException e) {
	         e.printStackTrace();
	        }
	       resultData = new String(byteArrayOutputStream.toByteArray());    
	       return resultData;
	   } 
}
