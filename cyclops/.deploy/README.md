# Deploy

## Install
1. Have nginx redirect to localhost:8000

2. Have the cyclops repo in `/home/cyclops`. I.e, copied to `/home/cyclops/cyclops`

3. Have `deploy.sh` in `/home/cyclops/` (set the variables in your `/etc/profile`) (requires `pass`)

4. Add `cyclops-app.service` to `/etc/systemd/system` and run `systemctl daemon-reload`

5. Run 

```
systemctl enable cyclops-app.service
systemctl start  cyclops-app.service
```

The systemd service will now ensure that the docker-compose service is always running,
even after a reboot or unexpected power-flicker.

## Upgrade

Simply run `systemctl restart cyclops-app.service`
