/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class week31 {
    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection();

        updateCollection(collection);
    }

    private static DBCollection createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection collection = db.getCollection("students");
        return collection;
    }

       private static void printCollection(final DBCollection collection) {
        DBCursor cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    private static void updateCollection(final DBCollection collection) {
        for(int i = 0 ; i <200 ; i++ ) {

            DBCursor cursor = collection.find(new BasicDBObject("_id", i));
            try {
                while (cursor.hasNext()) {

                    DBObject obj = cursor.next();
                    ArrayList<DBObject> list =  (ArrayList<DBObject>)  obj.get("scores");
                    double min = 0;
                    int num = 0;
                    String temps ;
                    String homework =  new String("homework");
                    int j = 0 ;
                    for( j =0; j < list.size();j++ ) {
                        DBObject tempobj =  list.get(j);
                        String type = (String) tempobj.get("type");
                        Double value = (Double) tempobj.get("score");

                        if(type.equals(homework) ) {
                            if(min == 0) {
                                min =  value;
                                num = j;
                            }   else {
                                if(value < min) {
                                    min = value;
                                    num = j;
                                }
                            }
                        }
                    }
                    list.remove(num);
                    System.out.println( obj);
                    collection.update(new BasicDBObject("_id", i),obj, true,false)    ;

                }
            } finally {
                cursor.close();
            }

        }

    }
}
