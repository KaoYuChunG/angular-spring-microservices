#!/bin/sh
# ==============================================================================
# 企業級微服務多資料庫初始化腳本
# 用途：在 PostgreSQL 容器啟動時，自動建立各微服務獨立的資料庫與專屬帳號。
# 運作原理：PostgreSQL 官方映像檔會自動執行 /docker-entrypoint-initdb.d/ 目錄下的所有 .sh 腳本。
# ==============================================================================
set -e

# 使用超級管理員帳號執行資料庫與角色建立
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- 1. 建立 Keycloak SSO 專屬帳號與資料庫 (符合最小權限原則)
    CREATE USER keycloak WITH PASSWORD 'keycloak_dev_pass';
    CREATE DATABASE keycloak_db OWNER keycloak;
    GRANT ALL PRIVILEGES ON DATABASE keycloak_db TO keycloak;

    -- 2. 建立各微服務獨立資料庫 (Database-per-service 模式，確保微服務自主性)
    CREATE DATABASE auth_db;
    CREATE DATABASE online_db;
    CREATE DATABASE homework_db;

    -- 3. 印出成功訊息以供日誌追踪
    \echo '==================================================='
    \echo 'Enterprise microservices databases initialized successfully!'
    \echo 'Databases created: keycloak_db, auth_db, online_db, homework_db'
    \echo '==================================================='
EOSQL
