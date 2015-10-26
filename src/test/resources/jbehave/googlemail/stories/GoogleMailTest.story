Narrative:
In order to become guru in Selenium and BDD
As a automation engineer
I want to implement few BDD scenarios to train myself

Scenario: User attempts to login with invalid credentials
Given Gmail Login page is opened
When User attempts to login with invalid email <email>
Then <error_message> error message should appear

Examples:
|email|error_message|
||Please enter your email.|
|@%|Please enter a valid email address.|

Scenario: User login with valid credentials
Given Gmail Login page is opened
When User enter valid email and press Next button
And User enter valid password and press Sign In button
Then Gmail Home page is opened

Scenario: User compose an email
Given Gmail Home page is opened
When User clicks Compose button on Home page
Then New Email window is opened
When User type gadzilla.test@gmail.com in To field
And User type Test in Subject field
And User type Test in Email body field
And User clicks Send button
Then email with Test subject is send to gadzilla.test@gmail.com