## Emaar MAS setup Playbook

Change the Values in env_list to change to environment list
Navigate to folder where you store your configuration files ( eg: /home/mascli)

Run in IBM Cli
```bash
cd /home/mascli
docker run -v .:/mascli/masconfig --env-file env_list -ti --rm --pull always quay.io/ibmmas/cli
```
<br/>

**Login to Openshift First 🏁**
```bash
# Get the login command from openshift 
oc login xxxxxxxxxxx
```
---
👉 If Mongo Db is hosted externally , then run the below script first to generate mongo DB configuration
```bash
ansible-playbook mongocfggen.yaml -f 10 --verbose 
```


* Optional: Create Azure files standard for ARO should be run inside CLI
```bash
oc apply -f ./emaar/azurefiles-standard.yaml
```

👉 Run the Below command to Run the Maximo install command

```bash
ansible-playbook playbook.yaml -f 10 --verbose 
```