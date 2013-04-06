package com.cuentahogar;


import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class ListadosActivity extends MainActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listados);
	
		Log.i("Activity", "Creando pestañas");
		TabHost host = (TabHost) findViewById(R.id.Mov_TabHost);
		host.setup();
		
		TabSpec gastos = host.newTabSpec("gastos");
		gastos.setIndicator(getResources().getString(R.string.listado_gastos), getResources().getDrawable(R.drawable.gastos));
		gastos.setContent(R.id.ScrollViewGastos);
		host.addTab(gastos);
		
		TabSpec ingresos = host.newTabSpec("ingresos");
		ingresos.setIndicator(getResources().getString(R.string.listado_ingresos), getResources().getDrawable(R.drawable.ingresos));
		ingresos.setContent(R.id.ScrollViewIngresos);
		host.addTab(ingresos);
		
		TabSpec traspasos = host.newTabSpec("traspasos");
		traspasos.setIndicator(getResources().getString(R.string.listado_traspasos), getResources().getDrawable(R.drawable.traspasos));
		traspasos.setContent(R.id.ScrollViewTraspasos);
		host.addTab(traspasos);
		Log.i("Activity", "Pestañas creadas");

		Log.i("Activity", "Seleccionando pestaña por defecto");
		host.setCurrentTabByTag("gastos");
		
		Log.i("Activity", "Creando Tablas");
        TableLayout tablaGastos = (TableLayout) findViewById(R.id.TableLayout_Gastos);
        TableLayout tablaIngresos = (TableLayout) findViewById(R.id.TableLayout_Ingresos);
        TableLayout tablaTraspasos = (TableLayout) findViewById(R.id.TableLayout_Traspasos);
        
        try {
			createTableExpenses(tablaGastos);
			createTableIncome(tablaIngresos);
			createTableTransfer(tablaTraspasos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createTableExpenses(TableLayout tabla) throws IOException, SQLException{
		Log.i("Activity", "Cargando Tabla Gastos");
		String sentencia =
				"SELECT C1.NOMBRE 'Cuenta', C2.NOMBRE 'Gasto', M.FECHA 'Fecha', M.IMPORTE 'Importe' " +
				"FROM CUENTAS C1, CUENTAS C2, MOVIMIENTOS M " +
				"WHERE C1.ID_CUENTA = M.ID_CUENTA_G " +
				"AND C2.ID_CUENTA = M.ID_CUENTA_I " +
				"AND C2.TIPO = 'G' " + 
				"ORDER BY M.FECHA DESC;";
		
		String[] titles = insertHeaderRow(tabla, sentencia);		
		insertDataRow(tabla, titles, sentencia);
	}
	
	private void createTableIncome(TableLayout tabla) throws IOException, SQLException{
		Log.i("Activity", "Cargando Tabla Ingresos");
		String sentencia =
				"SELECT C1.NOMBRE 'Cuenta', C2.NOMBRE 'Ingreso', M.FECHA 'Fecha', M.IMPORTE 'Importe' " +
				"FROM CUENTAS C1, CUENTAS C2, MOVIMIENTOS M " +
				"WHERE C1.ID_CUENTA = M.ID_CUENTA_G " +
				"AND C2.ID_CUENTA = M.ID_CUENTA_I " +
				"AND C2.TIPO = 'I' " + 
				"ORDER BY M.FECHA DESC;";
		
		String[] titles = insertHeaderRow(tabla, sentencia);		
		insertDataRow(tabla, titles, sentencia);
	}
	
	private void createTableTransfer(TableLayout tabla) throws IOException, SQLException{
		Log.i("Activity", "Cargando Tabla Traspasos");
		String sentencia =
				"SELECT C1.NOMBRE 'C. Orig.', C2.NOMBRE 'C. Dest.', M.FECHA 'Fecha', M.IMPORTE 'Importe' " +
				"FROM CUENTAS C1, CUENTAS C2, MOVIMIENTOS M " +
				"WHERE C1.ID_CUENTA = M.ID_CUENTA_G " +
				"AND C2.ID_CUENTA = M.ID_CUENTA_I " +
				"AND M.TIPO = 'T' " +
				"ORDER BY M.FECHA DESC;";
		
		String[] titles = insertHeaderRow(tabla, sentencia);		
		insertDataRow(tabla, titles, sentencia);
	}

	private String[] insertHeaderRow(TableLayout table, String sentencia) throws IOException {
		Log.i("Activity", "Cargando cabecera tabla");
		TableRow headerRow = new TableRow(this);
		int textColor = getResources().getColor(R.color.listados_header_color);
		float textSize = getResources().getDimension(R.dimen.listados_header_size);

		String[] titles = bd.nombreColumnas(sentencia);

		for(int i = 0; i < titles.length; i++){
			addTextToRowWithValues(headerRow, titles[i], textColor,textSize);
		}
	
		table.addView(headerRow);
		
		return titles;
	}
	
    private void insertRow(final TableLayout table, String[] elements) throws IOException {
    	Log.i("Activity", "Cargando fila tabla");
        final TableRow newRow = new TableRow(this);
        int textColor = getResources().getColor(R.color.item_color);
        float textSize = getResources().getDimension(R.dimen.listados_row_size);

        for(int i= 0; i < elements.length; i++){
        	addTextToRowWithValues(newRow, elements[i], textColor,textSize);
        }
        
        table.addView(newRow);
    }
    
    private void insertDataRow(final TableLayout tabla, String[] titles, String sentencia) throws SQLException, IOException{
    	Log.i("Activity", "Recuperando datos de la BBDD");
    	String[] elements = new String[titles.length]; 	
    	ResultSet rs = (ResultSet) bd.ejecutarConsulta(sentencia);
    	 
        while(rs.next()){
        	
        	for (int i = 0; i < elements.length; i++){
        		elements[i] = rs.getString(titles[i]);
        	}

        	insertRow(tabla, elements);
        }
        
        bd.desconectar();
    }
 
	private void addTextToRowWithValues(final TableRow tableRow, String text, int textColor, float textSize) {
		Log.i("Activity", "Cargando elemento fila");
		TextView textView = new TextView(this);
		textView.setTextSize(textSize);
		textView.setTextColor(textColor);
		textView.setText(text);
		
		tableRow.addView(textView);
	}

}
