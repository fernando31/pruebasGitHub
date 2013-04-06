package com.cuentahogar;

import java.io.IOException;
import java.sql.*;

public class BDConectar {

	private Connection cn = null;
    private Statement st = null;
    private ResultSet rs = null;
    private String bbdd;
    private String login;
    private String password;

    /**
     * Constructor vacio
     */
    public BDConectar() {
        
    }
    
    /**
     * Constructor con parametros (bbdd, login y password)
     * @param bbdd
     * @param login
     * @param password 
     */
    public BDConectar(String bbdd, String login, String password) {
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
     */
    public Connection conectar() {
        
        Connection link = null;

        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            link = DriverManager.getConnection("jdbc:mysql://10.0.2.2/" + bbdd, login, password);
        }catch (Exception e){}
        
        return link;

    }
    
    /**
     * Metodo para ejecutarConsultas
     * @param bbdd
     * @param login
     * @param password
     * @param sentencia
     * @return ResultSet
     * @throws IOException 
     */
    public ResultSet ejecutarConsulta (String sentencia) throws IOException {    
        try {
            cn = conectar();
            
            st = cn.createStatement();
	
            rs = st.executeQuery(sentencia);
		
        }catch (SQLException e){}
        
        return rs;
    }
    
    /**
     * Metodo para ejecutarSentencias
     * @param bbdd
     * @param login
     * @param password
     * @param sentencia
     * @throws IOException 
     */
    public void ejecutarSentencia(String sentencia) throws IOException {    
        try {
            cn = conectar();
		
            st = cn.createStatement();
            
            st.executeUpdate(sentencia);

        }catch (SQLException e){

        }finally {
            desconectar();
        }    
    }
    
    /**
     * Metodo para desconectar de la bbdd
     */
	public void desconectar() {
		try {
			rs.close();

		} catch (SQLException e) {

		} catch (Exception e) {}

		try {
			st.close();

		} catch (SQLException e) {

		} catch (Exception e) {}

		try {
			cn.close();

		} catch (SQLException e) {

		} catch (Exception e) {}
	} 
      
    /**
     * Metodo para seleccionar los campos de una tabla e introducirlos en un vector
     * @param bbdd
     * @param login
     * @param password
     * @param tabla
     * @return String[]
     * @throws IOException 
     */
    public String[] nombreColumnas(String sentencia) throws IOException {

        String [] columnas =  null;
        
		try {
	            rs = ejecutarConsulta(sentencia);
	         
	            columnas = new String [rs.getMetaData().getColumnCount()];
	
	            for(int i = 0; i < columnas.length; i++){
	                columnas[i] = rs.getMetaData().getColumnName(i+1);
	            }      
	
		}catch (SQLException e){
			
	    }catch (NullPointerException e){
	    	
		} finally {
	            desconectar();
		}
        
        return columnas;
    }  
    
    /**
     * Metodo para consultar id de algun elemento de una tabla
     * @param consulta
     * @return id
     * @throws IOException 
     */
    public int solicitaID(String consulta) throws IOException{
        
        String[] campos = this.nombreColumnas(consulta); 
        int id = 0;

        try{
            ResultSet rs = this.ejecutarConsulta(consulta);
            while (rs.next()){
                for(int i = 0; i < campos.length; i++){
                    id = rs.getInt(campos[i]);
                }
            }

            this.desconectar();
            
        }catch(SQLException ex){
        }catch(NullPointerException ex){}
        
        return id;
    }
}
