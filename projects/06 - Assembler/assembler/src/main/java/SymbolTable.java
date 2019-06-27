import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, Integer> map;
    ObjectMapper mapper = new ObjectMapper();

    public SymbolTable() {
        try {
            map = mapper.readValue(Code.class.getResourceAsStream("symbol.json"), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEntry(String symbol, int address){
        map.put(symbol, address);
    }

    public boolean contains(String symbol){
        return map.containsKey(symbol);
    }

    public int getAddress(String symbol){
        return map.get(symbol);
    }
}
