package common;

public class ThreadTest {

	public static class ThreadTest2 extends Thread 
	{
		Object message;

		public ThreadTest2(Object message)
		{
			this.message = message;
		}

		@Override
		public synchronized void run() {
			System.out.println("I am thread");
			MainClient.client.accept(message);
			notify();
		}
	}
}
