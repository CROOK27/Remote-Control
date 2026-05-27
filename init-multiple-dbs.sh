#!/bin/bash
set -e

echo "=== Initializing multiple databases ==="

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE test_mgmt_db;
    CREATE DATABASE session_db;
    CREATE DATABASE proctoring_db;
    CREATE DATABASE gamification_db;
    CREATE DATABASE analytics_db;
    CREATE DATABASE notification_db;
    CREATE DATABASE auth_db;
EOSQL

echo "=== All databases created successfully ==="