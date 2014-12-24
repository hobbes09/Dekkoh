package com.dekkoh.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import com.dekkoh.custom.exception.DekkohException;
import com.dekkoh.util.Constants.DekkohExceptionErrorCodes;
import com.dekkoh.util.IOUtils;
import com.dekkoh.util.Log;

/**
 * Wrapper to help make HTTP requests easier - after all, we want to make it
 * nice for the people.
 */
public final class HTTPRequestHelper {
	private static final String TAG = "HTTPRequestHelper";
	private static final String HTTP_PROTOCOL = "http";
	private static final String HTTPS_PROTOCOL = "https";
	private static int responseCode = 000;
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String DELETE = "DELETE";
	public static final String PUT = "PUT";

	public static String processGetRequest(String serviceURL) throws Exception {
		return processGetRequest(serviceURL, getJSONRequestHeader());
	}

	public static String processGetRequest(String serviceURL,
			String authorizationToken) throws Exception {
		return processGetRequest(serviceURL,
				getJSONRequestHeader(authorizationToken));
	}

	public static String processGetRequest(String serviceURL,
			String authorizationToken, String requestData) throws Exception {
		return processRequest(serviceURL, GET,
				getJSONRequestHeader(authorizationToken), requestData);
	}

	public static String processGetRequest(String serviceURL,
			Map<String, String> requestHeaders) throws Exception {
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
		responseCode = httpResponse.getStatusLine().getStatusCode();
		Log.i(TAG, "Response Code : " + responseCode);
		if (responseCode == 200) {
			String responseString = IOUtils.toString(httpResponse.getEntity()
					.getContent());
			Log.i(TAG, "Response string : " + responseString);
			return responseString;

		} else {
			throw new DekkohException(
					DekkohExceptionErrorCodes.NETWOR_ERROR_CODE,
					"Return responce code is not 200");
		}
	}

	public static String processPostRequest(String serviceURL,
			String requestData) throws Exception {
		return processPostRequest(serviceURL, getJSONRequestHeader(),
				requestData, null);
	}

	public static String processPostRequest(String serviceURL,
			String requestData, Map<String, String> responseHeaderMap)
			throws Exception {
		return processPostRequest(serviceURL, getJSONRequestHeader(),
				requestData, responseHeaderMap);
	}

	public static String processPostRequest(String serviceURL,
			String requestData, String authorizationToken) throws Exception {
		return processPostRequest(serviceURL,
				getJSONRequestHeader(authorizationToken), requestData, null);
	}

	public static String processPostRequest(String serviceURL,
			String requestData, String authorizationToken,
			Map<String, String> responseHeaderMap) throws Exception {
		return processPostRequest(serviceURL,
				getJSONRequestHeader(authorizationToken), requestData,
				responseHeaderMap);
	}

	public static String processPostRequest(String serviceURL,
			Map<String, String> requestHeaders, String requestData,
			Map<String, String> responseHeaderMap) throws Exception {
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
		if (responseHeaderMap != null) {
			Header[] headers = httpResponse.getAllHeaders();
			for (Header header : headers) {
				responseHeaderMap.put(header.getName(), header.getValue());
				if (Log.DEBUG) {
					Log.i(TAG, "Key : " + header.getName() + " ,Value : "
							+ header.getValue());
				}
			}
		}
		responseCode = httpResponse.getStatusLine().getStatusCode();
		Log.i(TAG, "Response Code : " + responseCode);
		if (responseCode == 200) {
			String responseString = IOUtils.toString(httpResponse.getEntity()
					.getContent());
			Log.i(TAG, "Response string : " + responseString);
			return responseString;

		} else {
			throw new DekkohException(
					DekkohExceptionErrorCodes.NETWOR_ERROR_CODE,
					"Return responce code is not 200");
		}
	}

	public static String processDeleteRequest(String serviceURL)
			throws Exception {
		return processDeleteRequest(serviceURL, getJSONRequestHeader());
	}

	public static String processDeleteRequest(String serviceURL,
			String authorizationToken) throws Exception {
		return processDeleteRequest(serviceURL,
				getJSONRequestHeader(authorizationToken));
	}

