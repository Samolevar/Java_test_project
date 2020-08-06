These bugs were found during testing:

1. Limit of recors more than in descriptionВ хранилище, лимит хранимых записей больше указанного в описании:
  - Scenario:
    - Send 10 post requests (for create 10 triangles)
    - Send one more post request
  - Actual result:
    - ResponceCode = 200
    - Record added
    - Get all request returns 11 records
  - Expected result:
    - ResponceCode = not 200 (limit expired)
    - Record not added
    - Get all request returns 10 records
   
  **Comment**: 
   The publicly available API statement specifies the maximum number of records to store - 10. The current implementation allows storing more. A good decision would be to match these values:
     - If the number 10 is important, then edit the code
     - If it is possible to store more, then amend the documentation.
     All changes can be discussed with the analyst in advance.
    
2. Delete request witn nonexistent id returns success (Code = 200)
  - Scenario:
    - There are no records in app/ There are some records in app
    - Send Delete request with nonexistent id
  - Actual result:
    - ResponceCode = 200
    - Response body - `1`
  - Expected result:
    - ResponceCode = 404 (or other suitable not founded)
    
  **Comment**:
  User must know that deleting was successful, and if something went wrong API must return reliable information
  
3. Special symbols not available as a separator in post request
 - Scenario:
    - Send Post request. Separator is $
 - Actual result:
    - StatusCode = 422 
    - Record not added
 - Expected result:
    - StatusCode = 200
    - Record added
    
 **Comment**:
   Other case for separator - *, returns other error"java.util.regex.PatternSyntaxException", with point to "Dangling meta character '*' near index 0\n*\n^".
   If it is not specified anywhere which characters cannot be sent, then special ones should be accepted as well.
   
   4. In Post request user can send digit as a separator
  - Scenario:
    - Send Post request, the separator is a digit, and it is not equal to any digit in the input
  - Actual result:
    - StatusCode = 200 
    - Record added
  - Expected result:
    - StatusCode = 422
    - Record not added
    
  **Comment**:
   This is an available situation when digit as a separator can match one of the transmitted digits. 
   It turns out that the same separator works just sometimes - this is a bad pattern of application behavio
   
   5. The application saves record if one of the sizes is zero
  - Scenario:
    - Send Post request, one of the values in inpit is zero (1;1;0)
  - Actual result:
    - StatusCode = 200 
    - Record added
  - Expected result:
    - StatusCode = 422
    - Record not added
    
   **Comment**:
   A figure with values 1; 1; 0 is validated according to the used triangle rule (the sum of any two sides is strictly greater than the third), while being, in fact, an angle.
   
  6. User can send Post request with a figure with any amount of sides (more than 3)
  - Scenario:
    - Send Post request with not triangle figure (3;4;5;6)
  - Actual result:
    - StatusCode = 200
    - Record added
    - For record creation application uses the first 3 values
  - Expected result:
    - StatusCode = 422
    - Record not added
    
   **Comment**:
   Unobvious application work. The user should be aware that he cannot create a shape other than a triangle.
