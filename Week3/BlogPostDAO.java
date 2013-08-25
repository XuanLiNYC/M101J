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

package course;

import com.mongodb.*;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import javax.print.attribute.standard.DateTimeAtProcessing;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class BlogPostDAO {
    DBCollection postsCollection;

    public BlogPostDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public DBObject findByPermalink(String permalink) {

        DBObject post = null;


        // XXX HW 3.2,  Work Here
        post =   this.postsCollection.findOne(new BasicDBObject("permalink",permalink));



        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<DBObject> findByDateDescending(int limit) {

        List<DBObject> posts = new ArrayList<DBObject>();


        // XXX HW 3.2,  Work Here
        // Return a list of DBObjects, each one a post from the posts collection
        DBCursor cursor = this.postsCollection.find(new BasicDBObject()).sort(new BasicDBObject("date",-1)).limit(limit);
        while(cursor.hasNext()){
            posts.add(cursor.next());
        }
        cursor.close();
        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        BasicDBObject post = new BasicDBObject();
      // BasicDBObject comments =  new ArrayList();
      //  post.append("author",username).append("body", body).append("permalink",permalink).append("tags",tags).append("comments",comments ).append("date", "8/18/2013");
        post.put("title", title);
        post.put("author", username);
        post.put("body", body);
        post.put("permalink", permalink);
        post.put("tags", tags);
        post.put("comments", new ArrayList<String>());
        post.put("date", new Date());


        try {
            postsCollection.insert(post);
        } catch ( Exception e) {
            System.out.println("Error insert post");
        }

        // XXX HW 3.2, Work Here
        // Remember that a valid post has the following keys:
        // author, body, permalink, tags, comments, date
        //
        // A few hints:
        // - Don't forget to create an empty list of comments
        // - for the value of the date key, today's datetime is fine.
        // - tags are already in list form that implements suitable interface.
        // - we created the permalink for you above.

        // Build the post object and insert it


        return permalink;
    }




   // White space to protect the innocent








    // Append a comment to a blog post
      /*
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        // XXX HW 3.3, Work Here
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments



    }

       */
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        // XXX HW 3.3, Work Here
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        // operator to append the comment on to any existing list of comments
        DBObject comment = new BasicDBObject();
        comment.put("author", name);
        comment.put("body", body);
        if(StringUtils.isNotEmpty(email)) {
            comment.put("email", email);
        }
        DBObject post = this.postsCollection.findOne(new BasicDBObject("permalink", permalink));
        List<DBObject> comments = (List<DBObject>)post.get("comments");
        comments.add(comment);

        System.out.println(post);

        this.postsCollection.update(new BasicDBObject("permalink", permalink),post);

    }



}
