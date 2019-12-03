
About this project:
This is nodejs project exposing rest API using nodejs EXPRESS package for:

1. Getting day of the week.

2. Searching books by ISBN which uses a publicaly available google service https://www.googleapis.com/books/v1/volumes
##########################################
To run the project you must have nodejs installed, then open command prompt and go to directory where you downloaded this project then do below inside the that directory:

1. npm install (do this only once)

2. npm start

A. open browser and goto to get today's Day :
http://localhost:3000/api/today

B. http://localhost:3000/api/book_search_by_isbn/1788886526
where 1788886526 is the ISBN of a book. You can replace it with a valid ISBN to search the book.
