package com.karcompany.heybeach.service;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachResultReceiver extends ResultReceiver {
	private Receiver receiver;

	// Constructor takes a handler
	public BeachResultReceiver(Handler handler) {
		super(handler);
	}

	// Setter for assigning the receiver
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	// Defines our event interface for communication
	public interface Receiver {
		void onReceiveResult(int resultCode, Bundle resultData);
	}

	// Delegate method which passes the result to the receiver if the receiver has been assigned
	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (receiver != null) {
			receiver.onReceiveResult(resultCode, resultData);
		}
	}
}
