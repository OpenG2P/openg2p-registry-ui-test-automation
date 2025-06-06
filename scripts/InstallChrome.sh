#!/bin/bash
set -ex

CHROME_VERSION="120.0.6099.129"
CHROMEDRIVER_VERSION="114.0.5735.90"


wget https://dl.google.com/linux/direct/google-chrome-stable_${CHROME_VERSION}_amd64.deb
sudo apt install ./google-chrome-stable_${CHROME_VERSION}_amd64.deb


wget -N https://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip -P /tmp/
sudo unzip /tmp/chromedriver_linux64.zip -d /usr/local/bin/
sudo chmod +x /usr/local/bin/chromedriver
