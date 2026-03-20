#!/bin/sh
#set -e

export VAULT_ADDR="http://$VAULT_DEV_LISTEN_ADDRESS"
export VAULT_TOKEN="$VAULT_DEV_ROOT_TOKEN_ID"

# Включаем KV v2 secrets engine если ещё не включен
#echo "Настройка Vault..."
#vault secrets enable -path=secret kv-v2 || echo "Secrets engine already started"

SECRET_KEY_VALUE=c29tZV9yYW5kb21fQW5kX1ZFRUVFUllfbGFyZ2VfJGVjcmV0X0tFWQ==
DB_USERNAME_VALUE=postgres
DB_PASSWORD_VALUE=root

# Создаём секреты
echo "Creating secrets..."
vault kv put secret/auth-ms \
  DB_USERNAME="$DB_USERNAME_VALUE" \
  DB_PASSWORD="$DB_PASSWORD_VALUE" \
  SECRET_KEY="$SECRET_KEY_VALUE" \
  ACCESS_DURATION=15 \
  REFRESH_DURATION=7

vault kv put secret/profile-ms \
  DB_USERNAME="$DB_USERNAME_VALUE" \
  DB_PASSWORD="$DB_PASSWORD_VALUE" \
  MINIO_HOST="localhost" \
  MINIO_PORT="9000" \
  MINIO_USERNAME="admin" \
  MINIO_KEY="root1234" \
  MINIO_BUCKET="rag-workspace-ms"

vault kv put secret/gateway-server \
  SECRET_KEY="$SECRET_KEY_VALUE"

vault kv put secret/rag-workspace-ms \
  DB_USERNAME="$DB_USERNAME_VALUE" \
  DB_PASSWORD="$DB_PASSWORD_VALUE" \
  MINIO_HOST="localhost" \
  MINIO_PORT="9000" \
  MINIO_USERNAME="admin" \
  MINIO_KEY="root1234" \
  MINIO_BUCKET="rag-workspace-ms"

# Выводим информацию о созданных секретах
echo "Secrets successfully created"
vault kv list secret

# Бесконечное ожидание чтобы контейнер не завершал работу
#echo "Vault готов к использованию"
#tail -f /dev/null
