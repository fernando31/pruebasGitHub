package com.cuentahogar;

import android.app.Activity;
import android.app.Dialog;


public class MainActivity extends Activity {

	public static final String CUENTAHOGAR_PREFERENCES = "CuentaHogarPrefs";
	public static char movimiento;
	public static BDConectar bd = new BDConectar("CUENTAHOGAR", "fernando", "fernando");
	
	public static char getMovimiento() {
		return movimiento;
	}
	public static void setMovimiento(char movimiento) {
		MainActivity.movimiento = movimiento;
	}
	protected Dialog onCreateDialog() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
