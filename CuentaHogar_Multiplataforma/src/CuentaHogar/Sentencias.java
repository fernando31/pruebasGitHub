package CuentaHogar;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Clase Sentencias (Elementos que gestionan las solicitudes del programa a la bbdd)
 * @author Fernando J. Gonzalez Lopez
 * @version 0.1
 */
public class Sentencias {
    
/*-----------------------------VARIABLES---------------------------------*/       
    
    //Variables
    private ConexionBBDD conectionBBDD;  
    private ElementosDinamicos ed;
    private static final String G = "Gastos";
    private static final String I = "Ingresos";
    private static final String T = "Traspaso";
    private static final String SINFILTRO = "";
    
    /**
     * Constructor vacio
     */
    public Sentencias() {
    
    }
    
    /**
     * Constructor con parametros (bbdd, login y password)
     * @param bbdd
     * @param login
     * @param password 
     */
    public Sentencias(String bbdd, String login, String password) {
        this.conectionBBDD = new ConexionBBDD(bbdd, login, password);
        this.ed = new ElementosDinamicos(bbdd, login, password);
    }
    
    /**
     * Metodo que devuelve el parametro G (Gastos)
     * @return Sting 
     */
    public static String getG() {
        return G;
    }

    /**
     * Metodo que devuelve el parametro I (Ingresos)
     * @return String
     */
    public static String getI() {
        return I;
    }

    /**
     * Metodo que devuelve el parametro T (Traspasos)
     * @return String
     */
    public static String getT() {
        return T;
    }

    /**
     * Metodo que devuelve el parametro "" (SinFiltro)
     * @return 
     */
    public static String getSinFiltro() {
        return SINFILTRO;
    }
    
    
 /*------------------------------SENTENCIAS GENERALES---------------------------------*/  
    
    /**
     * Metodo que carga la tabla de cuentas y los desplegables del programa correspondientes con los datos de las cuentas de la bbdd
     * @param tabla
     * @param desp
     * @throws Exception 
     */
    public void cargarTablaCuentas(JTable tabla, JComboBox desp, boolean ordenable) throws Exception {
        //Pasamos la consulta que queremos cargar y la tabla donde queremos que se cargue la consulta
        ed.pasarConsultaTabla("SELECT NOMBRE 'Nombre', NUMERO 'Numero', CAPITAL 'Capital'"
                                    + "FROM CUENTAS "
                                    + "WHERE TIPO = 'C';", tabla, ordenable);
        
        //Pasamos la tabla de donde queremos extraer los datos, el desplegable donde los queremos cargar y la columna de la tabla que contiene los datos
        ed.añadirDesplegable(tabla, desp, 0);
    }
    
    /**
     * Metodo que carga la tabla de tipo de movimiento y los desplegables del programa correspondientes con los datos de los tipos de movmientos de la bbdd
     * @param tipo
     * @param tabla
     * @param desp
     * @throws Exception 
     */
    public void cargarTablaTipoMov(String tipo, JTable tabla, JComboBox desp, boolean ordenable) throws Exception{
        //Pasamos la consulta que queremos cargar, el tipo de movimiento que es y la tabla donde queremos que se cargue la consulta
        ed.pasarConsultaTabla(
                "SELECT NOMBRE 'Nombre' "
                + "FROM CUENTAS "
                + "WHERE TIPO = '" + tipo.charAt(0) + "';", tabla, ordenable);
        
        //Pasamos la tabla de donde queremos extraer los datos, el desplegable donde los queremos cargar y la columna de la tabla que contiene los datos
        ed.añadirDesplegable(tabla, desp, 0);
    }
    
