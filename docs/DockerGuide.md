More info on how to run and use the docker image can be found here: https://hub.docker.com/r/hippodrome/intellijplugin

# Docker Build Guide
This will create a docker image containing IntelliJ IDEA 2019, this plugin, and an installation of Hippodrome. Running the image will start IntelliJ in the docker container.

1. In your PC, navigate to the directory containing the `Dockerfile` in this folder.
2. Open a command terminal in the directory.
3. Run `docker build .` (add the `-t` parameter to tag the created image, eg. `docker build -t [tag] .`. The current tag for hippodrome is "hippodrome/intellijplugin")
4. Wait for the build process to complete.
5. A new docker image should be created. To start a container with the image, read the dockerhub page: https://hub.docker.com/r/hippodrome/intellijplugin
6. (Optional) When a container docker starts, IntelliJ will open with a prompt for a new project. Use "Source Control" and paste "https://github.com/sigmund-c/hippodromeIntelliJTests/" for a set of tests ready to be used.
7. (Optional) after the set of tests have been imported to IntelliJ, commit the changes by running `docker commit [container name] [tagname]` in a command terminal.
8. (Optional) Push the image to dockerhub by running `docker push [tag]` in a command terminal. The current tag for hippodrome is "hippodrome/intellijplugin".