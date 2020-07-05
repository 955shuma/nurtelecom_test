package com.test.first_exercise;

import lombok.Data;

@Data
public class Weather {
    String WeatherText;
    Temperature Temperature;

    @Data
    class Temperature{
        Metric Metric;

        @Data
        class Metric{
            Float Value;
        }
    }
}
