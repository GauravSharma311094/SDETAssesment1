package common;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Utils {

    public static RequestSpecification req;
    EnvironmentVariables variables= SystemEnvironmentVariables.createEnvironmentVariables();
    String baseURL= EnvironmentSpecificConfiguration.from(variables).getProperty("baseURL");

    public RequestSpecification requestSpecification() throws IOException {
        if(req==null){
            PrintStream log=new PrintStream(new FileOutputStream("logging.txt"));
            req=new RequestSpecBuilder().setBaseUri(baseURL).setRelaxedHTTPSValidation().
                    setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public String getJsonPath(Response response,String key) {
        String resp=response.asString();
        JsonPath js=new JsonPath(resp);
        return js.get(key).toString();
    }

    public String getCurrencyPriceJsonPath(Response response,String key) {
        String resp=response.asString();
        JsonPath js=new JsonPath(resp);
        HashMap<String,Float> rates=js.get("rates");
        return String.valueOf(rates.get(key));
    }

    public int getCurrencyPairCount(Response response) {
        String resp=response.asString();
        JsonPath js=new JsonPath(resp);
        HashMap<String,Float> rates=js.get("rates");
        return rates.size();
    }

    public void getAssertJSONSchema(Response response,String jsonFilePath) {
        System.out.println(jsonFilePath);
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonFilePath));
    }
}
