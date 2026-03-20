#!/bin/bash

docker build \
  --build-arg GPR_USER="DanyaChetvyrtov" \
  --build-arg GPR_KEY="..." \
  -t danilchet/profile-ms:mark .