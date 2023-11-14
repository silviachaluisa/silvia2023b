import java.util.Random; //Libreria para trabajar con numeros aleatorios
import java.util.Scanner; //Libreria para leer los datos ingresados por teclado
public class Ej_ahorcado {
    private Scanner ingreso; //Crear un objeto de la clase Scanner
    static String[] bp = {" O ", "/", "|", "\\", "/", "\\"}; //Crear lista para dibujar el cuerpo del ahorcado
    static String cabeza, cuerpo, piernas; //Variables que almacenaran cada parte del cuerpo
    // Función para verificar si una cadena contiene solo letras
    private static boolean esLetra(String cadena)
    {
        return cadena.matches("[a-zA-Z]+"); // Utiliza una expresión regular para verificar si son solo letras
    }

    public String Verificar (String texto) //Funcion con parametro para verificar que la cadena sea estrictamente una letra
    {
        Scanner vld=new Scanner(System.in); // Crea un objeto de la clase Scanner para recibir entrada del usuario.
        if (texto.length()!=1) //Verifica si se ingreso solo una letra
        {
            System.out.println("Solo puedes ingrear una letra!");
            System.out.println("Ingresalo nuevamente: ");
            texto=vld.nextLine();
            vld.close(); 
            Verificar(texto);
        }
        if (!esLetra(texto)) //Uso de una funcion para verificar si la cadena es solo letras
        {
            System.out.println("Solo puede ingresar letras!");
            System.out.println("Ingrese de nuevo: ");
            texto=vld.nextLine();
            vld.close();//C
            Verificar(texto);
        }
        return texto;
    }

    public void menu() //Menu inicial del juego
    {
        System.out.println("\t Bienvenido al juego El Ahorcado");
        System.out.println("Que desea hacer");
        System.out.println("J.- Jugar");
        System.out.println("S.- Salir");
        System.out.println("Ingrese una opcion: ");
    }
    public static void main(String[] args) // Programa principal
    {
        Ej_ahorcado elemento=new Ej_ahorcado(); //Crear un nuevo objeto de la clase Ej_ahorcado y asignarlo a la variable elemento
        String opc="";
        elemento.ingreso=new Scanner(System.in); //Crear una instancia del escaner a partir del objeto creado
        do {
            elemento.menu();//Llamar a la funcion menu usando el objeto elemento
            opc=Ingresar(opc); //Llamar a la funcion Ingresar
            if (opc.equals("j")) { // verificar que opc sea igual a 'j'
                elemento.juego();//Llamar a la funcion juego usando el objeto elemento
                opc="";
            }
        } while (!opc.equals("s"));// verificar que opc sea distinto a 's'
        System.out.println("Gracias por jugar!"); //Imprimir un mensaje
        elemento.ingreso.close(); //cerrar el escaner para liberar recursos
    }
    public void juego() //Funcion principal del juego
    {
        String adi, err;
        int intentos=0;
        adi="";
        err="";
        boolean adivinado=false;
        
        Random rand = new Random(); // Crea un objeto de la clase Random para generar números aleatorios.
        String [] cadenas={"juego","mesa","compania","asignado","total","reconocer","motor","vehiculo","acera","laderas"};
        // Selecciona aleatoriamente una palabra del arreglo 'cadenas' usando el objeto 'rand'.
        // Esto se hace generando un índice aleatorio dentro del rango de la longitud del arreglo.
        String Palabra_seleccionada = cadenas[rand.nextInt(cadenas.length)]; //Obtener una palabra al azar
        String Palabra="";
        int longitud=0;
        for (int i=0; i<Palabra_seleccionada.length(); i++) //generar una nueva cadena a partir de la palabra generada
        {
            Palabra+= "_";
            longitud++;
        }
        while (intentos<6 && !adivinado) //Comprobar que no se haya perdido o no se haya adivinado la palabra
        {
            System.out.println("Adivina la palabra: "+Palabra+" Longitud: "+longitud+" letras"); //mostrar detalles de la palabra
            System.out.println("Ingresa la palabra: ");
            String letra=ingreso.nextLine(); //Ingresar valores por teclado
            while (letra.length()!=1) // En caso de que no se ingrese nada
            {
                System.out.println("Error! Solo ingrese una sola letra.");
                System.out.println("Intentelo nuevamente: ");
                letra=ingreso.nextLine();//Ingresar valores por teclado
            }
            
            while (adi.indexOf(letra)!=-1 || err.indexOf(letra)!=-1) //En caso de que la letra ya haya sido usada
            {
                System.out.println("La letra "+ letra+" ya ha sido usada antes!");
                System.out.println("Intente con otra: ");
                letra=ingreso.nextLine();//Ingresar valores por teclado
                while (letra.length()!=1) //Verifica que se ingrese solo una letra
                {
                    System.out.println("Error! Solo ingrese una sola letra.");
                    System.out.println("Intentelo nuevamente: ");
                    letra=ingreso.nextLine(); //Ingresar valores por teclado
                }
            }
            int ind=Palabra_seleccionada.indexOf(letra); //Obtener el indice de la letra ingresada en la cadena de palabra seleccionada
            if (ind!=-1) //Si no falla la busqueda
            {
                while (ind!=-1) //mientras no falle la busqueda
                {
                    Palabra=Palabra.substring(0, ind)+letra+Palabra.substring(ind + 1); //remplazar la letra en la cadena que se muestra
                    adi+=letra; //Agregar la letra acertada
                    ind=Palabra_seleccionada.indexOf(letra, ind + 1); //Buscar si la letra ingresada se repite otra vez
                    //Ejemplo:
                    //Si palabra es "casa" y letra es "a", entonces ind es 2 y 4
                    //texto=____ ahora es texto=_a_a
                }
                if (Palabra.equals(Palabra_seleccionada)) //Si la palabra es igual a la palabra generada
                {
                    adivinado=true; //Se adivino la palabra
                }
            }
            else //Si fallo la busqueda
            {
                intentos++; //Aumenta los intentos fallidos
                err+=letra; //Agrega la letra fallida
                System.out.println("Fallaste!");
                System.out.println("Letras que no eran: " + err);
                System.out.println("Llevas " + intentos + " fallas");
                System.out.println("Intentalo de nuevo!");
                Cuerpo(intentos);//Funcion para dibujar el cuerpo del ahorcado
            }
        }

        if (!adivinado || intentos==6) //Si pierdes
        {
            System.out.println("Perdiste el juego! La palbra era '"+ Palabra_seleccionada+"'");
            intentos=0; //restablece los intentos fallidos
        }
        else if (adivinado) //Si ganas
        {
            System.out.println("Ganaste! Has adivinado la palabra '"+ Palabra_seleccionada+"'");
            intentos=0; //restablece los intentos fallidos
        }
    }

