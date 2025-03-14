FROM maven

RUN apt-get update && apt-get install -qq -y --no-install-recommends




ENV INSTALL_PATH /barbearia

RUN mkdir $INSTALL_PATH


WORKDIR $INSTALL_PATH

COPY . .