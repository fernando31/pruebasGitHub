package CuentaHogar;

import java.util.Date;

/**
 * Clase Movimientos (Para crear objetos con el formato de la tabla MOVIMIENTOS de la bbdd)
 * @author Fernando J. Gonzalez Lopez
 * @version 0.1
 */
public class Movimientos {

    //Variables
    private int id_movimiento;
    private int id_cuenta_g;
    private int id_cuenta_i;
    private char tipo;
    private Date fecha;
    private float importe;

    /**
     * Constructor vacio
     */
    public Movimientos() {
        
    }

    /**
     * Constructor con parametros
     * @param id_movimiento
     * @param id_cuenta_g
     * @param id_cuenta_i
     * @param tipo
     * @param fecha
     * @param importe 
     */
    public Movimientos(int id_movimiento, int id_cuenta_g, int id_cuenta_i, char tipo, Date fecha, float importe) {
        this.id_movimiento = id_movimiento;
        this.id_cuenta_g = id_cuenta_g;
        this.id_cuenta_i = id_cuenta_i;
        this.tipo = tipo;
        this.fecha = fecha;
        this.importe = importe;
    }

    /**
     * Metodo para recuperar el ID del movimiento
     * @return int
     */
    public int getId_movimiento() {
        return id_movimiento;
    }

    /**
     * Metodo para establecer el ID del movimiento
     * @param id_movimiento 
     */
    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    /**
     * Metodo para recuperar el ID de la cuenta de donde sale el importe
     * @return int
     */
    public int getId_cuenta_g() {
        return id_cuenta_g;
    }

    /**
     * Metodo para establecer el ID de la cuenta de donde sale el importe
     * @param id_cuenta_g 
     */
    public void setId_cuenta_g(int id_cuenta_g) {
        this.id_cuenta_g = id_cuenta_g;
    }

    /**
     * Metodo para recuperar el ID de la cuenta de que recibe el importe
     * @return int
     */
    public int getId_cuenta_i() {
        return id_cuenta_i;
    }

    /**
     * Metodo para establecer el ID de la cuenta de que recibe el importe
     * @param id_cuenta_i 
     */
    public void setId_cuenta_i(int id_cuenta_i) {
        this.id_cuenta_i = id_cuenta_i;
    }

    /**
     * Metodo para recuperar el tipo de movimiento (G = Gasto / I = Ingreso/ T = Traspaso)
     * @return char
     */
    public char getTipo() {
        return tipo;
    }

    /**
     * Metodo para establecer el tipo de movimiento (G = Gasto / I = Ingreso/ T = Traspaso)
     * @param tipo 
     */
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo para recuperar la fecha del movimiento
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Metodo para establecer la fecha del movimiento
     * @param fecha 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Metodo para recuperar el importe del movimiento
     * @return float
     */
    public float getImporte() {
        return importe;
    }

    /**
     * Metodo para establecer el importe del movimiento
     * @param importe 
     */
    public void setImporte(float importe) {
        this.importe = importe;
    }

    /**
     * Metodo para mostrar por terminal los datos del objeto Movimiento
     * @return String
     */
    @Override
    public String toString() {
        return  "***Movimiento***" + 
                "\nid_movimiento: " + id_movimiento + 
                "\nid_cuenta_g: " + id_cuenta_g + 
                "\nid_cuenta_i: " + id_cuenta_i + 
                "\ntipo: " + tipo + 
                "\nfecha: " + fecha + 
                "\nimporte: " + importe +
                "\n---------------------------\n";
    }
    
}
