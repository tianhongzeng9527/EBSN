import com.mongodb.*;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by tian on 16-4-10.
 * 71792232
 * [228510985, 228412847, 228841077, 228444684, 228823085]
 * [228519523, 228412847, tkjvflyvdbzb, 228670984, 228841077,
 * 228437347, 228444684, 228823085, dbfkmlyvdbzb, dnhjgkyvdbbc, qdsdncyvdbbc,
 * 228877715, 228631828, 228781358, fkvfdlyvdbbc, tzvgplyvdbbc, 228292878,
 * 228193319, 228777776, 228646649, 228979868, 228026412, xsbrplyvdbzb, 228718696, 228693507, hnqznlyvdbzb, lnvkqlyvdbbc, 228612616, 228705946, ddgkpkyvdbbc, 228811550, dswrjlyvdbhb, 228601983, 227713444, cjhmmlyvdbzb, wdkkmlyvdbzb, jzdfjlyvdbzb, 228872244, 227900705, 228669857, 228933844, qjmsqcyvdbbc, nmqjjlyvdbrb, 228103051, dmmdgkyvdbbc, 228734465, 228728224, 228926878, 228951250, wfhdnlyvdbbc, bfbknlyvdbzb, 228767874, 228534278, 228202469, 228967425, 228729557, 228646185, dcltdfyvdbbc, jfmvflyvdbzb, 228406768, jwgmnlyvdbbc, 228646392, 228709667, 226239240, 227966240, 228730800, 228887789, 228218229, 228627748, rnrwmlyvdbzb, gdpxnlyvdbbc, qbdbqdyvdbzb, 226571829, 228436181, 228908280, 228546709, 228233160, 228724591, 227928471, 228959620, 228932141, 226754667, qnwdndyvdbbc, dknmfdyvdbbc, 228614900, 226999293, gppqmlyvdbbc, 227999961, 228767390, mdwrplyvdbbc, ldtthlyvdbzb, 228829200, 228233937, 228601953, 228914054, 228793423, dvhdgjyvdbbc, 228914739, xgjqllyvdbbc, qxkxxhyvdbbc, 227610502, dtddgjyvdbbc, 227541520, 228637764, 228828135, prxvmlyvdbzb, 227994887, 228026818, 228407414, fdgcqlyvdbbc, wxgqflyvdbzb, hltnqlyvdbzb, 228729290, 226409521, 228732349, mfglmlyvdbbc, 228543484, 228970772, btdmqlyvdbbc, qfkhrkyvdbbc, 228771272, dtvsthyvdbqb, gpnqhlyvdbzb, 228745569, 228455399, 228757436, sxwxnlyvdbzb, qbbvbhyvdbbc, 228620543, 228744482, dlfjllyvdbzb, 228898392, 228784250, 228065413, 228770777, 228953041, jqsqklyvdbzb, 228694209, dzjdgjyvdbbc, dcgwlkyvdbbc, 227869497, 228688211, crpkllyvdbzb, 228348802, rjzvplyvdbbc, 228719927, qfpjjjyvdbzb, 228414750, 228931262, htvtnlyvdbzb, zrmgmlyvdbbc, 228221927, 228769538, 228519454, 228815410, 228925913, qstxkcyvdbbc, 228804894, djrxnlyvdbbc, 228910424, mnddqlyvdbbc, spbmqyvdbbc, 228631868, 228203800, zcpbplyvdbzb, 228060808, bbjtplyvdbbc, 228240092, sbkzhlyvdbzb, 228618870, qtrnpdyvdbbc, 228823059, dszcnlyvdbbc, kpcrflyvdbzb, jmmrmlyvdbbc, 228510985, 228436580, 228968139, 228356511, pnknqlyvdbzb, wlfchlyvdbbc, 228902018, 228482654, wkdfqlyvdbbc, 228868859, 228933085, 228568598, cbhpnlyvdbbc, xdzmqlyvdbzb, 228614983, 226732716, 228876001, qssrchyvdbbc, xplqclyvdbbc, krtmqlyvdbbc, qfglmlyvdbbc, 227578380, 227211544, 228718990, sgwvmlyvdbzb, 228575423]
 */
