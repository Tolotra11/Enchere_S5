package cloud.util;
import java.sql.Connection;
import java.sql.DriverManager;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;



public class ConnectionMongo {
	
	    public static MongoDatabase getMongoConnection() throws Exception {
	        ConnectionString connectionString = new ConnectionString("mongodb://127.0.0.1:27017/?retryWrites=true&w=majority");
	        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
	        MongoClient mongoClient = MongoClients.create(settings);
	        MongoDatabase database = mongoClient.getDatabase("enchere");
	        for (String collectionName : database.listCollectionNames()) {
	            System.out.println(collectionName);
	        }
	        return database;
	    }

}
