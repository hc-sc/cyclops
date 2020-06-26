#!/usr/bin/env bash


#   // var rootUser = '$MONGO_ROOT_USER';
#   // var rootPassword = '$MONGO_ROOT_PASS';
#   // var admin = db.getSiblingDB('admin');
#   // admin.auth(rootUser, rootPassword);

mongo -- "$MONGO_MAPI_DB" <<EOF

    var user = '$MONGO_MAPI_USER';
    var passwd = '$MONGO_MAPI_PASS';
    db.createUser({user: user, pwd: passwd, roles: ["readWrite"]});

EOF
