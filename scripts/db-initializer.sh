#!/usr/bin/sh

set -e

echo "PostgreSQL is ready. Creating databases..."

for cur_db in "auth_user_db" "profile_db" "rag_workspace_db"; do
  psql -U "$POSTGRES_USER" -c "CREATE DATABASE ${cur_db};" || echo "Database '${cur_db}' already exists"
done

echo "Adding required extensions"
psql -U "$POSTGRES_USER" -d "rag_workspace_db" -c "CREATE EXTENSION IF NOT EXISTS vector;"