public class Event {
    public Set<String> recommendMembersId = new HashSet<String>();
    private Map<String, DBObject> recommendEventInformation = new HashMap<String, DBObject>();
    private Map<String, DBObject> recommendGroupInformation = new HashMap<String, DBObject>();
    private Map<Integer, DBObject> userInformation = new HashMap<Integer, DBObject>();
    public Set<String> recommendEventsId = new HashSet<String>();
    public Map<String, BasicDBList> attendEventMembersId = new HashMap<String, BasicDBList>();
    private Map<Integer, List<String>> result1 = new HashMap<Integer, List<String>>();
    private Map<Integer, List<String>> result2 = new HashMap<Integer, List<String>>();
    private Map<Integer, List<String>> result3 = new HashMap<Integer, List<String>>();
    private Map<Integer, List<String>> result4 = new HashMap<Integer, List<String>>();
    private Map<Integer, List<String>> result5 = new HashMap<Integer, List<String>>();
    private Mongo mongo;
    private DB db;
    private DBCollection memberInformationDBCollection;
    private DBCollection groupDBCollection;
    private DBCollection eventDBCollection;
    public int sum = 0;
    public static double m = 0.5;
    public static double n = 0.5;
    public static double p = 0.5;
    public static double q = 0.5;
    public static double r = 0.5;

    public static void main(String[] args) throws UnknownHostException, FileNotFoundException {
        Event event = new Event();
        event.init();
        event.generateRecommendMemberId();
//        System.out.println(event.eventRecommendResult(144247392));
        System.out.println(event.recommendMembersId);
        System.out.println(event.recommendEventsId);
        System.out.println(event.attendEventMembersId);
        double i = 0;
        System.out.println(event.recommendMembersId.size());
        for (String s : event.recommendMembersId) {
            i++;
            try {
                DBObject userObject;
                BasicDBObject query = new BasicDBObject("id", Integer.valueOf(s));
                userObject = event.memberInformationDBCollection.findOne(query);
                BasicDBList basicDBList1 = (BasicDBList) userObject.get("topic");
                BasicDBList basicDBList2 = (BasicDBList) userObject.get("groups");
                BasicDBList basicDBList3 = (BasicDBList) userObject.get("events");
                if (basicDBList1.size() > 0 && basicDBList2.size() > 0 && basicDBList3.size() > 0) {
                    q = 0.1;
                    event.result1.put(Integer.valueOf(s), event.eventRecommendResult(Integer.valueOf(s)));
                    q = 0.3;
                    event.result2.put(Integer.valueOf(s), event.eventRecommendResult(Integer.valueOf(s)));
                    q = 0.5;
                    event.result3.put(Integer.valueOf(s), event.eventRecommendResult(Integer.valueOf(s)));
                    q = 0.7;
                    event.result4.put(Integer.valueOf(s), event.eventRecommendResult(Integer.valueOf(s)));
                    q = 0.9;
                    event.result5.put(Integer.valueOf(s), event.eventRecommendResult(Integer.valueOf(s)));
                } else {
                    event.sum++;
                }
            } catch (Exception e) {
                event.sum++;
            }
            System.out.println("****" + (i / event.recommendMembersId.size()) + "  " + i);
        }
        for (int k = 1; k <= 10; k++) {
            System.out.print(event.calculatePrecision(event.result1, k));
            System.out.println("---");
            System.out.print(event.calculatePrecision(event.result2, k));
            System.out.print("---");
            System.out.print(event.calculatePrecision(event.result3, k));
            System.out.print("---");
            System.out.print(event.calculatePrecision(event.result4, k));
            System.out.print("---");
            System.out.println(event.calculatePrecision(event.result5, k));
            System.out.println("********");
        }

    }