	public static String processDeleteRequest(String serviceURL,
			Map<String, String> requestHeaders) throws Exception {
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
		responseCode = httpResponse.getStatusLine().getStatusCode();
		Log.i(TAG, "Response Code : " + responseCode);
		if (responseCode == 200) {
			String responseString = IOUtils.toString(httpResponse.getEntity()
					.getContent());
			Log.i(TAG, "Response string : " + responseString);
			return responseString;

		} else {
			throw new DekkohException(
					DekkohExceptionErrorCodes.NETWOR_ERROR_CODE,
					"Return responce code is not 200");
		}
	}

	public static String processPutRequest(String serviceURL, String requestData)
			throws Exception {
		return processPutRequest(serviceURL, getJSONRequestHeader(),
				requestData);
	}

	public static String processPutRequest(String serviceURL,
			String requestData, String authorizationToken) throws Exception {
		return processPutRequest(serviceURL,
				getJSONRequestHeader(authorizationToken), requestData);
	}

	public static String processPutRequest(String serviceURL,
			Map<String, String> requestHeaders, String requestData)
			throws Exception {
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
		responseCode = httpResponse.getStatusLine().getStatusCode();
		Log.i(TAG, "Response Code : " + responseCode);
		if (responseCode == 200) {
			String responseString = IOUtils.toString(httpResponse.getEntity()
					.getContent());
			Log.i(TAG, "Response string : " + responseString);
			return responseString;

		} else {
			throw new DekkohException(
					DekkohExceptionErrorCodes.NETWOR_ERROR_CODE,
					"Return responce code is not 200");
		}
	}

	/**
	 * Method which processes http reqest
	 * 
	 * @param url
	 *            ServiceURL to hit
	 * @param httpMethod
	 * @param requestHeaders
	 *            requestHeaders map for call
	 * @param requestData
	 *            , body for the request
	 * @return response in string form
	 */
	public static String processRequest(String url, String httpMethod,
			Map<String, String> requestHeaders, String requestData) {
		return processRequest(url, httpMethod, requestHeaders, requestData, -1,
				-1, null);
	}

	/**
	 * @param url
	 * @param httpMethod
	 * @param requestHeaders
	 * @param requestData
	 * @param connectionTimeOut
	 * @param readTimeout
	 * @param responseHeaderMap
	 * @return
	 */
	public static String processRequest(String url, String httpMethod,
			Map<String, String> requestHeaders, String requestData,
			int connectionTimeOut, int readTimeout,
			Map<String, List<String>> responseHeaderMap) {
		return processRequest(url, httpMethod, requestHeaders, requestData,
				connectionTimeOut, readTimeout, responseHeaderMap, null);
	}

