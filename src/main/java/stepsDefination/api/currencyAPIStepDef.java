package stepsDefination.api;
import common.APIResources;
import common.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class currencyAPIStepDef extends Utils {

    RequestSpecification res;
    ResponseSpecification respSec;
    Response response;

    @Given("CurrencyAPI")
    public void currency_API() throws IOException {
        res=given().spec(requestSpecification());
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResources resourceAPI=APIResources.valueOf(resource);
        respSec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if(method.equalsIgnoreCase("GET"))
            response=res.when().get(resourceAPI.getResource());
        else if(method.equalsIgnoreCase("POST"))
            response=res.when().get(resourceAPI.getResource());
    }

    @Then("the API call got success with status code {string}")
    public void the_api_call_got_success_with_status_code(String statusCode){
        Assert.assertEquals(String.valueOf(response.getStatusCode()),statusCode);
    }

    @And("{string} in response body is {string}")
    public void result_in_response_body(String keyValue,String expectedValue){
        Assert.assertEquals(getJsonPath(response,keyValue),expectedValue);
    }

    @And("verify USD currency price against {string} range from {double} to {double}")
    public void assert_USD_currency_price(String keyValue,Double currencyMinValue,Double currencyMaxValue){
        Double actualValue= Double.valueOf(getCurrencyPriceJsonPath(response,keyValue));
        assertThat(actualValue,allOf(greaterThanOrEqualTo(currencyMinValue),lessThanOrEqualTo(currencyMaxValue)));
    }

    @And("verify currency API response time is less than 3 seconds")
    public void assert_currency_API_response_time(){
        Assert.assertTrue(response.getTimeIn(TimeUnit.SECONDS)<3);
    }

    @And("verify {int} currency pair returned by currencyAPI")
    public void assert_currency_pair(int expectedPairCount){
        int actualPairCount=getCurrencyPairCount(response);
        Assert.assertEquals(actualPairCount,expectedPairCount);
    }

    @And("verify currencyAPI response matches the {string}")
    public void assert_currency_json_schema(String jsonFilePath){
        getAssertJSONSchema(response,jsonFilePath);
    }
}
