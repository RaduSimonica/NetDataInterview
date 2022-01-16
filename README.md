# NetData Interview

This is the test assignment for Radu Simonică.

### Instructions for installing and using this framework:
* Install Docker on your local machine from here: https://www.docker.com/products/docker-desktop
* Make sure the installation is successful.
* Checkout this repository on your local machine
* Download the ```jenkins.tar``` archive from here: https://drive.google.com/file/d/1ktNc4L753Gvi4-uYYOzUVc3WuyAxxPp_/view?usp=sharing
* Copy the jenkins.tar file to ```src/main/resources/docker```
* Inside this directory, you'll find two shell scripts called ```run-grid.sh``` and ```run-jenkins.sh```
* Open a terminal and type the following command ```cd src/main/resources/docker``` or open the terminal directly in that directory
* Execute the ```sh run-jenkins.sh``` command to load the image and start a Docker container with a fully configured Jenkins instance
  * Verify that Jenkins is up and running at this url: http://localhost:8080/ (or ```http://local_IP:8080```)
  * Login into Jenkins using ```user: admin``` ```password: admin``` (so secure! :) )
* Execute the ```sh run-grid.sh``` command to build a set of Docker containers with a fully configured Selenium Grid instance
  * Verify that Selenium Grid is up and running at this url: http://localhost:4444/grid/console (or ```http://local_IP:4444/grid/console``
  * Here you should see two nodes, each with two Chrome instances
* Note the local IP address of the machine (from Network Settings)
* Go to the ```config.properties``` file (```src/main/resources/config.properties```) and modify the following:
  * The IP address inside ```grid.hostname = http://192.168.0.134:4444/wd/hub```. leave the rest of the URL intact
  * The IP address inside ```app.hostname = http://192.168.0.134:19999/```. leave the rest of the URL intact.
* Commit and push your changes to ```master``` branch.
* Go to Jenkins and run the job ```Run Tests```. It will automagically™ deploy the netdata app by running the job ```Deploy Netdata``` and then run the test-suite.
* Hopefully all went smooth and the test(s) are running with no issue.
    * Outcome nr.1: The application is deployed successfully, and all the tests are passed. (Build marked as Successful)
    * Outcome nr.2: The application is deployed successfully, but there are test failures. (Build marked as Unstable)
    * Outcome nr.3: The application is not deployed and the tests are skipped (Build marked as Failure)
    
### Notes:
* This solution was developed and tested on a MacOS machine (MacOS 10.13). I did not develop anything inside to work out of the box on a Windows, or Linux machine. You'll need the following:
  * Chromedriver.exe version 94 (for Windows) or Chromedriver version 94 (for Linux)
  * Change the path separator for all hardcoded paths (for Windows. On Linux they work the same as MacOS)
  * Change the shell scripts to batch files (for Windows)
  * Might need to refactor the ```Runner.runProcess()``` method for Windows.
* This solution uses Selenium Grid version 3 due to a major incompatibility of Docker 4 with MacOS 10.13 (EOL)
* The solution is missing a Reporting module due to lack of time in development and the relatively high complexity. It has the bare minimum modules to work and fulfill the requirements.
* The solution was developed using recycled and upcycled code from other repositories of mine (mostly a demo made by me for DevTalks last year)

If you have any kind of issues deploying or running this solution, please contact me at any time during this recruitment process. (radu@crownstudio.ro)