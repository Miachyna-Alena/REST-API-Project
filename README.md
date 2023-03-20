## REST API (GET/POST) Project  
  
API: [JSONPlaceholder](https://jsonplaceholder.typicode.com)  
  
| **№** | **Step** | **Expected result** |  
|------------------------------------------------------|------------------------------------------------------|-----------------------------------------------------|  
| 1. | Send GET request to get all posts (/posts). | Status code is 200. The list in response body is json. Posts are sorted by id ascending.|  
| 2. | Send GET request to get post with id = 99 (/posts/99). | Status code is 200. Post information is correct: userId = 10, id = 99, title and body aren't empty.|  
| 3. | Send GET request to get post with id = 150 (/posts/150). | Status code is 404. Response body is empty |  
| 4. | Send POST request to create post with userId = 1, random body and random title (/posts). | Status code is 201. Post information is correct: title, body, userId match data from request, id is present in response. |  
| 5. | Send GET request to get all users (/users).| Status code is 200. The list in response body is json. The data of the user with id = 5 is equals to: ![image](https://user-images.githubusercontent.com/108085021/226320525-e72f131e-0462-4205-8223-15c65fc17cae.png) |  
| 6. | Send GET request to get user with id = 5 (/users/5). | Status code is 200. User data matches with user data in the previous step. |  
