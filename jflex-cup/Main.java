import java.io.*;
   
public class Main {

  static public void main(String argv[]) {

    try {

      parser p = new parser(new Scanner(new FileReader(argv[0])));
      Object result = p.parse().value;      

    } catch (Exception e) {

      e.printStackTrace();
      
    }

  }
}


