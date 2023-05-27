#!/bin/sh
echo "Starting CO-Auth core API"
export COAUTH_DB_IP="127.0.0.1"
export COAUTH_DB_PORT="3306"
export COAUTH_DB_USERNAME="root"
export COAUTH_DB_PASSWORD="admin_password"
export COAUTH_JWT_SECRET="ThisIsSecretForJWTHS512SignatureAlgorithmThatMUSTHave64ByteLength"
export COAUTH_SYSTEM_SECRET="SYSTEM_SECRET"
export COAUTH_USERNAME_SECRET="USERNAME_SECRET"
export COAUTH_TOTP_SECRET="1234567890123456"

java -jar orchestration/target/coauth-0.0.1-SNAPSHOT-fat.jar
