

import com.mongodb.*;
import com.mongodb.client.*;
public class Conexion {
    /**
     * Método que devuelve un objeto MongoDatabase. Con este método se obtiene un base de datos.
     * @return
     */
    public static MongoDatabase conectar(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://asosa:1234@mongo.CRUD.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("mongo");
        return database;
    }
}
