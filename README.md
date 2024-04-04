## Emaar MAS setup Playbook

Change the Values in env_list to change to environment list

Run in IBM Cli 
```bash
docker run -v .:/mascli/emaar --env-file env_list -ti --rm --pull always quay.io/ibmmas/cli
```
<br/>

* Optional: Create Azure files standard for ARO should be run inside CLI
```bash
oc apply -f ./emaar/azurefiles-standard.yaml
```