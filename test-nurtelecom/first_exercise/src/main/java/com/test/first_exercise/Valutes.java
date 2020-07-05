package com.test.first_exercise;

import lombok.Data;

@Data
public class Valutes {
    Valute Valute;
    @Data
    class Valute{
        USD USD;
        EUR EUR;
        KGS KGS;

        @Data
        class USD {
            Float Value;
        }

        @Data
        class EUR {
            Float Value;
        }

        @Data
        class KGS {
            Float Value;
        }
    }
}
