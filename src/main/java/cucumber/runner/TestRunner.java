package cucumber.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features="src/test/resources/features",
        glue={"stepsDefinition.api"},
        plugin={"pretty","json:target/jsonReports/cucumber-report.json"},
        tags="@Test",
        monochrome=true
)
public class TestRunner {
}
