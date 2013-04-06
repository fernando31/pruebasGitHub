package com.cuentahogar;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AyudaActivity extends MainActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ayuda);

		InputStream iFile = getResources().openRawResource(R.raw.ayuda);
		
		try {
			TextView helpText = (TextView) findViewById(R.id.Ayuda_TextView_Text);
			String strFile = inputStreamToString(iFile);
			helpText.setText(strFile);
		} catch (Exception e) {
			Log.e("DEBUG_TAG", "InputStreamToString failure", e);
		}

		
	}

	public String inputStreamToString(InputStream is) throws IOException {
		Log.i("Activity", "Leyendo ayuda.txt");
		StringBuffer sBuffer = new StringBuffer();
		DataInputStream dataIO = new DataInputStream(is);
		String strLine = null;
		
		while ((strLine = dataIO.readLine()) != null) {
			sBuffer.append(strLine + "\n");
		}
		
		dataIO.close();
		is.close();
		return sBuffer.toString();
	}


}
