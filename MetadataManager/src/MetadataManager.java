// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

import com.sun.javafx.binding.StringFormatter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 * Class for managing 'metadata' file used for webtoon metadata.
 */
public class MetadataManager {
    /**
     * Insert new webtoon, and generate a table for chapter database.
     * @param webtoonName new webtoon name
     * @param authorName new webiwtoon's author name
     * @param thumbnailImage thumbnail image's filename
     */
    public static void insertNewWebtoon(String webtoonName, String authorName, String thumbnailImage) {
//        String url = "jdbc:sqlite:metadata";
        String url = "jdbc:sqlite:metadata";
        try {
            // Open SQL
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            // insert webtoon SQL
            String sql = "INSERT INTO WebtoonListDB VALUES('"
                    + webtoonName + "', '" + authorName + "', '" + thumbnailImage + "');";
            statement.execute(sql);

            // create chapterListDB table SQL
            String webtoonHashID = HashManager.md5(webtoonName + "_" + authorName);
            sql = "CREATE TABLE ChapterListDB_" + webtoonHashID
                    + "(ChapterNumber TEXT, ChapterName TEXT, UploadedDate TEXT, " +
                    "LikeNumber INTEGER, DislikeNumber INTEGER, ThumbnailImage TEXT);";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert new chapter to the given webtoon, and image database for that chapter.
     * @param webtoonName webtoon name
     * @param authorName author name
     * @param chapterNumber new chapter number
     * @param chapterName new chapter name
     * @param uploadedDate new uploaded date
     * @param likeNumber number of likes
     * @param dislikeNumber number of dislikes
     * @param thumbnailImage filename of thumbnail images
     */
    public static void insertNewChapter(String webtoonName, String authorName,
                                        String chapterNumber, String chapterName, String uploadedDate,
                                        int likeNumber, int dislikeNumber, String thumbnailImage) {
        String url = "jdbc:sqlite:metadata";

        try {
            // Open SQL
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            String webtoonHashID = HashManager.md5(webtoonName + "_" + authorName);

            // insert chapter SQL
            String sql = "INSERT INTO ChapterListDB_" + webtoonHashID +  " VALUES('"
                    + chapterNumber + "', '" + chapterName + "', '" + uploadedDate + "', "
                    + likeNumber + ", " + dislikeNumber + ", '" + thumbnailImage + "');";
            statement.execute(sql);

            // create ImageListDB table SQL
            String chapterHashID = HashManager.md5(chapterNumber + "_" + chapterName);
            sql = "CREATE TABLE ImageListDB_" + webtoonHashID + "_" + chapterHashID
                    + "(Image TEXT);";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert image metadata to the given webtoon and chapter.
     * e.g., if image file name is hello_1 to hello_10, imageFileName becomes "hello_"
     * and startNumber = 1, endNumber = 10.
     * @param webtoonName webtoon name
     * @param authorName author name
     * @param chapterNumber chapter number
     * @param chapterName chapter name
     * @param imageFileName imagefile's name
     * @param startNumber start number
     * @param endNumber end number
     * @param digit digit number
     * @param extension extension if exists
     */
    public static void insertNewImages(String webtoonName, String authorName, String chapterNumber, String chapterName,
                                       String imageFileName, int startNumber, int endNumber,
                                       int digit, String extension) {
        String url = "jdbc:sqlite:metadata";

        try {
            // Open SQL
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            String webtoonHashID = HashManager.md5(webtoonName + "_" + authorName);
            String chapterHashID = HashManager.md5(chapterNumber + "_" + chapterName);


            // insert images into SQL
            String sql;
            for (int i = startNumber; i <= endNumber; i++) {
                String imageNumber = String.format(Locale.US, "%0" + digit + "d", i);
                sql = "INSERT INTO ImageListDB_" + webtoonHashID + "_"  + chapterHashID + " VALUES('"
                        + imageFileName + imageNumber + extension + "');";
                statement.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}