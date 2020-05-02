About this project:
I have developed this sample web application for selling cars using below technologies:

Core Java8
JEE - EJB, JPA, JSP/Servlets, JAX-RS
HTML, JQuery
Amazon AWS - EC2, Cognito/OAuth, S3, SNS(Simple Notification Service), Lambda (Serverless Compute), SES (Simple Email Service), RDS-MySQL, AWS-Java-SDK
OAuth, JOSE, JWT (JSON Web Token), Maven, Linux, Glassfish

I started on this project in start of 2020 and I took around 6 weeks on and off to develop this project.

I have deployed this application on AWS accessible at below link :
Below link use adhoc certificate so to visit it you will have to allow bellow site in your browser. If you are using chrome and when chrome shows warning after you click below link then click "Advanced" link on chrome warning page and then click "Proceed to ec2..." link.

https://ec2-54-218-76-189.us-west-2.compute.amazonaws.com:9191/aws-web-app8-web/

You can visit the above link without login as well but in that case you wont be able to post your cars for selling but you can browse and search cars already posted. 

To post your car for selling you need to login. Upon first signup you will be asked to enter your email address, nickname and password. Your will receive an email on your provided email address to verify your email account, after verifying only you will be able to login. When you are on login popup please press signup if you are new user. You can press resend button on signup page if verify link expires. 

After login you can post cars for selling, upload car photos, update, delete your cars etc. When you add/update/delete your car details you will get an email notifying the action taken and containing the link of the car (if not deleted).

If you want to deploy this application in your own environment then you will have to setup below in your own AWS account:
AWS EC2 (You would need to setup Java8 and Maven & JEE Server like Glassfish on top of your AWS EC2)
AWS Cognito User Pool
AWS MySQL RDS (you need to change the persistence.xml to auto create tables in mysql from my JPA entities upon first deployment OR manual create tables)
AWS S3 Bucket
AWS SNS
AWS Lambda
AWS SES
And change the properties in the properties files of this project and then build the project using maven and then deploy in JEE Server on AWS EC2 like Glassfish for which you need to setup mysql datasource with JNDI name as in my persistence.xml.

https://www.youracclaim.com/users/arif-shaikh/badges
