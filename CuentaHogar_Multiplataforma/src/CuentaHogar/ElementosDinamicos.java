package CuentaHogar;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Clase ElementosDinamicos (Metodos dinamicos utiles para cualquier programa) 
 * @author Fernando J. Gonzalez Lopez
 * @version 0.1
 */
public class ElementosDinamicos {
    
/*-----------------------------VARIABLES---------------------------------*/       

    //Variables
    private ConexionBBDD conectionBBDD;  
    private Sentencias sent = new Sentencias();
    
    /**
     * Constructor vacio
     */
    public ElementosDinamicos() {
        
    }

    /**
     * Constructor con parametros (bbdd, login y password)
     * @param bbdd
     * @param login
     * @param password 
     */
    public ElementosDinamicos(String bbdd, String login, String password) {
        this.conectionBBDD = new ConexionBBDD(bbdd, login, password);
    }
    
   
/*------------------------------GESTION DE COMPONENTES GRAFICOS QUE CARGAN DATOS DE LA BBDD---------------------------------*/     

    
    /**
     * Metodo para seleccionar los nombres de columnas de una tabla en funcion de una consulta e introducirlos en un vector
     * @param bbdd
     * @param login
     * @param password
     * @param tabla
     * @return String[]
     * @throws Exception 
     */
    public String[] nombreColumnas(String consulta) throws Exception {
        //Vector donde introduciremos el nombre de las columnas de una consulta
        String[] columnas = null;
        //Cargamos el ResultSet con los resultados de una consulta
        ResultSet rs = conectionBBDD.ejecutarConsulta(consulta);
        //Dimensionamos el vector
        columnas = new String[rs.getMetaData().getColumnCount()];
        //Recorremos el vector y recogemos el Nombre de la columna del ResultSet
        for (int i = 0; i < columnas.length; i++) {
            columnas[i] = rs.getMetaData().getColumnName(i + 1);
        }
        //Desconectamos de la bbdd
        conectionBBDD.desconectar();
        //Devolvemos el vector con el nombre de las columnas
        return columnas;
    }  
    
    /**
     * Metodo para consultar id de algun elemento de una tabla
     * @param consulta
     * @return id
     * @throws Exception  
     */
    public int solicitaID(String consulta) throws Exception{
        //Cargamos los nombres de las columnas en un vector
        String[] campos = this.nombreColumnas(consulta); 
        //Inicializamos una variable int para el ID
        int id = 0;
        //Cargamos un ResultSet con la consulta
        ResultSet rs = conectionBBDD.ejecutarConsulta(consulta);
        //Recorremos el resulset y con un "for" del vector "campos" extraemos el ID de la 1ª Columna
        while (rs.next()) {
            for (int i = 0; i < campos.length; i++) {
                id = rs.getInt(campos[i]);
            }
        }
        //Desconectamos de la bbdd
        conectionBBDD.desconectar();
        //Devolvemos el ID
        return id;
    }
    
    /**
     * Metodo que construye dinamicamente una JTable a partir de cualquier consulta realizada a la BBDD
     * @param consulta
     * @param tabla
     * @throws Exception 
     */
    public void pasarConsultaTabla(String consulta, JTable tabla, boolean ordenable) throws Exception {
        //Variables
        String[] campos = this.nombreColumnas(consulta); //Extreamos el nombre de las columnas que devuelve la consulta
        String[] campo = new String[campos.length];
        DefaultTableModel dtm = new DefaultTableModel(null, campos);
        

        //Extraemos las filas que devuelve la consulta y las añadimos al DefaultTableModel
        ResultSet rs = conectionBBDD.ejecutarConsulta(consulta);
        while (rs.next()) {
            for (int i = 0; i < campos.length; i++) {
                campo[i] = rs.getString(campos[i]);
            }
            dtm.addRow(campo);
        }

        //Desconectamos el link con la BBDD
        conectionBBDD.desconectar();

        //Introducimos el DefaultTableModel en la tabla
        tabla.setModel(dtm);
    
        //En el caso de que queramos una tabla ordenable utilizamos un TableRowSorter<TableModel>
        if (ordenable) {       
            TableRowSorter<TableModel> trs = new TableRowSorter<TableModel>(dtm);
            tabla.setRowSorter(trs);
        }
  
      /*  
        //Ejemplo para controlar el doble click en la tabla (importante parametrizar "final JTable tabla")
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {

                    } catch (Exception ex) {
                        Logger.getLogger(CuentaHogar.class.getName()).log(Level.SEVERE, null, ex);

                    }
                }
            }
        });
      */
        
    }
    
    /**
     * Metodo para añadir elementos de una columna de una tabla a un JComboBox
     * @param tabla
     * @param desplegable 
     */
    public void añadirDesplegable(JTable tabla, JComboBox desplegable, int columna) {
        //Limpiamos los valores del JComboBox
        desplegable.removeAllItems();

        //Introducimos en el JComboBox los valores de cada fila de la columna introducida por parametros
        for (int i = 0; i < tabla.getRowCount(); i++) {
            desplegable.addItem(tabla.getValueAt(i, columna));
        }
    }
 
    
/*------------------------------GESTION COMPONENTES GRAFICOS---------------------------------*/ 
    
    
    /**
     * Metodo para eliminar todas las filas de una tabla
     * @param tabla
     * @param dtm 
     */
    private void eliminarFilasTabla(JTable tabla, DefaultTableModel dtm) {
        //Recuperamos el DefaultTableModel
        dtm = (DefaultTableModel) tabla.getModel();

        //Eliminamos todas las filas desde al ultima a la primera
        for (int i = (dtm.getRowCount() - 1); i >= 0; i--) {
            dtm.removeRow(i);
        }
    }
      
