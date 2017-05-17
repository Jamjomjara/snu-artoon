// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

import java.util.Scanner;

/**
 * A manager that manages database by using the console.
 */
public class ConsoleManager {
    // values to be used in System.out.print()
    private static final String webtoonName = "Webtoon name : ";
    private static final String authorName = "Author name : ";
    private static final String chapterNumber = "Chapter number : ";
    private static final String chapterName = "Chapter name : ";
    private static final String thumbnailImage = "Thumbnail filename : ";
    private static final String defaultImage = "Image filename : ";
    private static final String uploadedDate = "Uploaded date : ";
    private static final String likeNumber = "Like Number : ";
    private static final String dislikeNumber = "Dislike Number : ";
    private static final String startNumber = "Start Number : ";
    private static final String endNumber = "End Number : ";
    private static final String askType = "1 - Webtoon, 2 - Chapter, 3 - Image, 4 - quit :";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int selectedType;
        System.out.print(askType);
        selectedType = scanner.nextInt();

        // variables used
        String webtoon;
        String author;
        String thumbnail;
        String chapterNo;
        String chapter;
        String uploaded;
        int like;
        int dislike;
        String defaultImg;
        int start;
        int end;

        boolean keepGoing = true;

        while (keepGoing) {
            switch (selectedType) {
                case 1:
                    // ask the information
                    System.out.print(webtoonName);
                    webtoon = scanner.nextLine();
                    System.out.print(authorName);
                    author = scanner.nextLine();
                    System.out.print(thumbnailImage);
                    thumbnail = scanner.nextLine();

                    // ask the manager
                    MetadataManager.insertNewWebtoon(webtoon, author, thumbnail);
                    break;

                case 2:
                    // ask the information
                    System.out.print(webtoonName);
                    webtoon = scanner.nextLine();
                    System.out.print(authorName);
                    author = scanner.nextLine();
                    System.out.print(chapterNumber);
                    chapterNo = scanner.nextLine();
                    System.out.print(chapterName);
                    chapter = scanner.nextLine();
                    System.out.print(uploadedDate);
                    uploaded= scanner.nextLine();
                    System.out.print(likeNumber);
                    like = scanner.nextInt();
                    System.out.print(dislikeNumber);
                    dislike = scanner.nextInt();
                    System.out.print(thumbnailImage);
                    thumbnail = scanner.nextLine();

                    // ask the manager
                    MetadataManager.insertNewChapter(webtoon, author, chapterNo, chapter, uploaded,
                            like, dislike, thumbnail);
                    break;

                case 3:
                    // ask the information
                    System.out.print(webtoonName);
                    webtoon = scanner.nextLine();
                    System.out.print(authorName);
                    author = scanner.nextLine();
                    System.out.print(chapterNumber);
                    chapterNo = scanner.nextLine();
                    System.out.print(chapterName);
                    chapter = scanner.nextLine();
                    System.out.print(defaultImage);
                    defaultImg = scanner.nextLine();
                    System.out.print(startNumber);
                    start = scanner.nextInt();
                    System.out.print(endNumber);
                    end = scanner.nextInt();

                    // ask the manager
                    MetadataManager.insertNewImages(webtoon, author, chapterNo, chapter,
                            defaultImg, start, end);
                    break;

                default:
                    keepGoing = false;
            }
        }
    }
}
