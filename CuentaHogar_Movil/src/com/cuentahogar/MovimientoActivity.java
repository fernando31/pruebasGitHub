package com.cuentahogar;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.ResultSet;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences.Editor;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class MovimientoActivity extends MainActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Activity", "Arrancando Movimiento");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movimiento);
			
		try {
			this.seleccionarTipoMov();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void seleccionarTipoMov() throws IOException, SQLException{
		Log.i("Activity", "Cargando TextView Movimiento");
		TextView title = (TextView) findViewById(R.id.Movimiento_TextView_Title);
		TextView cuenta = (TextView) findViewById(R.id.Movimiento_TextView_Cuenta);
		TextView tipo = (TextView) findViewById(R.id.Movimiento_TextView_Tipo);
		
		Spinner spinnerCta = (Spinner) findViewById(R.id.Movimiento_Spinner_Cuenta);
		this.loadAccount(spinnerCta);
		Spinner spinnerMov = (Spinner) findViewById(R.id.Movimiento_Spinner_Tipo);

		if (MainActivity.getMovimiento() == 'G'){
			Log.i("Activity", "El Movimiento es un Gasto");
			title.setText(R.string.menu_item_gastos);
			cuenta.setText(R.string.movimiento_cuenta);
			tipo.setText(R.string.movimiento_gasto);
			this.loadExpenses(spinnerMov);
		}else if(getMovimiento() == 'I'){
			Log.i("Activity", "El Movimiento es un Ingreso");
			title.setText(R.string.menu_item_ingresos);
			cuenta.setText(R.string.movimiento_cuenta);
			tipo.setText(R.string.movimiento_ingreso);
			this.loadInncome(spinnerMov);
		}else if(getMovimiento() == 'T'){
			Log.i("Activity", "El Movimiento es un Traspaso");
			title.setText(R.string.menu_item_traspasos);
			cuenta.setText(R.string.movimiento_traspaso_origen);
			tipo.setText(R.string.movimiento_traspaso_destino);
			this.loadAccount(spinnerMov);
		}		
	}
		
	@SuppressWarnings("deprecation")
	public void onClickAceptarButton(View view) {
		Log.i("Activity", "Boton Aceptar seleccionado");
		
		Spinner spinnerCta = (Spinner) findViewById(R.id.Movimiento_Spinner_Cuenta);
		Spinner spinnerMov = (Spinner) findViewById(R.id.Movimiento_Spinner_Tipo);
		EditText editTextImporte = (EditText) findViewById(R.id.Movimiento_EditText_Importe);
		TextView textViewFecha = (TextView) findViewById(R.id.Movimiento_TextView_fecha_info);

	    String cuenta = spinnerCta.getSelectedItem().toString();
	    String movimiento = spinnerMov.getSelectedItem().toString();
	    String importe = editTextImporte.getText().toString();
	    String fecha = (String) textViewFecha.getText();

	    insertMov(cuenta, movimiento, importe, fecha, MainActivity.getMovimiento());
		//Toast.makeText(MovimientoActivity.this, "TODO: Aceptar", Toast.LENGTH_LONG).show();
	}
	
	@SuppressWarnings("deprecation")
	public void onClickFechaButton(View view) {
		Log.i("Activity", "Boton Fecha seleccionada");
		showDialog(0);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Log.i("Activity", "Generando Dialog de Fecha");
		Calendar now = Calendar.getInstance();
		
		Log.i("Activity", "Escuchando Fecha");
		DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Time date = new Time();
				date.set(dayOfMonth, monthOfYear, year);
		
				TextView fecha = (TextView) findViewById(R.id.Movimiento_TextView_fecha_info);
				long aux = date.toMillis(true);
				fecha.setText(DateFormat.format("yyyy/MM/dd", aux));

			}
		}, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		return dateDialog;
	}
	
	@SuppressWarnings("deprecation")
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		
		DatePickerDialog dateDialog = (DatePickerDialog) dialog;
		int iDay, iMonth, iYear;
		
		Calendar cal = Calendar.getInstance();
		iDay = cal.get(Calendar.DAY_OF_MONTH);
		iMonth = cal.get(Calendar.MONTH);
		iYear = cal.get(Calendar.YEAR);
		
		dateDialog.updateDate(iYear, iMonth, iDay);
		return;
	}
	
	private void loadExpenses(Spinner spinner) throws IOException, SQLException{
		String sentencia =
				"SELECT NOMBRE " +
				"FROM CUENTAS " +
				"WHERE TIPO = 'G';";
		
		loadElementsSpinner(sentencia, spinner);
	}
	
	private void loadInncome(Spinner spinner) throws IOException, SQLException{
		String sentencia =
				"SELECT NOMBRE " +
				"FROM CUENTAS " +
				"WHERE TIPO = 'I';";
		
		loadElementsSpinner(sentencia, spinner);
	}
	
	private void loadAccount(Spinner spinner) throws IOException, SQLException{
		String sentencia =
				"SELECT NOMBRE " +
				"FROM CUENTAS " +
				"WHERE TIPO = 'C';";
		
		loadElementsSpinner(sentencia, spinner);
	}
	
	private void loadElementsSpinner(String sentencia, Spinner spinner) throws IOException, SQLException{
		
		ArrayList<String> elements = new ArrayList<String>();
		String[] titles = bd.nombreColumnas(sentencia);
		
		ResultSet rs = (ResultSet) bd.ejecutarConsulta(sentencia);
		while (rs.next()) {
			for (int i = 0; i < titles.length; i++) {
				elements.add(rs.getString(titles[i]));
			}
		}
		bd.desconectar();
		
		loadSpinner(elements, spinner);		
	}

	private void loadSpinner(ArrayList<String> elements, Spinner spinner) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
 
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {	
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	
    private int comprobarMovID(String cuenta, String movimiento, String importe, String fecha, char tipo){

        int id = 0;
        int idCuenta;
        int idMovimiento;
        
        if(tipo == 'G' || tipo == 'T'){
        	importe = "-" + importe;
        }
        
        try{
            idCuenta = bd.solicitaID(
                "SELECT ID_CUENTA " +
                    "FROM CUENTAS " +
                        "WHERE NOMBRE ='" + cuenta + "';"); 

            idMovimiento = bd.solicitaID(
                          "SELECT ID_CUENTA " +
                            "FROM CUENTAS " +
                                "WHERE NOMBRE ='" + movimiento + "';"); 
            id = bd.solicitaID(
                    "SELECT ID_MOVIMIENTO " + 
                        "FROM MOVIMIENTOS " + 
                            "WHERE ID_CUENTA_G = " + idCuenta + 
                            " AND ID_CUENTA_I = " + idMovimiento  + 
                            " AND TIPO = '" + tipo + "' " +
                            "AND FECHA = '" + fecha + "' " +
                            "AND IMPORTE = " + importe + ";"
                    );
        }catch (IOException ex) {}

        return id;
    }
    
    
	private void insertMov(String cuenta, String movimiento, String importe, String fecha, char tipo){
		
        int id = this.comprobarMovID(cuenta, movimiento, importe, fecha, tipo);
        
        int idCuenta;
        int idMovimiento;
      
        if(tipo == 'G' || tipo == 'T'){
        	importe = "-" + importe;
        }
        
        if(id == 0){
            try {

                idCuenta = bd.solicitaID(
                        "SELECT ID_CUENTA " +
                            "FROM CUENTAS " +
                                "WHERE NOMBRE ='" + cuenta + "';"); 
                
                System.out.println(idCuenta);

                idMovimiento = bd.solicitaID(
                          "SELECT ID_CUENTA " +
                            "FROM CUENTAS " +
                                "WHERE NOMBRE ='" + movimiento + "';"); 
                
                System.out.println(idMovimiento);

                bd.ejecutarSentencia( 
                        "INSERT INTO MOVIMIENTOS (ID_CUENTA_G, ID_CUENTA_I, TIPO, FECHA, IMPORTE) " +
                            "VALUES (" + idCuenta + ", " + idMovimiento + ", '" + tipo + "', '" + fecha + "', " + importe + ");");
                
                Toast.makeText(MovimientoActivity.this, "Movimiento insertado correctamente", Toast.LENGTH_LONG).show();

        		EditText editTextImporte = (EditText) findViewById(R.id.Movimiento_EditText_Importe);
        		TextView textViewFecha = (TextView) findViewById(R.id.Movimiento_TextView_fecha_info);
        		editTextImporte.setText("");
        	    textViewFecha.setText(R.string.movimiento_fecha_info);             
                

            }catch (IOException ex) {
            	Toast.makeText(MovimientoActivity.this, "Fallo al insertar movimiento", Toast.LENGTH_LONG).show();
            } 
            

        }else {
        	Toast.makeText(MovimientoActivity.this, "Movimiento repetido", Toast.LENGTH_LONG).show();
        }      	
	
		
	}
}
