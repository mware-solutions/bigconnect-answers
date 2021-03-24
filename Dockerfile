FROM registry.cloud.bigconnect.io/service-container-base:latest
ENV JAVA_HOME=${JDK11_HOME}

ENV FC_LANG en-US
ENV LC_CTYPE en_US.UTF-8
ENV LANGUAGE=en_US.UTF-8
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8

# dependencies
RUN apt install -y ttf-dejavu fontconfig locales
RUN locale-gen en_US.UTF-8

# add Metabase jar
COPY ./target/uberjar/metabase.jar /app/

# add our run script to the image
COPY ./bin/docker/run_metabase.sh /app/

# create the plugins directory, with writable permissions
RUN mkdir -p /plugins
RUN chmod a+rwx /plugins
COPY ./resources/modules/* /plugins/

# expose our default runtime port
EXPOSE 3000

ENV MB_DB_FILE=/data/metabase
VOLUME /data

# run it
ENTRYPOINT ["/app/run_metabase.sh"]