    /**
     * Metodo para activar o desactivar cualquier elemento javax.swing.* y sus componentes
     * @param componente
     * @param estado 
     */
    public void estadoComponente(JComponent componente, boolean estado) {
        //Activamos o desactivamos el elemento
        componente.setEnabled(estado);

        //Recorremos todos los componentes del elemento y los activamos o desactivamos
        for (int i = 0; i < componente.getComponents().length; i++) {
            componente.getComponent(i).setEnabled(estado);
        }
    }
    
    /**
     * Metodo que recupera los valores de la fila de una tabla
     * @param tabla
     * @return String[]
     */
    public String[] recuperarValoresFila(JTable tabla) {
        //Vector donde introduciremos los valores
        String[] valores = null;

        //Controlamos que se ha seleccionado al menos una fila
        if (tabla.getSelectedRowCount() > 0) {
            //Recuperamos la fila seleccionada y el número de columnas de la tabla. Luego dimensionamos el vector
            int fila = tabla.getSelectedRow();
            int columnas = tabla.getColumnCount();
            valores = new String[columnas];
            //Recorremos la fila de la tabla e introducimos sus valores en el vector
            for (int i = 0; i < columnas; i++) {
                valores[i] = (String) tabla.getValueAt(fila, i);
            }
        }

        //Devolvemos los valores
        return valores;

    }
    

/*------------------------------INFORMES---------------------------------*/ 
    

    /**
     * Metodo para generar informes
     * @param archivo
     * @throws Exception 
     */
    public void generarReport(String archivo) throws Exception {
        //Conectamos a la bbdd
        Connection cn = conectionBBDD.conectar();
        //Inicializamos variables
        JasperReport reporte;
        JasperPrint imprimir;
        //Generamos el infrome a partir el archivo que nos pasen por parametro
        reporte = JasperCompileManager.compileReport(archivo);
        imprimir = JasperFillManager.fillReport(reporte, null, cn);
        //Mostramos el infrome (el false es para que no se cierre el programa al cerrar la ventana del informe)
        JasperViewer.viewReport(imprimir, false);
    }
    
    /**
     * Metodo que genera un informe a partir de una JTable
     * @param panel
     * @param tabla
     * @param archivo
     * @throws Exception 
     */
    public void imprimirTabla(JPanel panel, JTable tabla, String archivo) throws Exception {
        //Variables
        DefaultTableModel dtm = (DefaultTableModel) tabla.getModel(); 
        JRTableModelDataSource datasource = new JRTableModelDataSource(dtm);
        String reportSource = archivo;

        //Compilamos el informe
        JasperReport jr = JasperCompileManager.compileReport(reportSource);

        //Utilizamos un HashMap para los parametros
        HashMap<String, Object> params = new HashMap<String, Object>();

        //Insertamos el TitleBorder en el HashMap y luego los elementos
        TitledBorder tb = (TitledBorder) panel.getBorder();
        params.put("Title", tb.getTitle());
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            params.put("Title" + i, tabla.getColumnName(i));
        }

        //Preparamos el informe
        JasperPrint jp = JasperFillManager.fillReport(jr, params, datasource);

        //Mostramos e infrome (false para que la ventana cuando se cierre no cierre todo el programa)
        JasperViewer.viewReport(jp, false);
    }
    
    
/*------------------------------ERRORES---------------------------------*/    

    
    /**
     * Metodo para mostrar por pantalla una ventana de error con el texto de la Exception
     * @param mensaje
     * @param ex 
     */
    public void mostrarError(String mensaje, Exception ex) {
        //Variables
        JPanel panel = new JPanel();
        JLabel label = new javax.swing.JLabel();
        JScrollPane scrollPanel = new javax.swing.JScrollPane();
        JTextArea textArea = new javax.swing.JTextArea();

        //Establecemos el tipo de letra, estilo y tamaño del JLabel e introducimos el mensaje
        Font newLabelFont = new Font("Serif", Font.BOLD, 18);  
        label.setFont(newLabelFont);  
        label.setText(mensaje);

    /*
        //Posibilidades de edicion del JTextArea
        textArea.setColumns(40);
        textArea.setRows(10);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.BLUE);
        textArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    */
        //Deshabilitamos el JTextArea e introducimos la Exception
        textArea.setEditable(false);
        textArea.setText(ex.getMessage());
        
        //Introducimos el JTextArea en un scrollPanel
        scrollPanel.setViewportView(textArea);

        //Declaramos un diseño vertical para que aparezca el label encima del textArea
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Añadimos JLabel al JPanel con los espacios que consideramos
        panel.add(Box.createVerticalStrut(15));
        panel.add(label);
        
        //Añadimos el JScrollPanel con el JTextArea y los espacios que consideremos, siempre y cuando el JTextArea no este vacio
        if (!textArea.getText().isEmpty()) {
            panel.add(Box.createVerticalStrut(25));
            panel.add(scrollPanel);
        }

        //Mostramos un JOptionPane con el panel que hemos creado de error
        JOptionPane.showMessageDialog(null, panel, "ERROR", JOptionPane.ERROR_MESSAGE);
    }    

}
