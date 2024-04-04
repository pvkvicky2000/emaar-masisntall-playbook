## Emaar MAS setup Playbook

Change the Values in env_list to change to environment list

Run in IBM Cli 
```bash
docker run -v playbook.yaml:/mascli/playbook.yaml --env-file env_list -ti --rm --pull always quay.io/ibmmas/cli
```