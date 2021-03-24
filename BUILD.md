To build

JDK 1.8 !

sudo curl -O /tmp/linux-install-1.10.1.708.sh https://download.clojure.org/install/linux-install-1.10.1.708.sh
sudo chmod 755 /tmp/linux-install-1.10.1.708.sh
sudo /tmp/linux-install-1.10.1.708.sh

sudo curl -O /usr/local/bin/lein https://raw.github.com/technomancy/leiningen/stable/bin/lein
sudo chmod 755 /usr/local/bin/lein
lein upgrade
lein deps

./bin/build

To develop:

yarn install
yarn build (or build-hot)

install Cursive intellij plugin
Create Local REPL runtime with dev profile

lein with-profiles +include-all-drivers run

http://localhost:3000/
