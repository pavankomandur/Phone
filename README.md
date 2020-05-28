# Phone
This is an Phone Validation API which validates the Phone Number given and gives suggestions based on the Phone Number entered.

Features

1.  Validates the Phone Number( it will accept only either 7 digit or 10 digit number)
2.  if the Phone Number is 7 digit (Assuming without Area Code). It will add all the area codes to the given Number and displays as suggestions.
3.  if the Phone Number is 10 digit, it will convert the last digit into character as per tele keypad and displays the suggestions.
4.  written United Test Cases for Service Class where 90% of the logic is present. Due to Time Limitation, I could not able to write for controller.
5.  Logs are implemented. Error Handling is implemented.

Tech Stack
  1.  Spring Boot.
  
     Post Url : http://localhost:8081/phonebook/getSuggestions
     Parameters : {
                  "phoneNumber" : "3140288",
                  "pageNo" : "1"
                  }
                  
                  
