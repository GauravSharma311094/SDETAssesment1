Feature: Currency API Validation

  Scenario: Verify currency API call is successful
    Given CurrencyAPI
    When user calls "getCurrencyAPI" with "GET" http request
    Then the API call got success with status code "200"
    And "result" in response body is "success"


  Scenario Outline: Verify USD currency price against AED currency
    Given CurrencyAPI
    When user calls "getCurrencyAPI" with "GET" http request
    Then the API call got success with status code "200"
    And verify USD currency price against "<currency>" range from <currencyMinValue> to <currencyMaxValue>

    Examples:
      | currency | currencyMinValue | currencyMaxValue |
      | AED      | 3.6              | 3.7              |

  Scenario: Verify currency API response time is greater than 3 seconds
    Given CurrencyAPI
    When user calls "getCurrencyAPI" with "GET" http request
    Then the API call got success with status code "200"
    And verify currency API response time is less than 3 seconds


  Scenario: Verify 162 currency pair returned by currencyAPI
    Given CurrencyAPI
    When user calls "getCurrencyAPI" with "GET" http request
    Then the API call got success with status code "200"
    And verify 162 currency pair returned by currencyAPI

  Scenario Outline: Verify currencyAPI response matches the json schema
    Given CurrencyAPI
    When user calls "getCurrencyAPI" with "GET" http request
    Then the API call got success with status code "200"
    And verify currencyAPI response matches the "<jsonSchemaFile>"

    Examples:
      | jsonSchemaFile         |
      | currencyAPISchema.json |