FROM adoptopenjdk/openjdk11-openj9 AS build
ADD . /src
WORKDIR /src

RUN ./gradlew --no-daemon clean cucumber --info --stacktrace; echo  $? > $?.exit
RUN if [ ! -f "0.exit" ]; then echo "Gradle build FAILED"; exit 1; fi

FROM adoptopenjdk:11-jre-openj9
COPY --from=build /src/build/libs/*.jar ./
COPY --from=build /src/run.sh ./
RUN chmod a+x /run.sh

EXPOSE 8080
CMD ["/run.sh"]



