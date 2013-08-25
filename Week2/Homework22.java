package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

import java.net.UnknownHostException;
import java.util.Random;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: xuanli
 * Date: 8/10/13
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Homework22 {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");

        System.out.println("\nFind all: ");

        for(int id = 0 ; id <= 200; id ++) {
            QueryBuilder builder = QueryBuilder.start("student_id").is(id);
            DBCursor cursor = collection.find( builder.get()).sort(new BasicDBObject("score", 1)).limit(1);

            try {
                while (cursor.hasNext() ) {
                    DBObject cur = cursor.next();
                    System.out.println(cur);
                    collection.remove(cur);

                }
            } finally {
                cursor.close();
            }


        }



        System.out.println("\nCount:");
        long count = collection.count();
        System.out.println(count);
    }

}





