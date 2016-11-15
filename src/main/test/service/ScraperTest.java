package service;

import domain.Product;
import domain.Result;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.google.common.collect.Lists.newArrayList;
import static service.Scraper.printProduct;

@RunWith(MockitoJUnitRunner.class)
public class ScraperTest {
    private final ByteArrayOutputStream printOutContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(printOutContent));
    }

    @After
    public void cleanUp() {
        System.setOut(null);
    }

    @Mock
    private PageScrapperHelper helper;

    @Test
    public void shouldPrintOutResult() throws JSONException {
        // Given
        Result result = new Result();
        Product product = new Product();
        product.setTitle("This is a test");
        product.setDescription("To make sure the correct json comes through");
        product.setSize("15");
        product.setUnit_price("£15");
        result.setTotal("£15");
        result.setResults(newArrayList(product));

        // When
        printProduct(result);

        //Then
        JSONAssert.assertEquals( printOutContent.toString(), "{\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"title\": \"This is a test\",\n" +
                "      \"size\": \"15\",\n" +
                "      \"unit_price\": \"£15\",\n" +
                "      \"description\": \"To make sure the correct json comes through\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": \"£15\"\n" +
                "}\n", false);
    }
}