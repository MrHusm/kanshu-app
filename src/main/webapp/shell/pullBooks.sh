#/bin/bash
cd /usr/local/shell/pullBooks/pullBooks.sh
wget -t 1  http://localhost:8082/yuewenJob/pullBooks.go?pageSize=100 > /dev/null 2>&1
