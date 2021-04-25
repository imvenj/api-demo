package me.venj.apidemo.Repositories;

import me.venj.apidemo.Entities.WeatherData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = WeatherData.class, idClass = Long.class)
public interface WeatherDataRepository extends CrudRepository<WeatherData, Long> {
    @Query("select w from weather_data w where id = (select max(ww.id) from weather_data ww)")
    Optional<WeatherData> findLastWeatherData();

    @Query("select w from weather_data w where w.timestamp > :begin and w.timestamp < :end")
    Iterable<WeatherData> findWeatherDataInRange(long begin, long end);
}
