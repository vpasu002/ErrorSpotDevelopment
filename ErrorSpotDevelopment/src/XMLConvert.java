

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.*;

import com.mongodb.*;
import com.mongodb.util.JSON;

public class XMLConvert
{

	public static String readXML(String file) throws IOException 
	{
		FileReader inputXMLFile = new FileReader(file);
		BufferedReader br = new BufferedReader(inputXMLFile);
		String line;
		StringBuilder sb = new StringBuilder();
		while((line=br.readLine())!= null){
		    sb.append(line.trim());
		}
		return sb.toString();
		
	}
	
	public static void XMLConversion(String inputXML)
	{
         JSONObject jsonObj = XML.toJSONObject(inputXML); 
         System.out.println(jsonObj);
         MongoDBDocumentWrite(jsonObj.toString());
	}
	
	public static void MongoDBDocumentWrite(String inputJSON)
 
	{		
		Mongo mongo = new Mongo("172.16.120.70", 27017);
		DB db = mongo.getDB("test");
		DBCollection collection = db.getCollection("dummyColl");

		// convert JSON to DBObject directly
		DBObject dbObject = (DBObject) JSON.parse(inputJSON);

		collection.insert(dbObject);

		DBCursor cursorDoc = collection.find();
		while (cursorDoc.hasNext()) {
			System.out.println(cursorDoc.next());
		}

		System.out.println("Done");

		
	}		
	public static String MongoDBDocumentRead( String host, int port, String dbName,String collectionName)
	{
		try{
		Mongo mongo = new Mongo(host, port);
		DB db = mongo.getDB(dbName);
		DBCollection collection = db.getCollection(collectionName);
		DBCursor cursorDoc = collection.find();
		String json = null;
		while (cursorDoc.hasNext())
		{
			 json = cursorDoc.next().toString();
			System.out.println(json);
		}
		System.out.println(json);
	    JSONObject jsonObj = new JSONObject(json);
	    return XML.toString(jsonObj);

		}

		catch(Exception e) 
		{
			e.printStackTrace();
			return "Error Happened";
		}
	}
	public static void main(String[] args) throws IOException
	{
		//String fileOutput = readXML("C:\\Users\\Vinay\\Desktop\\JavaInput.xml");
		//XMLConversion(fileOutput);
		String outputFromDB = MongoDBDocumentRead("172.16.120.70", 27017, "test", "dummyColl");
		System.out.println("output from DB:");
		System.out.println(outputFromDB);
		
		
	}

}
