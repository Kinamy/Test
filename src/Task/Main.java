package Task;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        FileReader file = new FileReader("flights.json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonParse jsonParse = objectMapper.readValue(file, JsonParse.class); {
                List<Flight> flightsFromMSKtoKHV = jsonParse.getFlights().stream()
                        .filter(flight -> (flight.getFromCity().equals("Москва") && flight.getToCity().equals("Хабаровск")))
                        .collect(Collectors.toList());

                int maxPrice = flightsFromMSKtoKHV
                        .stream()
                        .mapToInt(Flight::getPrice)
                        .max().orElseThrow(NoSuchElementException::new);

                double averagePrice = flightsFromMSKtoKHV
                        .stream()
                        .mapToDouble(Flight::getPrice)
                        .sum() / flightsFromMSKtoKHV.size();

                int minPrice = flightsFromMSKtoKHV
                        .stream()
                        .mapToInt(Flight::getPrice)
                        .min().orElseThrow(NoSuchElementException::new);

                System.out.println("Минимальная стоимость перелета: " + minPrice);
                System.out.println("Средняя стоимость перелета: " + averagePrice);
                System.out.println("Максимальная стоимость перелета: " + maxPrice);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