	/**
	 * Method which processes http reqest
	 * 
	 * @param url
	 *            ServiceURL to hit
	 * @param httpMethod
	 * @param requestHeaders
	 *            requestHeaders map for call
	 * @param requestData
	 *            , body for the request
	 * @param connectionTimeOut
	 * @param readTimeout
	 * @return response in string form
	 */
	public static String processRequest(String url, String httpMethod,
			Map<String, String> requestHeaders, String requestData,
			int connectionTimeOut, int readTimeout,
			Map<String, List<String>> responseHeaderMap, List<Cookie> cookieList) {
		if (Log.DEBUG) {
			Log.e(TAG, "********** processRequest **********");
			Log.i(TAG, "ServiceURL : " + url);
			Log.i(TAG, "HttpMethod : " + httpMethod);
			Log.i(TAG, "RequestData : " + requestData);
			if (requestHeaders != null)
				for (Entry<String, String> entry : requestHeaders.entrySet()) {
					Log.i(TAG, "RequestHeaders : " + "Key : " + entry.getKey()
							+ " ,Value : " + entry.getValue());
				}
			if (cookieList != null)
				for (Cookie cookie : cookieList) {
					Log.i(TAG, "Cookie : " + "Key : " + cookie.getName()
							+ ", Value : " + cookie.getValue());
				}
		}
		HttpURLConnection httpURLConnection = null;
		URL urlObj = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			urlObj = new URL(url);
			if (HTTP_PROTOCOL.equals(urlObj.getProtocol().toLowerCase())) {
				httpURLConnection = (HttpURLConnection) urlObj.openConnection();
			} else {
				trustAllHosts();
				httpURLConnection = (HttpsURLConnection) urlObj
						.openConnection();
				HttpsURLConnection.setDefaultHostnameVerifier(DO_NOT_VERIFY);
			}
			httpURLConnection.setRequestMethod(httpMethod);
			if (requestHeaders != null && requestHeaders.size() > 0) {
				for (final Map.Entry<String, String> entry : requestHeaders
						.entrySet()) {
					httpURLConnection.setRequestProperty(entry.getKey(),
							entry.getValue());
				}
			}
			if (-1 < connectionTimeOut)
				httpURLConnection.setConnectTimeout(connectionTimeOut);
			if (-1 < readTimeout)
				httpURLConnection.setReadTimeout(readTimeout);

			httpURLConnection.setDoInput(true);
			httpURLConnection.setUseCaches(true);
			if (requestData != null) {
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setRequestProperty("Content-length",
						requestData.length() + "");
			}
			if (cookieList != null && cookieList.size() > 0) {
				for (final Cookie cookie : cookieList) {
					httpURLConnection.setRequestProperty("Cookie",
							cookie.getName() + "=" + cookie.getValue());
					httpURLConnection.setRequestProperty(cookie.getName(),
							cookie.getValue());
				}
			}
			if (Log.DEBUG) {
				Map<String, List<String>> requestProperties = httpURLConnection
						.getRequestProperties();
				for (Entry<String, List<String>> entry : requestProperties
						.entrySet()) {
					String values = ", Value : ";
					for (String value : entry.getValue()) {
						values = values + "," + value;
					}
					Log.i(TAG,
							"RequestProperties : " + "Key : " + entry.getKey()
									+ values);
				}
			}
			httpURLConnection.connect();
			if (requestData != null) {
				outputStream = httpURLConnection.getOutputStream();
				if (outputStream != null) {
					outputStream.write(requestData.trim().getBytes());
					outputStream.flush();
				}
			}
			int responseCode = -1;
			responseCode = httpURLConnection.getResponseCode();
			if (responseHeaderMap != null) {
				responseHeaderMap.putAll(httpURLConnection.getHeaderFields());
			}
			Log.e(TAG, " Response code is:" + responseCode);
			// If it works fine
			if (responseCode == 200 || responseCode == 204) {
				try {
					inputStream = httpURLConnection.getInputStream();
					String responseString = IOUtils.toString(inputStream);
					Log.i(TAG, "ResponseString : " + responseString);
					return responseString;
				} catch (final SocketTimeoutException e) {
					throwTimeoutException(url, e);
				} catch (final ConnectException e) {
					throwTimeoutException(url, e);
				} catch (final Exception ex) {
					throwNetworkException(url, ex);
				}
			} else {
				inputStream = httpURLConnection.getErrorStream();
				Log.e(TAG, IOUtils.toString(inputStream));
				throw new DekkohException(
						DekkohExceptionErrorCodes.NETWOR_ERROR_CODE,
						"Return responce code is not 200");
			}
		} catch (final SocketTimeoutException e) {
			throwTimeoutException(url, e);
		} catch (final ConnectException e) {
			throwTimeoutException(url, e);
		} catch (final IOException e) {
			throwNetworkException(url, e);
		} finally {
			IOUtils.closeStream(inputStream);
			if (outputStream != null) {
				outputStream = null;
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
		return null;
	}

	// always verify the host - dont check for certificate
	final public static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	/**
	 * Trust every server - dont check for any certificate
	 */
	public static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			final SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (final Exception ex) {
			Log.e(TAG, ex.getMessage(), ex);
		}
	}

	private static void throwNetworkException(String url, Exception exception) {
		Log.e(TAG, "Error while making call to server for url ->" + url);
		Log.e(TAG, exception.getMessage(), exception);
		throw new DekkohException(DekkohExceptionErrorCodes.NETWOR_ERROR_CODE,
				exception.getMessage());
	}

	public static void throwTimeoutException(String url, Exception exception) {
		Log.e(TAG, "Request timeout while making call to server for url ->"
				+ url);
		Log.e(TAG, exception.getMessage(), exception);
		throw new DekkohException(
				DekkohExceptionErrorCodes.NETWOR_TIMEOUT_CODE,
				exception.getMessage());
	}

	public static Map<String, String> getJSONRequestHeader(
			String authorizationToken) {
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");
		requestHeaders.put("Content-type", "application/json");
		if (authorizationToken != null) {
			requestHeaders.put("Authorization", authorizationToken);
		}
		return requestHeaders;
	}

	public static Map<String, String> getJSONRequestHeader() {
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");
		requestHeaders.put("Content-type", "application/json");
		return requestHeaders;
	}

	public static int getResponseCode() {
		return responseCode;
	}
}
