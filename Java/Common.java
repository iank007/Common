package Java;

public class Common {

  public static boolean isFileExists(String path) {
    try {
      File file = new File(path);
      return file.exists() && !file.isDirectory();
    } catch(Exception e) { }
    return false;
  }
  
}
