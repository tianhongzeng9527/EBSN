import com.mongodb.*;

import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by tian on 16-4-11.
 */
public class Calculate {
    private static double lat = 36;
    private static double lon = -122;

    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("tian");
        System.out.println(db.getCollectionNames());
        DBCollection dbCollection = db.getCollection("event");
        eventMember(dbCollection);
    }

    public static void eventMember(DBCollection dbCollection) {
        DBCursor dbCursor = dbCollection.find();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            BasicDBList basicDBList = (BasicDBList) dbObject.get("members");
            if (map.containsKey(basicDBList.size())) {
                map.put(basicDBList.size(), map.get(basicDBList.size()) + 1);
            } else {
                map.put(basicDBList.size(), 1);
            }
        }
        System.out.println(map);
        int sum = 0;
        for (Map.Entry<Integer, Integer> m : map.entrySet()) {
            sum += m.getValue();
        }
        System.out.println(sum);
    }

    public static void locationNum(DBCollection dbCollection) {
        int sum = 0;
        for (int i = 0; i < 20; i++) {
            lat += 0.1;
            lon = -121;
            try {
                for (int j = 0; j < 20; j++) {
                    lon -= 0.1;
                    DecimalFormat df = new DecimalFormat("######0.0");
                    Pattern latPattern = Pattern.compile(df.format(lat));
                    Pattern lonPattern = Pattern.compile(df.format(lon));
                    BasicDBObject query = new BasicDBObject();
                    query.append("lat", latPattern);
                    query.append("lon", lonPattern);
                    int count = dbCollection.find(query).count();
                    if (count > 0)
                        System.out.println("lat:" + df.format(lat) + " lon:" + df.format(lon) + " " + "nums:" + count);
                    sum += dbCollection.find(query).count();
                }
            } catch (Exception e) {
                System.out.println("wrong");
            }
        }
        System.out.println(sum);
    }
}
