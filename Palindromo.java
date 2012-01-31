import java.io.*;

//HAZ UN PROGRAMA QUE INDIQUE SI LA PALABRE O FRASE INTRODUCIDA POR EL USUARIO ES UN PALINDROMO
//(Palindromo: Palabra o frase que se lee igual de izquierda a derecha,que de derecha a izquierda; p. ej., anilina.

public class Palindromo{

        private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	public static void main (String args[]) throws IOException{
		//Menu de opciones
		int opcio;
		do {
		 System.out.println("**********************************");
		 System.out.println("*       MENU DE OPCIONES         *");
		 System.out.println("*--------------------------------*");
		 System.out.println("*  1 - Definicion Palindromo     *");
		 System.out.println("*  2 - Comprobar Palindromo      *");
		 System.out.println("*  3 - Salir del Programa        *");
		 System.out.println("*--------------------------------*");
		 System.out.println("*     ELIGE UNA OPCION (1-3)     *");		
		 System.out.println("**********************************");		
		 opcio = Integer.parseInt(stdin.readLine());
		 switch (opcio) {
			case 1: System.out.println("Palabra o frase que se lee igual de izquierda a derecha,");
					System.out.println("que de derecha a izquierda; p. ej., anilina;");break;
			case 2: System.out.println("Has elegido coomprobar un palindromo");
			
				//Declaracion variables
				String cadena;

				//Introducir datos
				System.out.println("Introduce Palindromo: ");
				cadena = stdin.readLine();
	
				//Eliminar espacios, apostrofes y ponerlo en minusculas
				cadena = cadena.replaceAll(" ","").toLowerCase();
				cadena = cadena.replaceAll("'","");
	
				//Girar frase
				StringBuffer cadenainv = new StringBuffer(cadena);
				cadenainv = cadenainv.reverse();
	
				//Mostramos por pantalla resutlados para comprobacion
				System.out.println(cadena + " / " + cadenainv);
	
				//Condicionamos la respuesta comparando 2 String (transformando StringBuffer en String)
				if (cadena.equals(new String(cadenainv))){
				System.out.println("Enhorabuena!! has encontrado un Palindromo ");
				} else{
				System.out.println("Lo siento, no es un Palindromo");
					}break;
					
			case 3: System.out.println("Adios");break;
			default: System.out.println("Opcion desconocida");break;	
		 }	
		}while(opcio != 3);
	}
}