    public double calculatePrecision(Map<Integer, List<String>> result, int i) {
        double score = 0;
        for (Map.Entry<Integer, List<String>> map : result.entrySet()) {
            List<String> list = map.getValue();
            for (int j = 0; j < i; j++) {
                DBObject dbObject;
                if (recommendEventInformation.containsKey(list.get(j)))
                    dbObject = recommendEventInformation.get(list.get(j));
                else {
                    BasicDBObject eventQuery = new BasicDBObject("id", list.get(j));
                    dbObject = eventDBCollection.find(eventQuery).next();
                    recommendEventInformation.put(list.get(j), dbObject);
                }
                BasicDBList dbList = (BasicDBList) dbObject.get("members");
                if (dbList.contains(String.valueOf(map.getKey()))) {
                    score++;
//                    System.out.println(dbObject);
//                    System.out.println(map.getKey());
//                    System.out.println(map.getValue());
                    break;
                }
            }
//            for (String s : list) {
//                BasicDBObject eventQuery = new BasicDBObject("id", s);
//                DBObject dbObject = eventDBCollection.find(eventQuery).next();
//                BasicDBList dbList = (BasicDBList) dbObject.get("members");
//                if (dbList.contains(String.valueOf(map.getKey()))) {
//                    score++;
////                    System.out.println(dbObject);
////                    System.out.println(map.getKey());
////                    System.out.println(map.getValue());
//                    break;
//                }
//            }
        }
//        System.out.println(sum);
        System.out.println(score);
        System.out.println(result.size());
//        System.out.println(recommendMembersId.size());
        return score / (result.size());
    }

    public void init() throws UnknownHostException {
        mongo = new Mongo();
        db = mongo.getDB("tian");
        memberInformationDBCollection = db.getCollection("memberInformation");
        groupDBCollection = db.getCollection("groups");
        eventDBCollection = db.getCollection("event");
    }

