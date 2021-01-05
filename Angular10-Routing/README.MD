About this project:
I have developed this sample web application using below technologies:

Angular 10.0.5

Node: 12.18.3

NPM 6.13.1

This project has Frontend which I developed using Angular 10.0.5 also this project has a backend service which I developed using Node: 12.18.3. Angular Frontend calls the NodeJs backend service.


Angular also offers modular way of developing web apps. 

We can design components to be in different modules and each module contains related components.

When configured, the modules can be lazily loaded to optimize for the performance instead of loading all unrelated modules all at once.

I have developed this web app with modules which are lazy loaded as per Angular Routing. 

I have also created a CoreModule which contains the common classes used by all the modules in this web app.

I have also used third party Plotly library to plot the time-series graph showing commodity prices.

I have used intuitive naming for classes and variables so its easy to understand the code.


To run this app please follow:

1. Download and install NodeJS

2. Install Angular from terminal command prompt:

   npm install -g @angular/cli@latest
   
   After this install typescript:
   
   npm install -g typescript
   
3. Download this project.

4. Open new command terminal. 

5. In new Terminal go to commodities-service directory of this project where you downloaded it and run:

   npm i

6. Then start the backend commodities-service using:

   npm start
   
   The backend service runs on port 3000 (make sure its not used by any other app.) 

7. Open new command terminal.

8. In new Terminal  go to commodities-exchange directory of this project where you downloaded it and run:

   npm i
   
9. Build the Angular frontend webapp by running below on command prompt:

   ng build   
   
   
10. Then start the Angular frontend webapp by running below on command prompt:

    ng serve
   
   By default the Angular web app runs on port 4200 (make sure its not used by any other app)
   
   Then open chrome and goto:
   
   http://localhost:4200/
   

https://www.youracclaim.com/users/arif-shaikh/badges
