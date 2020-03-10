# Cars Web Dev Test Solution

## To run follow below steps


1. `docker build -t test .`
2. `docker rm --force test-run`
2. `docker run -d -it -p 80:8080 --name=test-run test`
3. Use swagger to test:
   http://localhost:80/swagger-ui.html   
   
  ##### Note: change port 80 in lines 2 and 3 if need be
  ##### Note: The docker image is based on an opensource image and is not secure enough to be used in production.
  
  

   

