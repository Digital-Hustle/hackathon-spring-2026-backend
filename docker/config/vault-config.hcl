storage "file" {
  path = "/vault/data"
}

listener "tcp" {
  address = "0.0.0.0:8200"
  tls_disable = true
  # Добавьте эту строку для UI
  ui = true
}

# Разрешаем UI
api_addr = "http://0.0.0.0:8200"