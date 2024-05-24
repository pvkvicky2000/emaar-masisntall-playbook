#!/bin/bash

export MAS_INSTANCE_ID=uat
export MAS_CONFIG_DIR=/mascli/masconfig

export MONGODB_NAMESPACE=
export MONGODB_ADMIN_USERNAME=
export MONGODB_ADMIN_PASSWORD=
export MONGODB_HOSTS=
export MONGODB_RETRY_WRITE=false
export MONGODB_CA_PEM_LOCAL_FILE=
export ROLE_NAME=gencfg_mongo


# Debug: Print environment variables
echo "MONGODB_NAMESPACE=$MONGODB_NAMESPACE"
echo "MONGODB_ADMIN_USERNAME=$MONGODB_ADMIN_USERNAME"
echo "MONGODB_ADMIN_PASSWORD=$MONGODB_ADMIN_PASSWORD"
echo "MONGODB_HOSTS=$MONGODB_HOSTS"
echo "MONGODB_CA_PEM_LOCAL_FILE=$MONGODB_CA_PEM_LOCAL_FILE"
echo "MAS_INSTANCE_ID=$MAS_INSTANCE_ID"
echo "MAS_CONFIG_DIR=$MAS_CONFIG_DIR"
echo "CUSTOM_LABELS=$CUSTOM_LABELS"
echo "MONGODB_RETRY_WRITES=$MONGODB_RETRY_WRITES"

# Run the Ansible role
ansible-playbook -i localhost, -c local -m include_role -a name=gencfg_mongo



# Set environment variables
export DB_INSTANCE_ID="dbinst"
export MAS_JDBC_USER="jdbc_user"
export MAS_JDBC_PASSWORD="jdbc_password"
export MAS_JDBC_URL="jdbc:db2://hostname:port/dbname"
export MAS_JDBC_CERT_LOCAL_FILE="/path/to/jdbc_cert.pem"
export MAS_CONFIG_SCOPE="app"
export MAS_INSTANCE_ID="instance1"
export MAS_WORKSPACE_ID="workspace1"
export MAS_CONFIG_DIR="/path/to/config/dir"
export MAS_APP_ID="app_id"
export SSL_ENABLED="true"
export CUSTOM_LABELS="label1:value1,label2:value2"

# Debug: Print environment variables
echo "DB_INSTANCE_ID=$DB_INSTANCE_ID"
echo "MAS_JDBC_USER=$MAS_JDBC_USER"
echo "MAS_JDBC_PASSWORD=$MAS_JDBC_PASSWORD"
echo "MAS_JDBC_URL=$MAS_JDBC_URL"
echo "MAS_JDBC_CERT_LOCAL_FILE=$MAS_JDBC_CERT_LOCAL_FILE"
echo "MAS_CONFIG_SCOPE=$MAS_CONFIG_SCOPE"
echo "MAS_INSTANCE_ID=$MAS_INSTANCE_ID"
echo "MAS_WORKSPACE_ID=$MAS_WORKSPACE_ID"
echo "MAS_CONFIG_DIR=$MAS_CONFIG_DIR"
echo "MAS_APP_ID=$MAS_APP_ID"
echo "SSL_ENABLED=$SSL_ENABLED"
echo "CUSTOM_LABELS=$CUSTOM_LABELS"

# Run the Ansible role
ansible-playbook -i localhost, -c local -m include_role -a name=gencfg_jdbc

#!/bin/bash

# Set environment variables
export MAS_INSTANCE_ID="instance1"
export MAS_WORKSPACE_ID="workspace1"
export MAS_WORKSPACE_NAME="Workspace One"
export MAS_CONFIG_DIR="/path/to/config/dir"
export CUSTOM_LABELS="label1:value1,label2:value2"

# Debug: Print environment variables
echo "MAS_INSTANCE_ID=$MAS_INSTANCE_ID"
echo "MAS_WORKSPACE_ID=$MAS_WORKSPACE_ID"
echo "MAS_WORKSPACE_NAME=$MAS_WORKSPACE_NAME"
echo "MAS_CONFIG_DIR=$MAS_CONFIG_DIR"
echo "CUSTOM_LABELS=$CUSTOM_LABELS"

# Run the Ansible role
ansible-playbook -i localhost, -c local -m include_role -a name=gencfg_workspace

#!/bin/bash

# Set environment variables
export IBM_CATALOGS_ACTION="install"
export ARTIFACTORY_USERNAME="your_artifactory_username"
export ARTIFACTORY_TOKEN="your_artifactory_token"
export MAS_CATALOG_DIGEST="your_catalog_digest"
export MAS_CATALOG_VERSION="v8-amd64"

# Debug: Print environment variables
echo "IBM_CATALOGS_ACTION=$IBM_CATALOGS_ACTION"
echo "ARTIFACTORY_USERNAME=$ARTIFACTORY_USERNAME"
echo "ARTIFACTORY_TOKEN=$ARTIFACTORY_TOKEN"
echo "MAS_CATALOG_DIGEST=$MAS_CATALOG_DIGEST"
echo "MAS_CATALOG_VERSION=$MAS_CATALOG_VERSION"

# Run the Ansible role
ansible-playbook -i localhost, -c local -m include_role -a name=ibm_catalogs
