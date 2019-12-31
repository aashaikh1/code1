AWS EC2 S3 RDS-MYSQL Rekognition
http://arif-rootuser-load-balancer-674693475.us-west-2.elb.amazonaws.com/

About this project :

This is one of the exercises I finished while doing a course in AWS. You can upload photos with this App which will be saved in a S3 bucket and then AWS Rekognition's detect-labels api is run on your uploaded photo which will detect details in the photo and then save those details/labels in MySQL database and also display the detected details/labels on screen. AWS Rekognition is an AWS AI and like all AIs it can only work at a certain level of confidence which can be erroneous.

It is deployed in AWS with a Elastic Load balancer with two EC2 micro instances running behind.

This AWS app can be accessed at:
http://arif-rootuser-load-balancer-674693475.us-west-2.elb.amazonaws.com/

 