    /**
     * Metodo que carga la tabla de tipo de movimiento y los desplegables del programa correspondientes con los datos de los tipos de movmientos de la bbdd en funcion a un filtro
     * @param tipo
     * @param filtro
     * @param tabla
     * @throws Exception 
     */
    public void cargarListaMov(String tipo, String filtro, JTable tabla, boolean ordenable) throws Exception{
        //En funcion del tipo de movimiento se modificaran los titulos de las columnas de la tabla
        String campo1 = "Cuenta";
        String campo2 = tipo;
        if (tipo.equals("T")) {
            campo1 = "Cuenta Origen";
            campo2 = "Cuenta Destino";
        }

        //Pasamos la consulta que queremos cargar, el tipo de movimiento, el filtro que necesitemos y la tabla donde queremos que se cargue la consulta
        ed.pasarConsultaTabla(
                "SELECT C1.NOMBRE '" + campo1 + "', C2.NOMBRE '" + campo2 + "', M.FECHA 'Fecha', M.IMPORTE 'Importe' "
                + "FROM CUENTAS C1, CUENTAS C2, MOVIMIENTOS M "
                + "WHERE C1.ID_CUENTA = M.ID_CUENTA_G "
                + "AND C2.ID_CUENTA = M.ID_CUENTA_I "
                + "AND M.TIPO = '" + tipo.charAt(0) + "' "
                + filtro
                + "ORDER BY M.FECHA DESC;", tabla, ordenable);             
    }
     
    /**
     * Metodo para extraer el ID de una Cuenta
     * @param cuenta
     * @return int
     * @throws Exception 
     */
    private int solicitaIdCuenta(String cuenta) throws Exception{
        
        int idCuenta;
        
        idCuenta = ed.solicitaID(
                        "SELECT ID_CUENTA "
                        + "FROM CUENTAS "
                        + "WHERE NOMBRE ='" + cuenta + "';");
        
        return idCuenta;
    }
                  
    /**
     * Metodo para extraer el Id de un Movimiento
     * @param movimiento
     * @return int
     * @throws Exception 
     */
    private int solicitaIdMov(String movimiento) throws Exception {
        
        int idMovimiento;
        
        idMovimiento = ed.solicitaID(
                "SELECT ID_CUENTA "
                + "FROM CUENTAS "
                + "WHERE NOMBRE ='" + movimiento + "';");
        
        return idMovimiento;
    }   

 /*------------------------------SENTENCIAS ESPECIFICAS PARA LA TABLA MOVIMIENTOS---------------------------------*/     
    
                
    /**
     * Metodo que comprueba el ID de una fila de la tabla Movimientos en nuestra bbdd
     * @param tipo
     * @param valores
     * @return int
     * @throws Exception 
     */
    public int comprobarMovID(String tipo, String [] valores) throws Exception {
        //Variables
        int id = 0;
        int idCuenta;
        int idMovimiento;
        String cuenta = valores[0];
        String movimiento = valores[1];
        String fecha = valores[2];
        String importe = valores[3];

        //Recuperamos el ID de la Cuenta
        idCuenta = this.solicitaIdCuenta(cuenta);

        //Recuperamos el ID del Movimiento
        idMovimiento = this.solicitaIdMov(movimiento);
        
        //Recuperamos el ID que necesitamos de la fila solicitada (utilizando ell idCuenta, el idMovimiento y los valores pasados por parametro)
        id = ed.solicitaID(
                "SELECT ID_MOVIMIENTO "
                + "FROM MOVIMIENTOS "
                + "WHERE ID_CUENTA_G = " + idCuenta
                + " AND ID_CUENTA_I = " + idMovimiento
                + " AND TIPO = '" + tipo.charAt(0) + "' "
                + "AND FECHA = '" + fecha + "' "
                + "AND IMPORTE = " + importe + ";");

        //Devolvemos el ID
        return id;
    }
    