    public List<String> eventRecommendResult(int userId) {
        List<String> list = new ArrayList<String>();
        try {
            Map<String, Double> distanceScore = new HashMap<String, Double>();
            Map<String, Double> groupScore = new HashMap<String, Double>();
            Map<String, Double> topicScore = new HashMap<String, Double>();
            Map<String, Double> friendScore = new HashMap<String, Double>();
            Map<String, Double> historyScore = new HashMap<String, Double>();
            Map<String, Double> eventSumScore = new HashMap<String, Double>();
            DBObject userObject;
            if (userInformation.containsKey(userId))
                userObject = userInformation.get(userId);
            else {
                BasicDBObject userQuery = new BasicDBObject("id", userId);
                userObject = memberInformationDBCollection.find(userQuery).next();
                userInformation.put(userId, userObject);
            }
            for (String eventId : recommendEventsId) {
//                System.out.println(eventId);
                DBObject eventObject;
                if (recommendEventInformation.containsKey(eventId)) {
                    eventObject = recommendEventInformation.get(eventId);
//                    System.out.println(eventId+"contain");
                } else {
                    BasicDBObject eventQuery = new BasicDBObject("id", eventId);
                    eventObject = eventDBCollection.find(eventQuery).next();
                    recommendEventInformation.put(eventId, eventObject);
                }
                double distance = getDistanceScore(userId, eventId, userObject, eventObject);
                distanceScore.put(eventId, distance);
                double friend = getFriendsScore(userId, eventId, userObject);
                friendScore.put(eventId, friend);
                double group = getUserEventGroupScore(userId, eventId, userObject, eventObject);
                groupScore.put(eventId, group);
                double event = getUserEventTopicScore(userId, eventId, userObject, eventObject);
                topicScore.put(eventId, event);
                double history = getHistoryEventScore(userId, eventId, userObject, eventObject);
                historyScore.put(eventId, history);
//                System.out.println(distance);
//                System.out.println(friend);
//                System.out.println(event);
//                System.out.println(group);
//                System.out.println(history);
            }
            eventSumScore = getEventSumScore(distanceScore, groupScore, topicScore, friendScore, historyScore);
            sortMapByValue2(eventSumScore, list);
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public Map<String, Double> getEventSumScore(Map<String, Double> distanceScore, Map<String, Double> groupScore, Map<String, Double> topicScore,
                                                Map<String, Double> friendScore, Map<String, Double> historyScore) {
        Map<String, Double> sum = new HashMap<String, Double>();
        for (Map.Entry<String, Double> map : distanceScore.entrySet()) {
            String eventId = map.getKey();
            double eventDistanceScore = map.getValue();
            double eventGroupScore = groupScore.get(eventId);
            double eventFriendScore = friendScore.get(eventId);
            double eventTopicScore = topicScore.get(eventId);
            double eventHistoryScore = historyScore.get(eventId);
            sum.put(eventId, m * eventDistanceScore + n * eventGroupScore + p * eventFriendScore + q * eventTopicScore + r * eventHistoryScore);
        }
//        System.out.println(m + "  " + n + "  " + p + "  " + q + "  " + r+"  "+sum);
        return sum;
    }

    public double getHistoryEventScore(int userId, String eventId, DBObject userObject, DBObject eventObject) {
//        double score = 0;
////        BasicDBObject userQuery = new BasicDBObject("id", userId);
////        DBObject userObject = memberInformationDBCollection.find(userQuery).next();
//        BasicDBList userEvent = (BasicDBList) userObject.get("events");
//        for (int i = 0; i < userEvent.size(); i++) {
//            score += eventSimilarScore(userEvent.get(i).toString(), eventId, eventObject);
//            System.out.println(score);
//        }
//        return score / userEvent.size();
        return 0;
    }

    public double eventSimilarScore(String event1, String event2, DBObject dbObject1) {
        try {
//            BasicDBObject eventQuery1 = new BasicDBObject("id", event1);
            BasicDBObject eventQuery2 = new BasicDBObject("id", event1);
//            DBObject dbObject1 = eventDBCollection.find(eventQuery1).next();
            DBObject dbObject2 = eventDBCollection.find(eventQuery2).next();
            String group1 = dbObject1.get("group").toString();
            String group2 = dbObject2.get("group").toString();
//            System.out.println(dbObject1);
//            System.out.println(dbObject2);
            if (group1.equals(group2))
                return 1;
            return groupSimilarScore(group1, group2);
        } catch (Exception e) {
            System.out.println("error");
            return 0;
        }
    }

    public double groupSimilarScore(String group1, String group2) {
        DBObject dbObject1;
        DBObject dbObject2;
        BasicDBObject groupQuery1;
        BasicDBObject groupQuery2;
        if (!recommendGroupInformation.containsKey(group1)) {
            groupQuery1 = new BasicDBObject("id", group1);
            dbObject1 = groupDBCollection.findOne(groupQuery1);
            recommendGroupInformation.put(group1, dbObject1);
        } else {
            dbObject1 = recommendGroupInformation.get(group1);
        }
        if (recommendGroupInformation.containsKey(group2)) {
            dbObject2 = recommendGroupInformation.get(group2);
        } else {
            groupQuery2 = new BasicDBObject("id", group2);
            dbObject2 = groupDBCollection.findOne(groupQuery2);
            recommendGroupInformation.put(group2, dbObject2);
        }
        BasicDBList groupTopic1 = (BasicDBList) dbObject1.get("topics");
        BasicDBList groupTopic2 = (BasicDBList) dbObject2.get("topics");
        Set<String> topic = new HashSet<String>();
        for (int i = 0; i < groupTopic1.size(); i++) {
            topic.add(groupTopic1.get(i).toString());
        }
        for (int i = 0; i < groupTopic2.size(); i++) {
            topic.add(groupTopic2.get(i).toString());
        }
        return (groupTopic1.size() + groupTopic2.size() - topic.size()) / ((double) topic.size());
    }

    public double getUserEventTopicScore(int userId, String eventId, DBObject userObject, DBObject eventObject) {
        try {
//            BasicDBObject userQuery = new BasicDBObject("id", userId);
//            DBObject userObject = memberInformationDBCollection.find(userQuery).next();
            BasicDBList userTopic = (BasicDBList) userObject.get("topic");
//            BasicDBObject eventQuery = new BasicDBObject("id", eventId);
//            DBObject eventObject = eventDBCollection.find(eventQuery).next();
            Integer groupId = Integer.valueOf(eventObject.get("group").toString());
            DBObject groupObject;
            if (recommendGroupInformation.containsKey(String.valueOf(groupId))) {
                groupObject = recommendGroupInformation.get(String.valueOf(groupId));
            } else {
                BasicDBObject groupQuery = new BasicDBObject("id", groupId);
//            System.out.println(groupQuery);
                groupObject = groupDBCollection.find(groupQuery).next();
                recommendGroupInformation.put(String.valueOf(groupId), groupObject);
            }
            BasicDBList groupTopic = (BasicDBList) groupObject.get("topics");
            Set<String> topic = new HashSet<String>();
            for (int i = 0; i < userTopic.size(); i++) {
                topic.add(userTopic.get(i).toString());
            }
            for (int i = 0; i < groupTopic.size(); i++) {
                topic.add(groupTopic.get(i).toString());
            }
            return ((double) topic.size()) / (userTopic.size() + groupTopic.size());
        } catch (Exception e) {
            return 0;
        }

    }

    public double getUserEventGroupScore(int userId, String eventId, DBObject userObject, DBObject eventObject) {
        double score = 0;
//        BasicDBObject userQuery = new BasicDBObject("id", userId);
//        DBObject userObject = memberInformationDBCollection.find(userQuery).next();
//        BasicDBObject eventQuery = new BasicDBObject("id", eventId);
//        DBObject eventObject = eventDBCollection.find(eventQuery).next();
        BasicDBList userGroup = (BasicDBList) userObject.get("groups");
        if (userGroup.contains(eventObject.get("group")))
            score = 1;
        return score;
    }

    public double getFriendsScore(int userId, String eventId, DBObject userObject) {
        double score = 0;
//        List<Integer> friends = getFriends(userId);
//        for (Integer id : friends) {
//            if (((BasicDBList) userObject.get("events")).contains(id)) {
//                score++;
//            }
//        }
////        System.out.println("-------");
////        System.out.println(friends);
//        System.out.println(score+"***");
//        System.out.println("-------");
        return score;
    }

    public List<String> getHistoryEvent(int id, DBCollection dbCollection) {
        List<String> historyEvent = new ArrayList<String>();
        BasicDBObject query = new BasicDBObject("id", id);
        DBCursor dbCursor = dbCollection.find(query);
        DBObject dbObject = dbCursor.next();
        BasicDBList basicDBList = (BasicDBList) dbObject.get("events");
        for (int i = 0; i < basicDBList.size(); i++)
            historyEvent.add(basicDBList.get(i).toString());
        return historyEvent;
    }

    public void generateRecommendMemberId() {
        Pattern john = Pattern.compile("14559/*");
        BasicDBObject query = new BasicDBObject("time", john);
//        System.out.println(eventDBCollection.find(query).count());
        DBCursor dbCursor = eventDBCollection.find(query);
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            BasicDBList basicDBList = (BasicDBList) dbObject.get("members");
            for (int i = 0; i < basicDBList.size(); i++) {
                String memberId = basicDBList.get(i).toString();
                recommendMembersId.add(memberId);
            }
            recommendEventsId.add(dbObject.get("id").toString());
            attendEventMembersId.put(dbObject.get("id").toString(), basicDBList);
        }
    }

    public List<Integer> generateEventTopics(int groupId, DBCollection dbCollection) {
        List<Integer> list = new ArrayList<Integer>();
        BasicDBObject query = new BasicDBObject("id", groupId);
        DBCursor dbCursor = dbCollection.find(query);
        DBObject dbObject = dbCursor.next();
        BasicDBList topics = (BasicDBList) dbObject.get("topics");
        for (int i = 0; i < topics.size(); i++) {
            list.add(new JSONObject(topics.get(i).toString()).getInt("id"));
        }
        return list;
    }

    public List<Integer> getFriends(int userId) {
        List<Integer> list = new ArrayList<Integer>();
        Map<Integer, Double> friendScore = new HashMap<Integer, Double>();
        BasicDBObject query = new BasicDBObject("id", userId);
        DBObject userObject = memberInformationDBCollection.findOne(query);
        double lat = Double.valueOf(userObject.get("lat").toString());
        double lon = Double.valueOf(userObject.get("lon").toString());
        BasicDBObject query2 = new BasicDBObject();
        query2.put("lat", lat);
        query2.put("lon", lon);
        DBCursor dbCursor = memberInformationDBCollection.find(query2);
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            int friendId = (Integer) dbObject.get("id");
            if (userId != friendId) {
                double eventScore = getUserFriendEventScore((BasicDBList) userObject.get("events"), (BasicDBList) dbObject.get("events"));
                double groupScore = getUserFriendGroupScore((BasicDBList) userObject.get("groups"), (BasicDBList) dbObject.get("groups"));
                double topicScore = getUserFriendTopicScore((BasicDBList) userObject.get("topic"), (BasicDBList) dbObject.get("topic"));
                if (eventScore > 0 && groupScore > 0 && topicScore > 0) {
                    friendScore.put(friendId, eventScore + groupScore + topicScore);
                }
            }
        }
        sortMapByValue(friendScore, list);
        return list;
    }

