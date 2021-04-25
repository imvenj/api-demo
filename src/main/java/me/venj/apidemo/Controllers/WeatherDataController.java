package me.venj.apidemo.Controllers;

import me.venj.apidemo.Entities.WeatherData;
import me.venj.apidemo.Repositories.WeatherDataRepository;
import me.venj.apidemo.Utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/")
public class WeatherDataController {

    @Autowired
    private final WeatherDataRepository weatherDataRepository;

    public WeatherDataController(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @GetMapping("send")
    WeatherData sendData(String data) {
        String[] parts = data.split(",");
        if (parts.length != 5) {
            return null;
        }
        WeatherData weatherData = new WeatherData();
        weatherData.setTemperature(Double.parseDouble(parts[0]));
        weatherData.setPressure(Double.parseDouble(parts[1]));
        weatherData.setHumidity(Double.parseDouble(parts[2]));
        weatherData.setAltitude(Integer.parseInt(parts[3]));
        weatherData.setLuminocity(Double.parseDouble(parts[4]));
        long timestamp = System.currentTimeMillis();
        weatherData.setTimestamp(timestamp);
        weatherDataRepository.save(weatherData);
        return weatherData;
    }

    @GetMapping("all")
    Iterable<WeatherData> getWeatherData() {
        return weatherDataRepository.findAll();
    }

    @GetMapping("current")
    Optional<WeatherData> getCurrentWeatherData() {
        return weatherDataRepository.findLastWeatherData();
    }

    @GetMapping("{id}")
    Optional<WeatherData> getWeatherDatum(@PathVariable Long id) {
        return weatherDataRepository.findById(id);
    }

    @GetMapping("lastDay")
    Iterable<WeatherData> getLastDayWeatherData() {
        long end = System.currentTimeMillis();
        long begin = end - 86400000;
        return weatherDataRepository.findWeatherDataInRange(begin, end);
    }

    @GetMapping("today")
    Iterable<WeatherData> getTodayWeatherData() {
        long begin = APIUtils.timestampForToday();
        long end = begin + 86400000;
        return weatherDataRepository.findWeatherDataInRange(begin, end);
    }

    @GetMapping("yesterday")
    Iterable<WeatherData> getYesterdayWeatherData() {
        long end = APIUtils.timestampForToday();
        long begin = end - 86400000;
        return weatherDataRepository.findWeatherDataInRange(begin, end);
    }
}
