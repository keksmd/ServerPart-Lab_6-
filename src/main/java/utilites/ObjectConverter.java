package utilites;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ObjectConverter {


    public static <T> String toJson(T o) {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));

        try {
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ArrayList<T> readAndUpdate(String fileName, TypeReference<T> typeReference) {

        ArrayList<T> data = new ArrayList<>();
        try (FileReader fis = new FileReader(fileName);
             BufferedReader bfr = new BufferedReader(fis)) {
            String line;
            while ((line = bfr.readLine()) != null) {
                data.add(deserialize(line, typeReference));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;

    }

    public static <T> T deserialize(String line, TypeReference<T> typeReference) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readerFor(typeReference).readValue(line);
    }
}
