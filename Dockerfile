FROM ubuntu:latest
LABEL authors="hoang"

ENTRYPOINT ["top", "-b"]