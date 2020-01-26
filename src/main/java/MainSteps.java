import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jbehave.core.annotations.*;
import org.junit.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainSteps {
    //private final String FORMAT_PARAMETER = "format";
    private ObjectMapper objectMapper = new ObjectMapper();

    private HashMap<String, Object> queryParam = new HashMap<String, Object>();

    private List<ResponseBody> response;

    @Given("User is testing CheckText")
    public void urlSetup() {
        queryParam = new HashMap<String, Object>();
        RestAssured.baseURI = "http://speller.yandex.net/services/spellservice.json/checkText";
    }

    @Given("List of languages is $langList")
    public void setLang(String lang) {
        String LANG_PARAMETER = "lang";
        queryParam.put(LANG_PARAMETER, lang);
    }

    @Given("User wants to ignore digits")
    public void ignoreDigits() {
        Integer IGNORE_DIGITS = 2;
        setParams(IGNORE_DIGITS);
    }

    @Given("User wants to ignore urls")
    public void ignoreUrls() {
        Integer IGNORE_URLS = 4;
        setParams(IGNORE_URLS);
    }

    @Given("User wants to ignore capitalization")
    public void ignoreCapitalization() {
        Integer IGNORE_CAPITALIZATION = 512;
        setParams(IGNORE_CAPITALIZATION);
    }

    @Given("User wants to find repetitions")
    public void findRepetitions() {
        Integer FIND_REPEAT_WORDS = 8;
        setParams(FIND_REPEAT_WORDS);
    }

    @When("User checks text '$text'")
    public void increasesTheCounter(String text) throws JsonProcessingException {
        String TEXT_PARAMETER = "text";
        queryParam.put(TEXT_PARAMETER, text);
        System.out.println(queryParam.toString());
        Response rawResponse = RestAssured.given().queryParams(queryParam).when().get().then().statusCode(200).contentType(ContentType.JSON).extract().response();
        response = Arrays.asList(objectMapper.readValue(rawResponse.asString(), ResponseBody[].class));
        rawResponse.prettyPrint();
    }

    @Then("There are $errorCount mistakes")
    public void checkAmountOfErrors(int errorCount) {
        Assert.assertEquals(errorCount, response.size());
    }

    @Then("Next words have mistakes $words")
    public void failedWords(List<String> words) {
        for (ResponseBody spellResult : response) {
            if (words.contains(spellResult.word)) {
                words.remove(spellResult.word);
            }
        }
        Assert.assertTrue("Next words bypassed validation: " + words.toString(), words.isEmpty());
    }

    @Then("For word '$mistake' CheckText proposes next fix '$fix'")
    public void checkProposedFix(String mistake, String fix) {
        for (ResponseBody spellResult : response) {
            if (spellResult.word.equalsIgnoreCase(mistake)) {
                Assert.assertTrue(spellResult.s.contains(fix));
            }
        }
    }

    @Then("Word '$mistake' is unknown")
    public void checkUnknownWordMistake(String mistake) {
        checkErrorCode(mistake, 1);
    }

    @Then("Word '$mistake' is repeated more than 1 time")
    public void checkRepeatedWordMistake(String mistake) {
        checkErrorCode(mistake, 2);
    }

    @Then("Word '$mistake' has incorrect capitalization")
    public void checkCapitalizationMistake(String mistake) {
        checkErrorCode(mistake, 3);
    }

    @Then("Word '$mistake' has several mistakes")
    public void checkTooManyMistakesMistake(String mistake) {
        checkErrorCode(mistake, 4);
    }

    private void checkErrorCode(String mistake, Integer errorCode) {
        Boolean found = false;
        for (ResponseBody spellResult : response) {
            if (spellResult.word.equalsIgnoreCase(mistake) && (spellResult.code.equals(errorCode))) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    private void setParams(Integer parameterValue) {
        String OPTIONS_PARAMETER = "options";
        if (!queryParam.containsKey(OPTIONS_PARAMETER)) {
            queryParam.put(OPTIONS_PARAMETER, parameterValue);
        } else {
            queryParam.put(OPTIONS_PARAMETER, (Integer) queryParam.get(OPTIONS_PARAMETER) + parameterValue);
        }
    }

}
