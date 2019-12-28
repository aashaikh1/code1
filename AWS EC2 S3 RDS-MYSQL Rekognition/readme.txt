AWS EC2 S3 RDS-MYSQL Rekognition

About this project:

This is one of the exercises I finished while doing a course in AWS. It is deployed in AWS with a Elastic Load balancer with two EC2 micro instances running behind for load balancing and fault tolerance. The nice thing of using AWS's Elastic Load Balancer is that it provides a load balancer as a fully managed service.

You can upload photos with this App which will be saved in a S3 bucket and then AWS Rekognition is run on your uploaded photo which will detect-labels in the photo and then save those labels in MySQL database and also display the detected labels on screen.

This AWS app can be accessed at:
http://arif-rootuser-load-balancer-674693475.us-west-2.elb.amazonaws.com/

 
