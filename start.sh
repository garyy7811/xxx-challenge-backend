#!/usr/bin/env bash
#depends on filename not so good
java -jar build/libs/xxx-challenge-backend-0.0.1-SNAPSHOT.jar

#will handle everything including install, but slower start
#./gradlew bootRun --info --stacktrace