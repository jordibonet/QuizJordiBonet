import players.Jugador;

import java.io.*;
import java.text.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        String [] preguntas= new String[30];
        String [] preguntasEntretenimiento;
        String [] preguntasDeportes;
        String [] preguntasGeografia;
        String [] preguntasHistoria;
        int [] respuestas=new int[30];
        int [] respuestasEntretenimiento;
        int [] respuestasDeportes;
        int [] respuestasGeografia;
        int [] respuestasHistoria;
        int categoria;
        int numero;
        int [] ordenRandom;
        int errores = 0;
        boolean salir;
        String nombre;
        ArrayList<Jugador> players = new ArrayList<>();

        preguntasEntretenimiento = preguntasEntretenimiento();
        preguntasDeportes = preguntasDeportes();
        preguntasGeografia = preguntasGeografia();
        preguntasHistoria = preguntasHistoria();
        respuestasEntretenimiento = respuestasEntretenimiento();
        respuestasDeportes = respuestasDeportes();
        respuestasGeografia = respuestasGeografia();
        respuestasHistoria = respuestasHistoria();

        anadirJugadores(players);
        nombre=username(players);
        salir = false;
        do {
            categoria=categoria();preguntas=preguntas(categoria, preguntas, preguntasEntretenimiento, preguntasDeportes, preguntasGeografia, preguntasHistoria);
            respuestas=respuestas(categoria, respuestas, respuestasEntretenimiento, respuestasDeportes, respuestasGeografia, respuestasHistoria);

            numero = numeroPreguntas();
            ordenRandom = ordenRandom(numero);

            errores=respuesta(errores, ordenRandom, preguntas, respuestas);
            porcentajeDeRespuestas(errores,numero);

            crearjugador(nombre,players,errores,numero);

            salir=salir(salir);

        }while (!salir);



    }

    private static void crearjugador(String nombre, ArrayList<Jugador> players, int errores, int numero) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Date date = new Date();
        int aciertos;
        aciertos = numero - errores;
        boolean newJugador;


        newJugador = true;
        for (Jugador player : players) {
            if (player.getUserName().equals(nombre)) {
                player.setDate_time(dateFormat.format(date));
                player.setRespuestasCorrectas(aciertos);
                player.setRespuestasIncorrectas(errores);
                newJugador = false;
            }
        }
        if (newJugador){
            players.add(new Jugador(nombre,dateFormat.format(date),aciertos,errores));
        }

        String newFile = "src/resources/players.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))){
            for (int i = 0; i < players.size(); i++) {
                bw.write(players.get(i).file());
                if (i != players.size()){
                    bw.write(System.lineSeparator());
                }
            }

        } catch (IOException e){
            System.out.println("ERROR EN LA ESCRITURA DEL FICHERO");
        }


    }

    private static String username(ArrayList<Jugador> players) {
        String nombre;
        boolean salir, correcto, enLista;
        int opcion;

        salir=false;
        do {
            System.out.print("\nNombre: ");
            nombre=Teclat.llegirString();

            enLista=false;
            for (Jugador jug : players) {
                if (Objects.equals(jug.getUserName(), nombre)){
                    enLista=true;
                    correcto=false;
                    System.out.println("El nombre de usuario ya existe.");
                    do {
                        System.out.println("Quieres jugar como " + nombre +"?");
                        System.out.println("1. SI");
                        System.out.println("2. NO");
                        System.out.print("\nOPCIÓN:");
                        opcion=Teclat.llegirInt();
                        if (opcion==1){
                            System.out.println("Jugaras como " +nombre);
                            correcto=true;
                            salir=true;
                        } else if (opcion==2){
                            correcto=true;
                        }
                    }while (!correcto);
                }
            }
            if (!enLista){
                salir=true;
            }

        }while (!salir);
        return nombre;
    }

    private static void anadirJugadores(ArrayList<Jugador> players) {

        String fileName = "src/resources/players.txt";
        BufferedReader br;
        String line;
        String[] datos;

        try{
            br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null){
                datos = line.split(";");
                players.add(new Jugador(datos[0],datos[1],Integer.parseInt(datos[2]),Integer.parseInt(datos[3])));
            }
            for (Jugador jug : players){
                System.out.println(jug);
            }
        }catch (FileNotFoundException e) {
            System.out.println("ERROR AL BUSCAR EL FICHERO");
        } catch (IOException e) {
            System.out.println("ERROR AL LEER EL FICHERO");
        }

    }

    private static boolean salir(boolean salir) {
        int option;
        boolean correcto;

        correcto=false;
        do {
            System.out.println("¿Quieres volver a jugar?");
            System.out.println("1. SI");
            System.out.println("2. NO");
            option = Teclat.llegirInt();
            if (option == 1){
                salir=false;
                correcto=true;
            } else if (option==2) {
                System.out.println("Hasta la proxima");
                salir=true;
                correcto=true;
            } else System.out.println("¡¡¡ELIGE UNA OPCION DEL 1 al 2!!!");
        }while (!correcto);
        return salir;
    }
    private static void porcentajeDeRespuestas(int errores,int numero) {
        int aciertos;
        aciertos = numero - errores;

        System.out.println("HAS ACERTADO UN " + ((aciertos*100)/numero) +"%");
    }

    private static int respuesta(int errores, int[] ordenRandom, String[] preguntas, int[] respuestas) {
        int numero=1;
        int respuesta;
        boolean salir;

        for (int aux:ordenRandom) {
            System.out.println("\n"+ numero +" " +preguntas[aux]);
            numero++;
            salir=false;
            do {
                System.out.print("\nRespuesta:");
                respuesta=Teclat.llegirInt();
                if (respuesta>4||respuesta<1){
                    System.out.println("ELIGE UNA OPCIÓN VALIDA");
                }else salir=true;
                if (aux==25||aux==26||aux==27||aux==28||aux==29){
                    if (respuesta>2||respuesta<1){
                        salir=false;
                        System.out.println("ELIGE UNA OPCIÓN VALIDA");
                    }
                }
            }while (!salir);
            if (respuesta==respuestas[aux]){
                System.out.println("\nRESPUESTA CORRECTA");
            }else{
                System.out.println("\nLA RESPUESTA CORRECTA ERA LA = " +respuestas[aux]);
                errores++;
            }
        }
        return errores;
    }

    private static int [] ordenRandom(int numero) {
        int [] numeroPreguntas = new int[numero];
        boolean salir;

        for (int i = 0; i < numeroPreguntas.length; i++) {
            salir = true;
            int numeroRandom = (int) (Math.random()*30);
            for (int j = 0; j < i; j++) {
                if (numeroPreguntas[j]==numeroRandom){
                    salir=false;
                    i--;
                    break;
                }
            }
            if (salir){
                numeroPreguntas[i]=numeroRandom;
            }
        }
        return numeroPreguntas;
    }

    private static int numeroPreguntas() {
        int numero;
        boolean salir;
        salir = false;
        do {
            System.out.print("\nCuantas preguntas quieres hacer en el quiz (5 - 30): ");
            numero = Teclat.llegirInt();
            if (numero<5){
                System.out.println("El número de preguntas tiene que ser mínimo de 5.");
            } else if (numero>30) {
                System.out.println("El número máximo de preguntas es 30.");
            }else salir=true;
        }while (!salir);
        return numero;
    }

    private static int [] respuestas (int categoria, int[] respuestas, int[] respuestasEntretenimiento, int[] respuestasDeportes, int[] respuestasGeografia, int[] respuestasHistoria){
        switch (categoria){
            case 1:
                respuestas=respuestasEntretenimiento;
                break;
            case 2:
                respuestas=respuestasDeportes;
                break;
            case 3:
                respuestas=respuestasGeografia;
                break;
            case 4:
                respuestas=respuestasHistoria;
                break;
        }
        return respuestas;
    }
    private static String [] preguntas (int elegirPreguntas, String[] preguntas, String[] preguntasEntretenimiento, String[] preguntasDeportes, String[] preguntasGeografia, String[] preguntasHistoria){
        switch (elegirPreguntas){
            case 1:
                preguntas=preguntasEntretenimiento;
                break;
            case 2:
                preguntas=preguntasDeportes;
                break;
            case 3:
                preguntas=preguntasGeografia;
                break;
            case 4:
                preguntas=preguntasHistoria;
                break;
        }
        return preguntas;
    }

    private static int categoria(){
        boolean salir;
        int num;

        salir=false;
        do {
            System.out.println("""
                    De que tipo de preguntas quieres hacer el quiz:
                    1. Entretenimiento
                    2. Deportes
                    3. Geografia
                    4. Historia""");
            System.out.print("\nTipo: ");
            num=Teclat.llegirInt();
            switch (num) {
                case 1:
                    System.out.println("Tu Quiz será sobre Entretenimiento");
                    salir = true;
                    break;
                case 2:
                    System.out.println("Tu Quiz será sobre Deportes");
                    salir = true;
                    break;
                case 3:
                    System.out.println("Tu Quiz será sobre Geografia");
                    salir = true;
                    break;
                case 4:
                    System.out.println("Tu Quiz será sobre Historia");
                    salir = true;
                    break;
                default:
                    System.out.println("ELIGE UNA OPCIÓN VÁLIDA");
                    break;
            }
        }while (!salir);
        return num;
    }

    private static int [] respuestasHistoria(){
        String fileName = "src/resources/Respuestas_Historia.txt";
        int [] respuestas;
        respuestas=leerRespuestas(fileName);
        return respuestas;
    }

    private static int [] respuestasGeografia(){
        String fileName = "src/resources/Respuestas_Geografia.txt";
        int [] respuestas;
        respuestas=leerRespuestas(fileName);
        return respuestas;
    }
    private static int [] respuestasDeportes(){
        String fileName = "src/resources/Respuestas_Deportes.txt";
        int [] respuestas;
        respuestas=leerRespuestas(fileName);
        return respuestas;
    }

    private static int [] respuestasEntretenimiento() {
        String fileName = "src/resources/Respuestas_Entretenimiento.txt";
        int [] respuestas;
        respuestas=leerRespuestas(fileName);
        return respuestas;
    }

    private static String[] preguntasHistoria() {
        String fileName = "src/resources/Preguntas_Historia.txt";
        String [] preguntas;
        preguntas=leerPreguntas(fileName);
        return preguntas;
    }


    private static String[] preguntasGeografia() {
        String fileName = "src/resources/Preguntas_Geografia.txt";
        String [] preguntas;
        preguntas=leerPreguntas(fileName);
        return preguntas;
    }

    private static String[] preguntasDeportes() {
        String fileName = "src/resources/Preguntas_Deportes.txt";
        String [] preguntas;
        preguntas=leerPreguntas(fileName);
        return preguntas;
    }
    private static String [] preguntasEntretenimiento (){
        String fileName = "src/resources/Preguntas_Entretenimiento.txt";
        String [] preguntas;
        preguntas=leerPreguntas(fileName);
        return preguntas;
    }
    private static int[] leerRespuestas(String fileName) {
        int [] respuestas = new int[30];
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader(fileName));

            int i = 0;
            while ((line = br.readLine()) != null){
                respuestas[i] = Integer.parseInt(line);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL BUSCAR EL FICHERO");
        } catch (IOException e) {
            System.out.println("ERROR AL LEER EL FICHERO");
        }
        return respuestas;
    }
    private static String[] leerPreguntas(String fileName) {
        String [] preguntas = new String[30];

        BufferedReader br;
        String line;
        String[] datos;


        try {
            br = new BufferedReader(new FileReader(fileName));

            int i = 0;
            while ((line = br.readLine()) != null){
                datos = line.split(";");
                preguntas[i]=datos[0];
                for (int j = 1; j < datos.length; j++) {
                    preguntas[i]=preguntas[i]+"\n"+datos[j];
                }
                i++;
            }
        }catch (FileNotFoundException e) {
            System.out.println("ERROR AL BUSCAR EL FICHERO");
        } catch (IOException e) {
            System.out.println("ERROR AL LEER EL FICHERO");
        }

        return preguntas;
    }
}