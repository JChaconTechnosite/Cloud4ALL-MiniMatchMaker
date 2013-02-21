package com.cloud4all.minimatchmaker;

import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/*

MiniMatchMakerService
This class is a communication layout between Flow manager and Mini match maker's kernel.	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */

public class MiniMatchMakerService extends Service {

	public interface MiniMatchMakerListener {
		void MiniMatchMakerResponse(JSONObject data);
	}


	private final IBinder mBinder = new MyBinder();
	private MiniMatchMakerEngine engine = null;
	private MiniMatchMakerListener listener = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		MiniMatchMakerService getService() {
			return MiniMatchMakerService.this;
		}
	}

	public MiniMatchMakerService() {
		engine = new MiniMatchMakerEngine(this);
	}

	public MiniMatchMakerEngine getEngine() {
		return engine;
	}


	// ** Listener

	public void registerListener(MiniMatchMakerListener listenerObject) {
		listener = listenerObject;
		Log.i("MiniMatchMakerService", "Object registered as listener of the service");
	}

	// ** Input method

	public void insertData(JSONObject data) {
		if (engine == null) engine = new MiniMatchMakerEngine(this);
		Log.i("MiniMatchMakerService", "Sending data to the engine.");
		engine.receiveData(data);
	}

	// ** Output method

	public void ResponseData(JSONObject data) {
		if (listener!=null) {
			Log.i("MiniMatchMakerService", "Sending data to the listener.");
			listener.MiniMatchMakerResponse(data);		
		}
	}



}
