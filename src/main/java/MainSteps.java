import org.jbehave.core.annotations.*;

public class MainSteps {
    @Given("User is testing CheckText")
    public void aCounter() {
        System.out.println("CheckText");
    }
    @Given("Text value is $text")
    public void setText(String text) {
        System.out.println(text);
    }
    @Given("User wants to ignore digits")
    public void ignoreDigits() {
        System.out.println("no digits");
    }
    @Given("User wants to ignore urls")
    public void ignoreUrls() {
        System.out.println("no urls");
    }
    @Given("User wants to ignore capitalization")
    public void ignoreCapitalization() {
        System.out.println("don't care about capitalization");
    }
    @Given("User wants to find repetitions")
    public void findRrpetitions() {
        System.out.println("find repetitions");
    }


    @When("API is executed")
    public void increasesTheCounter() {
        System.out.println("API executed");
    }

    @Then("There are $errorCount errors")
    public void theValueOfTheCounterMustBe1Greater(Integer errorCount) {
        System.out.println("errors: " + errorCount);
    }
}
