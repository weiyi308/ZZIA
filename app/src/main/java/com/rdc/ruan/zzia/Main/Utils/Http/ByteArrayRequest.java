package com.rdc.ruan.zzia.Main.Utils.Http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * ByteArrayRequest override getBody() and getParams()
 * 
 * @author steven pan
 * 
 */
class ByteArrayRequest extends Request<byte[]> {

	private final Listener<byte[]> mListener;

    private Map<String, String> mHeaders=new HashMap<String, String>(1);

	private Object mPostBody = null;

	private HttpEntity httpEntity =null;

	private static final int SOCKET_TIMEOUT = 20000;

	public ByteArrayRequest(int method, String url, Object postBody, Listener<byte[]> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mPostBody = postBody;
		this.mListener = listener;
		Log.e("ByteArrayRequest=","before");
		if (this.mPostBody != null && this.mPostBody instanceof RequestParams) {// contains file
			this.httpEntity = ((RequestParams) this.mPostBody).getEntity();
			Log.e("ByteArrayRequest=","after");
		}
	}



	public void setmHeaders(Map<String, String> mHeaders) {
		this.mHeaders = mHeaders;
	}

	/**
	 * mPostBody is null or Map<String, String>, then execute this method
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, String> getParams() throws AuthFailureError {
		Log.e("getParams","before");
		if (this.httpEntity == null && this.mPostBody != null && this.mPostBody instanceof Map<?, ?>) {
			Log.e("getParams","after");
			return ((Map<String, String>) this.mPostBody);//common Map<String, String>
		}
		return null;//process as json, xml or MultipartRequestParams
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
	}

	@Override
	public String getBodyContentType() {
		if (httpEntity != null) {
			return httpEntity.getContentType().getValue();
		}
		return null;
	}

	@Override
	public RetryPolicy getRetryPolicy() {
		RetryPolicy retryPolicy = new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return retryPolicy;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (this.mPostBody != null && this.mPostBody instanceof String) {//process as json or xml
			String postString = (String) mPostBody;
			if (postString.length() != 0) {
				try {
					return postString.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				return null;
			}
		}
		if (this.httpEntity != null) {//process as MultipartRequestParams
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					httpEntity.writeTo(baos);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			return baos.toByteArray();
		}
		return super.getBody();// mPostBody is null or Map<String, String>
	}

	@Override
	protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
		Log.e("size",response.headers.size()+"");
		for(String value:response.headers.values()) {
			Log.e("location", value+ "");
		}
		return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(byte[] response) {
		this.mListener.onResponse(response);
	}

    public void setCookie(String cookie){
        mHeaders.put("Cookie", cookie);
    }
	public void setApiKey(String apiKey){
		mHeaders.put("apikey",apiKey);
	}
	public void setReferer(String Referer){
		mHeaders.put("Referer",Referer);
	}

	public void setCacheTime(long time){
		mHeaders.put("Cache-Control","max-age=" +time);
	}

}