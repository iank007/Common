package Java;

public class Common {

  // file 1
  public static boolean isFileExists(String path) {
    try {
      java.io.File file = new File(path);
      return file.exists() && !file.isDirectory();
    } catch(Exception e) { }
    return false;
  }

  // thread 1
  public static void thread1() {
    thread1impl("hoho");
  }

  private static void thread1impl(String param1) {
    new Thread(new Runnable() {
      @Override
      public void run(){
        System.out.println(param1);
      }
    }).start();
  }

  // quotes
  public static String quotes(String str) {
    String result = null;
    try {
        str = str.replace("\\", "\\\\");
        str = str.replace("\"", "\\\"");
        result = "\"" + str + "\"";         // surround by  quotes
    } catch(Exception e) {
    }
    return result;
  }

  // escape
  public static String escape(String str) {
    try {
        str = str.replace("\\", "\\\\");
        str = str.replace("\"", "\\\"");
    } catch(Exception e) {
        logger.error(e);
    }
    return str;
  }

  // list to string with [ ]
  public static String listToString(List<String> list) {
    String result = "[]";
    try {
        String tmp = "";
        for(String e : list) {
            if(tmp.isEmpty()) {
                tmp = quotes(e);
            } else {
                tmp += ", " + quotes(e);
            }
        }
        result = "[" + tmp + "]";
    } catch(Exception e) {
        logger.error("listToString: " + e);
    }
    return result;
  }

  // call config.properties
  public static String prop(String key) {
    String result = null;
    try {
      java.io.InputStream is = Common.class.getClassLoader().getResourceAsStream("config.properties");
      java.util.Properties prop = new Properties();
      prop.load(is);
      result = prop.getProperty("name"); // expected result: januar
      System.out.println(prop.getProperty("age")); //expected result: 26
    }catch(Exception e) { }
    return result;
  }

  public static void getResponse() {
    String token = "secret_key";
    org.json.JSONObject obj = new JSONObject("{}"); // param body
    java.net.URL url = new URL("http://localhost:8080/status");
    java.net.HttpUrlConnection conn = (HttpUrlConnection) url.openConnection();
    // headers
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    conn.setRequestProperty("Accept", "application/json");
    conn.setRequestProperty("Authorization", "Bearer " + token); // if needed

    conn.setDoOutput(true); // ?
    conn.setDoInput(true); // ?
    // bodies
    java.io.OutputStream os = conn.getOutputStream();
    os.write(obj.toString().getBytes("UTF-8"));
    os.close();

    // response
    if(200 == conn.getResponseCode()) {
      java.io.BufferedReader br = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
      String output;
      StringBuilder sb = new StringBuilder();
      while((output = br.readLine()) != null) {
          sb.append(output + "\n");
      }
      br.close();
      JSONObject jo = new JSONObject(sb.toString());
      System.out.println(jo);

    }
  }
}
