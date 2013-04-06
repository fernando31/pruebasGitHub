package CuentaHogar;

/**
 * Clase Cuentas (Para crear objetos con el formato de la tabla CUENTAS de la bbdd)
 * @author Fernando J. Gonzalez Lopez
 * @version 0.1
 */
public class Cuentas {

    //Variables
    private int id_cuenta;
    private String nombre;
    private char tipo;
    private String numero;
    private float capital;

    /**
     * Constructor vacio
     */
    public Cuentas() {
        
    }

    /**
     * Constructor con parametros
     * @param id_cuenta
     * @param nombre
     * @param tipo
     * @param numero
     * @param capital 
     */
    public Cuentas(int id_cuenta, String nombre, char tipo, String numero, float capital) {
        this.id_cuenta = id_cuenta;
        this.nombre = nombre;
        this.tipo = tipo;
        this.numero = numero;
        this.capital = capital;
    }

    /**
     * Metodo para recuperar el ID de la cuenta
     * @return int
     */
    public int getId_cuenta() {
        return id_cuenta;
    }

    /**
     * Metodo para establecer el ID de la cuenta
     * @param id_cuenta 
     */
    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    /**
     * Metodo para recuperar el Nombre de la cuenta
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo para establecer el Nombre de la cuenta
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo para recuperar el Tipo de cuenta
     * @return char
     */
    public char getTipo() {
        return tipo;
    }

    /**
     * Metodo para establecer el Nombre de cuenta
     * @param tipo 
     */
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo para recuperar el Numero de cuenta
     * @return String
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Metodo para establecer el Numero de cuenta
     * @param numero 
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Metodo para recuperar el Capital de la cuenta
     * @return float
     */
    public float getCapital() {
        return capital;
    }

    /**
     * Metodo para establecer el Capital de la cuenta
     * @param capital 
     */
    public void setCapital(float capital) {
        this.capital = capital;
    }

    /**
     * Metodo para mostrar por terminal los datos del objeto Cuenta
     * @return String
     */
    @Override
    public String toString() {
        return  "***Cuenta***" + 
                "\nid_cuenta: " + id_cuenta + 
                "\nnombre: " + nombre + 
                "\ntipo: " + tipo + 
                "\nnumero: " + numero + 
                "\ncapital: " + capital + 
                "\n---------------------------\n";
    }
    
}
