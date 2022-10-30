package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QueryStringTest {

    @Test
    void createTest() {
        QueryString queryString = new QueryString("operan1", "11");
        assertThat(queryString).isNotNull();
    }
}