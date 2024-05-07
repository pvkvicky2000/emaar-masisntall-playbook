import com.mongodb.client.*;
import org.bson.Document;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoDataExporter {

    private static final String CONNECTION_URI = "mongodb://admin:bJGK0GWIN5AFK6mm@mas-mongo-ce-0.apps.sme-azae-ocd.ocd.serco.ae:443/admin?ssl=true&retryWrites=true&loadBalanced=false&readPreference=primary&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-256";

    public static void main(String[] args) {
        MongoDataExporter exporter = new MongoDataExporter();
        exporter.exportAllDatabases();
    }

    public void exportAllDatabases() {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_URI)) {
            for (String dbName : mongoClient.listDatabaseNames()) {
                if(!Arrays.asList(new String[]{"admin","config","local"}).contains(dbName) ) {
                    MongoDatabase database = mongoClient.getDatabase(dbName);
                    Path dbPath = Paths.get("C:\\Users\\ADMIN\\Documents\\ADIA-dev\\" + dbName);
                    if (!Files.exists(dbPath)) {
                        Files.createDirectories(dbPath);
                    }
                    for (String collName : database.listCollectionNames()) {
                        MongoCollection<Document> collection = database.getCollection(collName);
                        exportCollection(collection, dbPath.resolve(collName + ".json"));
                        exportIndexes(collection, dbPath.resolve(collName + "_indexes.json"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportCollection(MongoCollection<Document> collection, Path filePath) {
        try {
            List<Document> documents = collection.find().into(new ArrayList<>());
            String jsonContent = documents.stream()
                    .map(Document::toJson)
                    .reduce("[", (partial, element) -> partial + element + ",");
            jsonContent = jsonContent.substring(0, jsonContent.length() - 1) + "]";
            Files.write(filePath, jsonContent.getBytes());
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    private void exportIndexes(MongoCollection<Document> collection, Path filePath) {
        try {
            List<Document> indexes = collection.listIndexes(Document.class).into(new ArrayList<>());
            String jsonContent = indexes.stream()
                    .map(Document::toJson)
                    .reduce("[", (partial, element) -> partial + element + ",");
            jsonContent = jsonContent.substring(0, jsonContent.length() - 1) + "]";
            Files.write(filePath, jsonContent.getBytes());
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }
}
