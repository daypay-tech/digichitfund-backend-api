## Systemd script setup

### create script file
sh
sudo vi /etc/systemd/system/navpulseuk-api.service


#### copy the below content, paste it in the above script file and save it
sh
[Unit]
Description=navpulseuk
After=syslog.target

[Service]
User=root
ExecStart=/var/www/html/app/navpulseuk-backend-api-1.0-SNAPSHOT.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-member.target


<p>
Note: we need to change Description, User and ExecStart value
</p>

#### Enable the service
sh
sudo systemctl enable navpulseuk-api.service


#### Check the status
sh
sudo systemctl status navpulseuk-api.service


#### To start/stop/restart our application
sh
sudo systemctl start navpulseuk-api.service
sudo systemctl stop navpulseuk-api.service
sudo systemctl restart navpulseuk-api.service


### View log
sh
sudo journalctl -u bookin.service


### View log tail
sh
sudo journalctl -u navpulseuk-api.service -f


### If log file failed to create then execute the below command
systemctl restart systemd-journald.service
