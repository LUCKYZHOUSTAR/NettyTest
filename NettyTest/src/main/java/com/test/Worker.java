package com.test;

public class Worker {

	public void doWorker(){
		Fetcher fetcher=new MyFetcher(new Data(1, 0));
		fetcher.fetchData(new FetcherCallback() {			
			//发生错误时被调用
			public void onError(Throwable cause) {
					System.out.println("an error accour"+cause);
			}
			//数据接收时被回调
			public void onData(Data data) {
				System.out.println("data receive"+data);			
			}
		});
	}
	
	
	public static void main(String[] args) {
		Worker w=new Worker();
		w.doWorker();
	}
}
