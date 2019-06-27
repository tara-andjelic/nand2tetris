import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;

public class Code {

    private static ObjectMapper mapper = new ObjectMapper();
    private static HashMap<String, String> destMap;
    private static HashMap<String, String> compMap;
    private static HashMap<String, String> jumpMap;
    static {
        try {
            destMap = mapper.readValue(Code.class.getResourceAsStream("dest.json"), HashMap.class);
            compMap = mapper.readValue(Code.class.getResourceAsStream("comp.json"), HashMap.class);
            jumpMap = mapper.readValue(Code.class.getResourceAsStream("jump.json"), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String dest(String mnemonic) {
        return destMap.get(mnemonic);
    }

    public static String comp(String mnemonic) {
        return compMap.get(mnemonic);
    }

    public static String jump(String mnemonic) {
        return jumpMap.get(mnemonic);
    }

}