    /**
     * Metodo para insertar un Movimiento en nuestra bbdd y refrescar la tabla Movmientos
     * @param tipo
     * @param id
     * @param valores
     * @param tabla
     * @throws Exception 
     */
    public void insertarMov(String tipo, int id, String[] valores, JTable tabla, boolean ordenable) throws Exception {
        //Si el ID no existe insertamos el Movimiento
        if (id == 0) {
            
            //Variables
            int idCuenta;
            int idMovimiento;
            String cuenta = valores[0];
            String movimiento = valores[1];
            String fecha = valores[2];
            String importe = valores[3];

            //Recuperamos el ID de la Cuenta
            idCuenta = this.solicitaIdCuenta(cuenta);

            //Recuperamos el ID del Movimiento
            idMovimiento = this.solicitaIdMov(movimiento);

            //Insertamos en la bbdd el nuevo Movimiento
            conectionBBDD.ejecutarSentencia(
                    "INSERT INTO MOVIMIENTOS (ID_CUENTA_G, ID_CUENTA_I, TIPO, FECHA, IMPORTE) "
                    + "VALUES (" + idCuenta + ", " + idMovimiento + ", '" + tipo.charAt(0) + "', '" + fecha + "', " + importe + ");");

            //Recargamos nuestra tabla Movimientos
            this.cargarListaMov(tipo, SINFILTRO, tabla, ordenable);

        //Si el ID existe avisamos por pantalla y no insertamos nada (Es un movmimiento ya introducido)
        } else {
            JOptionPane.showMessageDialog(null, "Movimiento ya introducido");
        }   
    }
    
    /**
     * Metodo para eliminar un Movimiento en nuestra bbdd y refrescar la tabla Movmientos
     * @param tipo
     * @param id
     * @param tabla
     * @throws Exception 
     */
    public void eliminarMov(String tipo, int id, JTable tabla, boolean ordenable) throws Exception {
        //Comprobamos que la fila esta seleccionada
        if (tabla.getSelectedRowCount() > 0) {
            //Recuperamos los valores de la Tabla Movimientos
            String[] valores = ed.recuperarValoresFila(tabla);
            //Si el vector valores no esta vacio eliminamos la fila de nuestra bbdd
            if (valores != null) {
                conectionBBDD.ejecutarSentencia(
                        "DELETE FROM MOVIMIENTOS "
                        + "WHERE ID_MOVIMIENTO = " + id + ";");

            }
            //Recargamos nuestra tabla Movmimientos
            this.cargarListaMov(tipo, SINFILTRO, tabla, ordenable);
            
        //Si no hay ninguna fila seleccionada avisamos por pantalla (Ninguna fila seleccionada)
        } else {
            JOptionPane.showMessageDialog(null, "Ninguna fila seleccionada");
        }
    }    

    /**
     * Metodo para actualizar un Movimiento en nuestra bbdd y refrescar la tabla Movmientos
     * @param tipo
     * @param id
     * @param tabla
     * @throws Exception 
     */
    public void editarMov(String tipo, int id, JTable tabla, JTable tablaEd, boolean ordenable) throws Exception {
        //Comprobamos que la fila de la tabla Movimientos esta seleccionada
        if (tabla.getSelectedRowCount() > 0) {
            //Comprobamos que la fila de la tabla de Edicion esta seleccionada
            if (tablaEd.getSelectedRowCount() > 0) {
                //Variables
                int idCuenta;
                int idMovimiento;
                //Recuperamos los nuevos valores
                String[] valoresNuevos = ed.recuperarValoresFila(tablaEd);
                String cuenta = valoresNuevos[0];
                String movimiento = valoresNuevos[1];
                String fecha = valoresNuevos[2];
                String importe = valoresNuevos[3];
                
                //Recuperamos el ID de la Cuenta
                idCuenta = this.solicitaIdCuenta(cuenta);
                //Recuperamos el ID del Movimiento
                idMovimiento = this.solicitaIdMov(movimiento);
                
                //Actualizamos en la bbdd el Movimiento
                conectionBBDD.ejecutarSentencia(
                        "UPDATE MOVIMIENTOS "
                        + "SET "
                        + "ID_CUENTA_G = " + idCuenta + ", "
                        + "ID_CUENTA_I = " + idMovimiento + ", "
                        + "TIPO = '" + tipo.charAt(0) + "', "
                        + "FECHA = '" + fecha + "', "
                        + "IMPORTE = " + importe
                        + " WHERE ID_MOVIMIENTO = " + id + ";");

                //Recargamos nuestra tabla Movimientos
                this.cargarListaMov(tipo, SINFILTRO, tabla, ordenable);

            }
        }        
    }
        
}
