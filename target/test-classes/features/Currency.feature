Feature: Currency API Validation

  Scenario: Verify currency API call is successful and return valid price
    Given CurrencyAPI
    When user calls "getCurrencyAPI" with "GET" http request
    Then the API call got success with status code 200
    And "result" in response body is "success"