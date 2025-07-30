package com.MicroServices.JobApp.Configrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // ✅ Used for mapping DTOs ↔ Entities (very useful in layered architecture)
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // ✅ Global ObjectMapper configuration for JSON serialization/deserialization
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 🔁 Support for new Java 8+ date/time types (LocalDate, LocalDateTime, etc.)
        mapper.registerModule(new JavaTimeModule());

        // ✅ Dates as ISO-8601 strings, not timestamps (e.g., "2025-04-25T10:00:00" instead of 1714041600000)
        // Example:
        // - true (timestamp): "birthDate": 1714041600000
        // - false (ISO):      "birthDate": "2025-04-25T10:00:00"
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // ✅ Prevents errors when trying to serialize an empty class (DTOs without fields)
        // - Example: class Empty {} → no error
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // ✅ Pretty-print JSON output (for debugging/logging, usually disabled in production)
        // - Example:
        // {w
        //     "name": "Om",
        //     "role": "Developer"
        // }
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
