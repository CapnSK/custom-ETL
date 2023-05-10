package com.dwm.a2.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.dwm.a2.Model.Article;
import com.dwm.a2.Utils.Constants;

public class PersistenceService {
    private static LogService logger = LogService.getLogger(Queue.class);

    private static String ROOT_DIR = System.getProperty("user.dir");
    private static String RESOURCE_DIR = "/app/src/main/resources";
    private static String DATA_STORE = "/data";
    private static final String FILE_PARTS_DIR = ROOT_DIR+RESOURCE_DIR+DATA_STORE+"/";
    private static File PARENT_DIR;

    static{
        File file = new File(FILE_PARTS_DIR);
        Boolean created = false;
        if(!file.exists()){
            created = file.mkdirs();
        }
        if(created || file.exists()){
            PARENT_DIR = file;
        }
    }

    public static Boolean save(Integer fileNo, List<Article> articles){
        Boolean fileSaved = false;
        if(PARENT_DIR != null && PARENT_DIR.exists() && PARENT_DIR.isDirectory()){
            String fileName = "PART_"+fileNo;
            String filePath = PARENT_DIR.getAbsolutePath()+"/"+fileName;
            File fileToPersist = new File(filePath);

            Boolean fileCreated = false;
            try {
                fileCreated = fileToPersist.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if((fileCreated || fileToPersist.exists()) && articles!=null && !articles.isEmpty()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToPersist,false))) {
                    fileSaved = articles.stream().filter(Objects::nonNull).map(article->{
                        try {
                            writer.append(article.getTopic());
                            writer.append(Constants.FILE_DELIMETER);
                            writer.append(article.getTitle());
                            writer.append(Constants.FILE_DELIMETER);
                            writer.append(article.getContent());
                            writer.append(Constants.FILE_NEWLINE_DELIMETER);
                        } catch (IOException e) {
                            logger.log(Arrays.toString(e.getStackTrace()));
                            return false;
                        }
                        return true;
                    }).allMatch(success->success);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    fileSaved = false;
                }
            }
        }
        return fileSaved;
    }

    public static List<List<String>> getSaved(){
        List<List<String>> articles = new ArrayList<>();
        if(PARENT_DIR != null && PARENT_DIR.exists() && PARENT_DIR.isDirectory()){
            Arrays.asList(PARENT_DIR.listFiles()).stream()
                                            .filter(file->file.canRead())
                                            .map(PersistenceService::readFileContents)
                                            .forEach(fileContent->{
                                                List<String> topics = Arrays.asList(fileContent.split(Constants.FILE_NEWLINE_DELIMETER_REGEX, -1));
                                                topics.stream().forEach(line->{
                                                    List<String> tokens = Arrays.asList(line.split(Constants.FILE_DELIMETER_REGEX, -1));
                                                    if(tokens.size() >= 3){
                                                        articles.add(tokens);
                                                    }
                                                });
                                            });
        }
        return articles;
    }

    private static String readFileContents(File file){
        StringBuilder contents = new StringBuilder();
        if(file != null && file.exists() && file.isFile() && file.canRead()){
            try (Scanner fileReader = new Scanner(file)) {
                while(fileReader.hasNextLine()){
                    contents.append(fileReader.nextLine());
                }
            } catch (FileNotFoundException e) {
                logger.log(Arrays.toString(e.getStackTrace()));
            }
        }
        return contents.toString();
    }
}
