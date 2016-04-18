package com.test;


//回到接口
public interface FetcherCallback {

	void onData(Data data);
	void onError(Throwable cause);
}
