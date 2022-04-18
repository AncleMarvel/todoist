package com.example.todo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        mapper.getConfiguration()
//                Строгий мэтчинг полей в ентити и ДТО
                .setMatchingStrategy(MatchingStrategies.STRICT)
//                Разрешение мэппинга полей
                .setFieldMatchingEnabled(true)
//                Скип наловые поля
                .setSkipNullEnabled(true)
//                Можно из исходного класса доставать прайват поля
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

}
