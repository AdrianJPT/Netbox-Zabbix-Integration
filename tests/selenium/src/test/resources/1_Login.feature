Feature: Login to Tramitator

  Scenario: Login to Tramitator
    Given I am in the login page of Tramitator
    When enter a correct username and password
    Then I should see the main page


