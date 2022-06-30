package pro.fengjian;

import java.io.InputStream;

public class Resources {

    public static InputStream getResourcesAsStream(String path){

        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
