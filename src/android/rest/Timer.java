package android.rest;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/** 
  * The Timer class allows a graceful exit when an application
  * is stalled due to a networking timeout. Once the timer is
  * set, it must be cleared via the reset() method, or the
  * timeout() method is called.
  * <p>
  * The timeout length is customizable, by changing the 'length'
  * property, or through the constructor. The length represents
  * the length of the timer in milliseconds.
  */
public class Timer extends AsyncTask<Void, Void, Void>
{
	private static final String SITESCOPE_TAG = "SITESCOPE";
	
	/** Rate at which timer is checked */
	protected int m_rate = 100;
	
	/** Length of timeout */
	private int m_length;

	/** Time elapsed */
	private int m_elapsed;

	/** timeout handler */
	private Handler m_handler;

	private Activity m_activity;
	
	/**
	  * Creates a timer of a specified length
	  * @param	length	Length of time before timeout occurs
	  */
	public Timer ( int length, Handler handler, Activity activity )
	{
		// Assign to member variable
		m_length = length;

		// Set time elapsed
		m_elapsed = 0;
		
		m_handler = handler;
		
		m_activity = activity;
	}

	
	/** Resets the timer back to zero */
	public synchronized void reset()
	{
		m_elapsed = 0;
	}


	// Override this to provide custom functionality
	public void timeout()
	{	
		if(!this.isCancelled())
		{
			Log.e(SITESCOPE_TAG, "timeout occurred.... terminating");
			
			// Send the processing result back to the main thread.
			Message toMain = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("result", "FAIL");
			toMain.setData(bundle);	 
			
			m_handler.sendMessage(toMain);
		}		
	}


	/** Performs timer specific code */
	@Override
	protected Void doInBackground(Void... params) {
		
		//first check that there is a network connection, either wireless of 3G
		//if none, fail immediately
		ConnectivityManager connectivityManager = (ConnectivityManager) m_activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if(activeNetworkInfo != null)
		{
			// Keep looping
			for (;;)
			{
				// Put the timer to sleep
				try
				{ 
					Thread.sleep(m_rate);					
				}
				catch (InterruptedException ioe) 
				{
					break;
				}

				// Use 'synchronized' to prevent conflicts
				synchronized ( this )
				{
					// Increment time remaining
					m_elapsed += m_rate;

					// Check to see if the time has been exceeded
					if (m_elapsed > m_length)
					{
						// Trigger a timeout
						timeout();
						break;
					}
				}

			}
		}
		else
		{
			Log.e(SITESCOPE_TAG, "No network connection detected");
			timeout();
		}
		
		return null;
	}
}