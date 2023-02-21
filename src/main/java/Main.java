
import com.mongodb.client.*;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;
import java.util.Scanner;
public class Main {
    public static MongoDatabase database =  Conexion.conectar();
    public static void main(String[] args) {
        menu();
    }
    /**
     * Método que muestra las diferentes opciones del crud.
     */
    private static void menu(){
        int opc;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("1. Insertar alumno");
            System.out.println("2. Mostrar datos de un alumno");
            System.out.println("3. Mostrar datos de todos los alumnos");
            System.out.println("4. Actualizar alumno");
            System.out.println("5. Eliminar alumno");
            System.out.println("6. Salir");
            opc = sc.nextInt();
            switch (opc){
                case 1:
                    insertarAlumno();
                    break;
                case 2:
                    String nombre;
                    System.out.println("Introduzca el nombre del alumno que desea mostrar");
                    nombre = sc.nextLine();
                    mostrarAlumnoPorNombre(nombre);
                    break;
                case 3:
                    mostrarAlumnos();
                    break;
                case 4:
                    String campo, valor;
                    System.out.println("Introduzca el nombre del alumno que desea actualizar");
                    nombre = sc.nextLine();
                    System.out.println("Introduzca el campo que desea cambiar");
                    campo = sc.nextLine();
                    System.out.println("Introduzca el nuevo valor del alumno");
                    valor = sc.nextLine();
                    actualizarAlumno(nombre, campo, valor);
                    break;
                case 5:
                    String id;
                    System.out.println("Introduzca el id del alumno que desea borrar");
                    id = sc.nextLine();
                    borrarAlumnoPorID(id);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }while (opc!=6);
    }
    /**
     * Método que inserta un nuevo alumno en la tabla Alumnos.
     */
    public static void insertarAlumno(){
        MongoCollection<Document> collection = database.getCollection("Alumnos");
        database .getCollection("Alumnos").insertOne(
                new Document()
                        .append("nombre","ale")
                        .append("apellidos","sosa")
                        .append("Fecha_nacimiento","2001-08-26")
                        .append("Edad",21));
    }
    /**
     * Método que se encarga de mostrar por pantalla los datos de un alumno con el nombre que se le
     * pasa por parámetro.
     * @param nombre nombre del alumno que se quiere mostrar
     */
    public static void mostrarAlumnoPorNombre(String nombre){
        MongoCollection<Document> collection = database.getCollection("Alumnos");
        Document doc = collection.find(eq("nombre", nombre)).first();
        if (doc != null) {
            System.out.println(doc.toJson());
        } else {
            System.out.println("No existe ningún alumno con ese nombre");
        }
    }
    /**
     * Método que muestra por pantalla los datos de todas las personas de la tabla Alumnos.
     */
    public static void mostrarAlumnos(){
        MongoCollection<Document> collection = database.getCollection("Alumnos");
        FindIterable<Document> document =  collection.find();
        for(Document doc : document) {
            System.out.println(doc.toJson());
        }
    }
    /**
     * Método que se encarga de actualizar el campo que se le pasa por parámetro al alumno con nombre
     * pasado por parámetro con nuevo valor también pasado por parámetro.
     * @param nombre nombre del alumno que se quiere modificar
     * @param campo campo de la tabla a modificar
     * @param valor nuevo valor del campo
     */
    public static void actualizarAlumno(String nombre, String campo, String valor){
        MongoCollection<Document> collection = database.getCollection("Alumnos");
        Document doc = collection.find(eq("nombre", nombre)).first();
        Bson actualizar = Updates.combine(
                Updates.set(campo, valor));
        UpdateOptions options = new UpdateOptions().upsert(true);
        database.getCollection("Alumnos").updateMany(doc, actualizar, options);
    }
    /**
     * Método que borra un alumno con el id que se pasa por parámetro.
     * @param id id del alumno que se quiere eliminar
     */
    public static void borrarAlumnoPorID(String id){
        MongoCollection<Document> collection = database.getCollection("Alumnos");
        collection.deleteOne(eq("_id",new ObjectId(id)));
    }
}