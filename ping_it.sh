#!/bin/bash
for i in {1..10000}
  do
    echo "hitting endpoint of Springtest0"

    curl localhost:9390/ServiceC

    sleep 5
  done