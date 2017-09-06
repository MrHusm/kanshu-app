#/bin/bash
cd /usr/local/shell/updateBooks/updateBooks_one.sh
wget -t 1  http://localhost:8082/yuewenJob/updateBooks.go?timeInterval=-30 > /dev/null 2>&1
