# Data Visualization Component

## Application Folder
- in this folder have applications for windows32, windows64, linux32, linux64 that ready to run without a processing compiler program.
- already have an input for running application

## Dataset
- .csv file that contains a large amount of data.
- thi file is an input of "waypoint-test.js" that will send requests to google maps api for lattitude and longitude of each trip in .csv file.

## Source Code
 Contain 2 components of code such as javascripts and processing
 
 
 ### Javascripts
 
 #### Mapbox
 - generate a gray-scale map by setting an input lattitude and longitude.
 
 #### Waypoint-test
 - generate routes by sending request to google maps api from starting point to ending point.
 - use .csv as an input file.
 - give an output as .txt file. 
 
 ### Processing
 - This is main code for visualizing and analyzing.
 - Input has 2 files: lattitude & longitude file ("Output.txt") and time file ("timeOutput.txt")
 - The Default input has 7 days input that can find in directory "processing/data/"
