package com.app.weather.controller;

import com.app.weather.models.WeatherRequest;
import com.app.weather.models.WeatherSummary;
import com.app.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
@Import(WeatherControllerTest.MockConfig.class)
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherService weatherService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public WeatherService weatherService() {
            return Mockito.mock(WeatherService.class);
        }
    }

    @Test
    void getWeather_validRequest_returnsOk() throws Exception {
        Mockito.when(weatherService.getWeatherData(any(WeatherRequest.class))).thenReturn(new WeatherSummary());
        String json = "{\"latitude\":10.0,\"longitude\":20.0}";
        mockMvc.perform(post("/get-weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void getWeather_invalidRequest_returnsBadRequest() throws Exception {
        String json = "{\"latitude\":0,\"longitude\":0}";
        mockMvc.perform(post("/get-weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
