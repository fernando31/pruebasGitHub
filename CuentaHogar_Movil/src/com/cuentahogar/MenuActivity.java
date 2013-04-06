package com.cuentahogar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends MainActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Activity", "Arrancando MenuActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		ListView menuList = (ListView) findViewById(R.id.Menu_ListView);
		String[] items = getResources().getStringArray(R.array.options_menu);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);
		menuList.setAdapter(adapt);
		
		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
				
					TextView textView = (TextView) itemClicked;
					String strText = textView.getText().toString();
					
					
					if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_gastos))) {

				
						Log.i("Activity", "Arrancando GastosActivity");		
						startActivity(new Intent(MenuActivity.this, MovimientoActivity.class));
						MainActivity.setMovimiento('G');
						
					} else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_ingresos))) {

						Log.i("Activity", "Arrancando IngresosActivity");
						startActivity(new Intent(MenuActivity.this, MovimientoActivity.class));
						MainActivity.setMovimiento('I');
						
					} else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_traspasos))) {

						Log.i("Activity", "Arrancando TraspasosActivity");
						startActivity(new Intent(MenuActivity.this, MovimientoActivity.class));
						MainActivity.setMovimiento('T');
						
					} else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_movimientos))) {
						
						Log.i("Activity", "Arrancando ListaMovimientosActivity");
						startActivity(new Intent(MenuActivity.this, ListadosActivity.class));
						
					} else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_ayuda))) {
						
						Log.i("Activity", "Arrancando AyudaActivity");
						startActivity(new Intent(MenuActivity.this, AyudaActivity.class));
					}
				}
			});		
		
		
	}



}
