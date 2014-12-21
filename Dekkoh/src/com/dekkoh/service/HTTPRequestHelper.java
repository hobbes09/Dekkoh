package com.dekkoh.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import com.dekkoh.util.IOUtils;
import com.dekkoh.util.Log;

/**
 * Wrapper to help make HTTP requests easier - after all, we want to make it
 * nice for the people.
 */
public final class HTTPRequestHelper {
	private static final String TAG = "HTTPRequestHelper";

	public String processGetRequest(String serviceURL)
			throws ClientProtocolException, IOException {
		return processGetRequest(serviceURL, getJSONRequestHeader());
	}

	public String processGetRequest(String serviceURL,
			Map<String, String> requestHeaders) throws ClientProtocolException,
			IOException {
		if (Log.DEBUG) {
			Log.e(TAG, "********** processGetRequest **********");
			Log.i(TAG, "ServiceURL : " + serviceURL);
			if (requestHeaders != null)
				for (Entry<String, String> entry : requestHeaders.entrySet()) {
					Log.i(TAG, "RequestHeaders : " + "Key : " + entry.getKey()
							+ " ,Value : " + entry.getValue());
				}
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				APIConstants.TIME_OUT);
		HttpGet httpGet = new HttpGet(serviceURL);
		if (requestHeaders != null && requestHeaders.size() > 0) {
			for (final Map.Entry<String, String> entry : requestHeaders
					.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpResponse httpResponse = httpClient.execute(httpGet);
		String responseString = IOUtils.toString(httpResponse.getEntity()
				.getContent());
		Log.d(TAG, "Response string : " + responseString);
		return responseString;
	}

	public String processPostRequest(String serviceURL, String requestData)
			throws ClientProtocolException, IOException {
		return processPostRequest(serviceURL, getJSONRequestHeader(),
				requestData);
	}

	public String processPostRequest(String serviceURL,
			Map<String, String> requestHeaders, String requestData)
			throws ClientProtocolException, IOException {
		if (Log.DEBUG) {
			Log.e(TAG, "********** processPostRequest **********");
			Log.i(TAG, "ServiceURL : " + serviceURL);
			Log.i(TAG, "RequestData : " + requestData);
			if (requestHeaders != null)
				for (Entry<String, String> entry : requestHeaders.entrySet()) {
					Log.i(TAG, "RequestHeaders : " + "Key : " + entry.getKey()
							+ " ,Value : " + entry.getValue());
				}
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				APIConstants.TIME_OUT);
		HttpPost httpPost = new HttpPost(serviceURL);
		if (requestHeaders != null && requestHeaders.size() > 0) {
			for (final Map.Entry<String, String> entry : requestHeaders
					.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		final HttpEntity httpEntity = new StringEntity(requestData);
		httpPost.setEntity(httpEntity);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String responseString = IOUtils.toString(httpResponse.getEntity()
				.getContent());
		Log.d(TAG, "Response string : " + responseString);
		return responseString;
	}

	public String processDeleteRequest(String serviceURL)
			throws ClientProtocolException, IOException {
		return processDeleteRequest(serviceURL, getJSONRequestHeader());
	}

	public String processDeleteRequest(String serviceURL,
			Map<String, String> requestHeaders) throws ClientProtocolException,
			IOException {
		if (Log.DEBUG) {
			Log.e(TAG, "********** processDeleteRequest **********");
			Log.i(TAG, "ServiceURL : " + serviceURL);
			if (requestHeaders != null)
				for (Entry<String, String> entry : requestHeaders.entrySet()) {
					Log.i(TAG, "RequestHeaders : " + "Key : " + entry.getKey()
							+ " ,Value : " + entry.getValue());
				}
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				APIConstants.TIME_OUT);
		HttpDelete httpDelete = new HttpDelete(serviceURL);
		if (requestHeaders != null && requestHeaders.size() > 0) {
			for (final Map.Entry<String, String> entry : requestHeaders
					.entrySet()) {
				httpDelete.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpResponse httpResponse = httpClient.execute(httpDelete);
		String responseString = IOUtils.toString(httpResponse.getEntity()
				.getContent());
		Log.d(TAG, "Response string : " + responseString);
		return responseString;
	}

	public String processPutRequest(String serviceURL, String requestData)
			throws ClientProtocolException, IOException {
		return processPostRequest(serviceURL, getJSONRequestHeader(),
				requestData);
	}

	public String processPutRequest(String serviceURL,
			Map<String, String> requestHeaders, String requestData)
			throws IllegalStateException, IOException {
		if (Log.DEBUG) {
			Log.e(TAG, "********** processPutRequest **********");
			Log.i(TAG, "ServiceURL : " + serviceURL);
			Log.i(TAG, "RequestData : " + requestData);
			if (requestHeaders != null)
				for (Entry<String, String> entry : requestHeaders.entrySet()) {
					Log.i(TAG, "RequestHeaders : " + "Key : " + entry.getKey()
							+ " ,Value : " + entry.getValue());
				}
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				APIConstants.TIME_OUT);
		HttpPut httpPut = new HttpPut(serviceURL);
		if (requestHeaders != null && requestHeaders.size() > 0) {
			for (final Map.Entry<String, String> entry : requestHeaders
					.entrySet()) {
				httpPut.addHeader(entry.getKey(), entry.getValue());
			}
		}
		final HttpEntity httpEntity = new StringEntity(requestData);
		httpPut.setEntity(httpEntity);
		HttpResponse httpResponse = httpClient.execute(httpPut);
		String responseString = IOUtils.toString(httpResponse.getEntity()
				.getContent());
		Log.d(TAG, "Response string : " + responseString);
		return responseString;
	}

	public static Map<String, String> getJSONRequestHeader() {
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");
		requestHeaders.put("Content-type", "application/json");
		return requestHeaders;
	}
}
