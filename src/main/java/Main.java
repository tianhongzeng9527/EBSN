import com.mongodb.*;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        System.out.println("Hello World!");
        Mongo mongo = new Mongo();
        System.out.println(mongo.getDatabaseNames());
        DB db = mongo.getDB("tian");
        System.out.println(db.getCollectionNames());
        DBCollection dbCollection = db.getCollection("memberInformation");
        System.out.println(dbCollection.find().next().toString());
        Pattern john = Pattern.compile("145/*");
//        BasicDBObject query = new BasicDBObject("time", john);
//        dbObject.put("time",new DATEdbObject.get("time"));
        System.out.println(Integer.MAX_VALUE);
//        System.out.println(dbObject);
        DBCursor dbCursor = dbCollection.find();
        while (dbCursor.hasNext()) {
            System.out.println(((BasicDBList) (dbCursor.next().get("groups"))).size());
            DBObject dbObject = dbCursor.next();
            int userId = (Integer)dbObject.get("id");
            if (((BasicDBList) (dbObject.get("topic"))).size() > 0 && ((BasicDBList) (dbObject.get("groups"))).size() > 0) {
                BasicDBObject query1 = new BasicDBObject();
                query1.put("lat", dbObject.get("lat"));
                query1.put("lon", dbObject.get("lon"));
                DBCursor dbCursor1 = dbCollection.find(query1);
                while (dbCursor1.hasNext()) {
                    DBObject dbObject1 = dbCursor1.next();
                    int friendId = (Integer)dbObject1.get("id");
                    if(userId != friendId){
                        double eventScore = getEventScore((BasicDBList) dbObject.get("events"), (BasicDBList) dbObject1.get("events"));
                        double groupScore = getGroupScore((BasicDBList) dbObject.get("groups"), (BasicDBList) dbObject1.get("groups"));
                        double topicScore = getTopicScore((BasicDBList) dbObject.get("topic"), (BasicDBList) dbObject1.get("topic"));
                        if (eventScore > 0 && groupScore > 0 && topicScore > 0) {
                            System.out.println(dbObject1.toString());
                            System.out.println(getEventScore((BasicDBList) dbObject.get("events"), (BasicDBList) dbObject1.get("events")));
                            System.out.println(getGroupScore((BasicDBList) dbObject.get("groups"), (BasicDBList) dbObject1.get("groups")));
                            System.out.println(getTopicScore((BasicDBList) dbObject.get("topic"), (BasicDBList) dbObject1.get("topic")));
                        }
                    }
//                    else{
//                        System.out.println(userId+"  "+friendId+"***");
//                        return;
//                    }
                }
                System.out.println("____________");
            }
            System.out.println(dbCursor.next());
            System.out.println("--");
            Thread.sleep(1000);
        }
        DBCollection dbCollection1 = db.getCollection("event");
        System.out.println(dbCollection1.find().next().toString());
    }

    public static double getGroupScore(BasicDBList user, BasicDBList friend) {
        int userSize = user.size();
        int friendSize = friend.size();
        Set<String> userGroupId = new HashSet<String>();
        Set<String> friendGroupId = new HashSet<String>();
        Set<String> sumGroupId = new HashSet<String>();
        for (int i = 0; i < userSize; i++) {
            String id = user.get(i).toString();
            userGroupId.add(id);
            sumGroupId.add(id);
        }
        for (int i = 0; i < friendSize; i++) {
            String id = friend.get(i).toString();
            friendGroupId.add(id);
            sumGroupId.add(id);
        }
        return (userGroupId.size() + friendGroupId.size() - sumGroupId.size()) / (double) sumGroupId.size();
    }

    public static double getEventScore(BasicDBList user, BasicDBList friend) {
        int userSize = user.size();
        int friendSize = friend.size();
        Set<String> userEvent = new HashSet<String>();
        Set<String> friendEvent = new HashSet<String>();
        Set<String> sumEvent = new HashSet<String>();
        for (int i = 0; i < userSize; i++) {
            String id = user.get(i).toString();
            userEvent.add(id);
            sumEvent.add(id);
        }
        for (int i = 0; i < friendSize; i++) {
            String id = friend.get(i).toString();
            friendEvent.add(id);
            sumEvent.add(id);
        }
        return (userEvent.size() + friendEvent.size() - sumEvent.size()) / (double) sumEvent.size();
    }

    public static double getTopicScore(BasicDBList user, BasicDBList friend) {
        int userSize = user.size();
        int friendSize = friend.size();
        Set<Integer> userTopicId = new HashSet<Integer>();
        Set<Integer> friendTopicId = new HashSet<Integer>();
        Set<Integer> sumTopicId = new HashSet<Integer>();
        for (int i = 0; i < userSize; i++) {
            int id = new JSONObject(user.get(i).toString()).getInt("id");
            userTopicId.add(id);
            sumTopicId.add(id);
        }
        for (int i = 0; i < friendSize; i++) {
            int id = new JSONObject(friend.get(i).toString()).getInt("id");
            friendTopicId.add(id);
            sumTopicId.add(id);
        }
        return (userTopicId.size() + friendTopicId.size() - sumTopicId.size()) / (double) sumTopicId.size();
    }

    public static double getDistanceScore(double lat1, double lon1, double lat2, double lon2) {
        return Math.pow((lat1 - lat2), 2) + Math.pow((lon1 - lon2), 2);
    }
}