    static String Ingresar(String texto) { //Funcion (estatica) con parametros para las opciones del menu
        Scanner leer = new Scanner(System.in);
        texto = texto.toLowerCase(); //Convertir el texto ingresado a minusculas, ejemplo si ingresas "HoLa" pasa a "hola"
        boolean numero = false;
        if (texto.length() != 1) { //Verificar que solo se ingrese una letra
            System.out.println("Debe ingresar al menos una letra!");
            System.out.println("Ingrese una opcion: ");
            texto = leer.nextLine();
            return Ingresar(texto);  // Retornar el resultado de la llamada recursiva
        }
        for (int i = 0; i < texto.length(); i++) { //Recorre la cadena en busca de algun numero
            char c = texto.toLowerCase().charAt(i);
            if ((c < 'a') || (c > 'z')) {
                numero = true;
                break;
            }
        }
        if (numero) { //Si la cadena es un numero
            System.out.println("Solo debes ingresar letras.");
            System.out.println("Ingrese una opcion: ");
            texto = leer.nextLine();
            return Ingresar(texto);  // Retornar el resultado de la llamada recursiva
        }
    
        if (!texto.equals("j") && !texto.equals("s")) { //Si la cadena no es 'j' o 's'
            System.out.println("Solo puede ingresar la 'S' para salir o 'J' para jugar.");
            System.out.println("Ingrese una opcion: ");
            texto = leer.nextLine();
            return Ingresar(texto);  // Retornar el resultado de la llamada recursiva
        }
        return texto;
    }
    
    public static void Cuerpo(int intentos) //Funcion para almacenary dibujar el cuerpo
    {
        //Inicializar las variables
        cabeza="";
        cuerpo="";
        piernas="";
        for (int i=0; i<intentos; i++)
        {
            if (i==0)
            {
                cabeza=bp[i]; //Almacena la cabeza
            }
            else if (i>0 && i<4)
            {
                cuerpo+=bp[i]; //Almacena el cuerpo
            }
            else
            {
                //Almacena las piernas
                piernas+=bp[i];
                piernas+=" ";
            }
        }
        System.out.println(cabeza);
        System.out.println(cuerpo);
        System.out.println(piernas);
    }
}