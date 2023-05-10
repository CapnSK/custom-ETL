package com.dwm.a2.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
    public static final String REMOTE_URL = "https://newsapi.org/v2/top-headlines";
    public static final String API_KEY = "3fd6e4e1279440dabb57256c1e99621a";
    public static final String API_KEY_ALTERNATE = "20f8235946c34db4ae03ec0dfcce517a";
    public static final String API_KEY_STR = "apiKey";
    public static final String QUERY_KEY = "q";
    public static final String FILE_DELIMETER = "_$_";
    public static final String FILE_DELIMETER_REGEX = "_\\$_";
    public static final String FILE_NEWLINE_DELIMETER = "_$$_";
    public static final String FILE_NEWLINE_DELIMETER_REGEX = "_\\$\\$_";
    private static String[] l = {"Canada","University","Dalhousie","Halifax","Canada Education","Moncton","hockey","Fredericton","celebration"};
    public static final List<String> KEYWORDS = Collections.unmodifiableList(Arrays.asList(l));
    public static final String DB_NAME = "NEWS_ARTICLES";


    public static final String JSON_PARSE_REGEX = "(?:\"|')(?<key>[\\w\\d]+)(?:\"|')(?:\\:\\s*)(?:\"|')?(?<value>[\\w\\s-]*)(?:\"|')?";

    public static final String SPECIAL_CHARACTER_REGEX = "[!@#\\$%\\^&\\*()_+\\-={}\\[\\]\\|\\\\:;\"'<>,.?/]";
    public static final String EMOTICONS_REGEX = "[\uD83D\uDE00-\uD83D\uDE4F\uD83C\uDF00-\uD83C\uDFFF\uD83D\uDE80-\uD83D\uDEFF\uD83C\uDDF0-\uD83C\uDDFF]";
    public static final String URL_REGEX = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))*(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";

    public static final String MONGO_CONNECTION_STRING = "mongodb+srv://kulkarnisankalp21:SKsk%4012_@cluster0.h9todqn.mongodb.net/?retryWrites=true&w=majority";


    public static final Integer PAGE_SIZE = 5;
}
