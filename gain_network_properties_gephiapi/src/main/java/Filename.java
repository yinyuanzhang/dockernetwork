import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Filename {


    public static void main(String[] args) {



        Filename fileclass = new Filename();
        fileclass.run();
    }


        public List<String> run(){
            List<String> filenamelist = new ArrayList<String>();
            File file = new File("A:\\pythonproject\\dversion6");
            File[] subFile = file.listFiles();
            System.out.println(subFile);
            for (int i = 0; i < subFile.length; i++) {
                String filename = subFile[i].getName().split("\\.")[0];
                filenamelist.add(filename);
                System.out.println(filename);
            }
            return filenamelist;
        }

}
