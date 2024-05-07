import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MongoJsonUploader {

    //private static final String DATABASE_NAME = "ibm-sls_sls_licensing";
    private static final String CONNECTION_URI = "mongodb://vijaymongodbtest:ITmbCIoU8YsWfA3nwpJhbv9Mz24MUZIssh3yMygjd1AxEJaElnjbL12hlTPRuNJg6MPpF4CacOG0ACDbiyjkeQ%3D%3D@vijaymongodbtest.mongo.cosmos.azure.com:10255/?ssl=true&retryWrites=false&loadBalanced=false&serverSelectionTimeoutMS=5000&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-256";

    public static void main(String[] args) throws Exception {
        MongoJsonUploader uploader = new MongoJsonUploader();
        Path dirPath = Paths.get("C:\\Users\\ADMIN\\Documents\\ADIA-dev");
        List<Path> databases = new ArrayList<>();

        try {
            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!dir.equals(dirPath)) {  // Check if the current directory is not the root directory
                        databases.add(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Path currentdb: databases) {
            System.out.println("Processing "+currentdb.getFileName().toString());
            List<Path> jsonFiles = uploader.findJsonFilesInDirectory(currentdb);
           uploader.processJsonFiles(jsonFiles,currentdb.getFileName().toString());
        }
    }

    public List<Path> findJsonFilesInDirectory(Path dirPath) throws Exception {
        List<Path> jsonFiles = new ArrayList<>();
        Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".json")) {
                    jsonFiles.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return jsonFiles;
    }

    public void processJsonFiles(List<Path> jsonFiles, String DBname) throws Exception {
        MongoClient mongoClient = MongoClients.create(CONNECTION_URI);
        MongoDatabase database = mongoClient.getDatabase(DBname);

        for (Path filePath : jsonFiles) {
            if (filePath.toString().endsWith(".json") && filePath.toString().contains("_indexes")) {
                try {

                    String collectionName = filePath.getFileName().toString().replace("_indexes.json", "");
                    MongoCollection<Document> collection = database.getCollection(collectionName);
                    String content = new String(Files.readAllBytes(filePath));
                    JSONArray jsonArray = new JSONArray(content);
                    jsonArray.forEach(item -> {
                        Document indexDoc = Document.parse(item.toString());
                        Document keys = (Document) indexDoc.get("key");
                        IndexOptions options = new IndexOptions();
                        if (indexDoc.containsKey("name")) {
                            options.name((String) indexDoc.get("name"));
                        }
                        if (indexDoc.containsKey("unique")) {
                            options.unique((Boolean) indexDoc.get("unique"));
                        }
                        System.out.println("Creating index "+item.toString()+" in "+collectionName);
                        collection.createIndex(keys, options);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (Exception e) {
                    System.err.println("Error importing indexes from file: " + filePath);
                    e.printStackTrace();
                }
            }
        }

        for (Path filePath : jsonFiles) {
            if (filePath.toString().endsWith(".json") && !filePath.toString().contains("_indexes")) {
                JSONArray jsonArray = readJsonFile(filePath);
                String collectionName = filePath.getFileName().toString().replace(".json", "");
                MongoCollection<Document> collection = database.getCollection(collectionName);

                if (!collectionExists(database, collectionName)) {
                    database.createCollection(collectionName);
                    collection = database.getCollection(collectionName);
                } else {
                    database.getCollection(collectionName).drop();
                    database.createCollection(collectionName);
                    collection = database.getCollection(collectionName);
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println("Inserting" + i + " in" + collectionName);
                    Document doc = Document.parse(jsonArray.getJSONObject(i).toString());
                    collection.insertOne(doc);
                    if (i % 50 == 0) {
                        Thread.sleep(1000);
                    }
                    if (i % 1000 == 0) {
                        Thread.sleep(5000);
                    }
                }
            }
        }


        mongoClient.close();
    }

    public JSONArray readJsonFile(Path filePath) {
        System.out.println("Reading JSON file: " + filePath);
        try {
            String content = new String(Files.readAllBytes(filePath));
            return new JSONArray(content);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("Invalid JSON format in file: " + filePath);
            e.printStackTrace();
        }
        return new JSONArray(); // Return an empty JSONArray if there's an error
    }
    private boolean collectionExists(MongoDatabase database, String collectionName) {
        for (String name : database.listCollectionNames()) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }
}
