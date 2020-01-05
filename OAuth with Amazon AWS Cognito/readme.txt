About this project (link given at bottom):

This is one of the exercises I finished while doing a course in AWS. For this I have used AWS Cognito for User authentication SignUp/SignIn. Upon first signup you will be asked to enter your email address, nickname and password. Your will receive an email on your provided email address to verify your email account, after verifying only you will be able to login. When you are on login popup please press signup if you are new user. You can press resend button on signup page if verify link expires. 

After login you can upload photos with this App which will be saved in a S3 bucket and then AWS Rekognition's detect-labels api is run on your uploaded photo which will detect details in the photo and then save those details/labels in MySQL database and also display the detected details/labels on screen. AWS Rekognition is an AWS AI and like all AIs it can only work at a certain level of confidence which can be erroneous. Your photos will be visible to your login user only.

This project is not using load balancer and only using one EC2 t2.micro instance.


This project is running on below link.
Below link use adhoc certificate so to visit it you will have to allow bellow site in your browser. If you are using chrome and when chrome shows warning after you click below link then click "Advanced" link on chrome warning page and then click "Proceed to ec2..." link.
https://ec2-54-244-207-117.us-west-2.compute.amazonaws.com:8087/
