package CuentaHogar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase ConexionBBDD (Clase para atacar a una bbdd)
 * @author Fernando J. Gonzalez Lopez
 * @version 0.1
 */
public class ConexionBBDD {
    
    //Variables  
    private Connection cn = null;
    private Statement st = null;
    private ResultSet rs = null;
    private String bbdd;
    private String login;
    private String password;

    /**
     * Constructor vacio
     */
    public ConexionBBDD() {
        
    }
    
    /**
     * Constructor con parametros (bbdd, login y password)
     * @param bbdd
     * @param login
     * @param password 
     */
    public ConexionBBDD(String bbdd, String login, String password) {
        this.bbdd = bbdd;
        this.login = login;
        this.password = password;
    }
          
    /**
     * Metodo para conectar a la base de datos
     * @param bbdd
     * @param login
     * @param password
     * @return Connection
     * @throws Exception 
     */
    public Connection conectar() throws Exception {
        
        //Declaramos una nueva conexion, utilizamos el driver correspondiente y le pasamos los datos de la bbdd
        Connection link = null;
        Class.forName("org.gjt.mm.mysql.Driver");
        link = DriverManager.getConnection("jdbc:mysql://localhost/" + bbdd, login, password);
        
        //Devolvemos la conexion
        return link;
    }
    
    /**
     * Metodo para ejecutar Consultas
     * @param bbdd
     * @param login
     * @param password
     * @param sentencia
     * @return ResultSet
     * @throws Exception 
     */
    public ResultSet ejecutarConsulta (String consulta) throws Exception {    
        //Conectamos a la bbdd
        cn = this.conectar();
        //Para enviar solicitudes a la bbdd creamos un Statement 
        st = cn.createStatement();
        //Enviamos la consulta a la bbdd y la introducimos en un ResultSet
        rs = st.executeQuery(consulta);
        //Devolvemos el ResultSet con los datos de la consulta
        return rs;
        //Recordar que en el metodo donde se recoja la consulta hay que "desconectar();" de la bbdd
    }
    
    /**
     * Metodo para ejecutar Sentencias
     * @param bbdd
     * @param login
     * @param password
     * @param sentencia
     * @throws Exception 
     */
    public void ejecutarSentencia(String sentencia) throws Exception {    
        //Conectamos a la bbdd
        cn = this.conectar();
        //Para enviar solicitudes a la bbdd creamos un Statement
        st = cn.createStatement();
        //Enviamos la sentendcia a la bbdd y la introducimos en un ResultSet
        st.executeUpdate(sentencia);
        //Desconectamos de la bbdd
        st.close();
        cn.close();
    }
    
    /**
     * Metodo para desconectar de la bbdd
     * @throws Exception 
     */
    public void desconectar() throws Exception {
        //Cerramos el ResultSet 
        rs.close();
        //Cerramos la Connection
        cn.close();
    } 
      
}
