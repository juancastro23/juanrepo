#!/bin/bash

# Establecer JAVA_HOME a JDK 17
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Mostrar versión del Java activo
echo "Usando JDK desde: $JAVA_HOME"
java -version

# Dar permisos y compilar
chmod +x ./gradlew
./gradlew clean build --no-daemon