    private void sortMapByValue(Map<Integer, Double> map, List<Integer> friendList) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            //升序排序
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
//        System.out.println(list);
        int i = 0;
        for (Map.Entry<Integer, Double> mapping : list) {
            if (i++ < 10) {
                friendList.add(mapping.getKey());
            } else {
                break;
            }
        }
    }

    private void sortMapByValue2(Map<String, Double> map, List<String> friendList) {
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            //升序排序
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
//        System.out.println(list);
        int i = 0;
        for (Map.Entry<String, Double> mapping : list) {
            if (i++ < 10) {
                friendList.add(mapping.getKey());
            } else {
                break;
            }
        }
    }

    public double getSumScore(DBObject dbObject) {
        double sumScore = 0;
        return sumScore;
    }

    public double getUserFriendGroupScore(BasicDBList user, BasicDBList friend) {
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

    public double getUserFriendEventScore(BasicDBList user, BasicDBList friend) {
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

    public double getUserFriendTopicScore(BasicDBList user, BasicDBList friend) {
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

    public double getDistanceScore(int userId, String eventId, DBObject eventObject, DBObject memberObject) {
//        BasicDBObject eventQuery = new BasicDBObject("id", eventId);
//        DBObject eventObject = eventDBCollection.find(eventQuery).next();
//        BasicDBObject memberQuery = new BasicDBObject("id", userId);
//        DBObject memberObject = memberInformationDBCollection.find(memberQuery).next();
        double userLat = Double.valueOf(memberObject.get("lat").toString());
        double userLon = Double.valueOf(memberObject.get("lon").toString());
        double eventLat = Double.valueOf(eventObject.get("lat").toString());
        double eventLon = Double.valueOf(eventObject.get("lon").toString());
//        return Math.pow((userLat - eventLat), 2) + Math.pow((userLon - eventLon), 2);
        return 1.0 / (Math.pow((userLat - eventLat) * 100, 2) + Math.pow((userLon - eventLon) * 100, 2) + 1);
    }

}
